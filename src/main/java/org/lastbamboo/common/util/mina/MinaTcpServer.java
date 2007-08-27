package org.lastbamboo.common.util.mina;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.common.DefaultIoFilterChainBuilder;
import org.apache.mina.common.ExecutorThreadModel;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoServiceListener;
import org.apache.mina.common.ThreadModel;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A MINA TCP server.
 */
public class MinaTcpServer implements MinaServer
    {
    
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    
    private final SocketAcceptor m_acceptor;
    private final int m_port;
    private final IoHandler m_handler;
    
    /**
     * Creates a new MINA TCP server.
     * 
     * @param codecFactory The codec factory to use with the acceptor.
     * @param ioServiceListener The listener for IO service events.
     * @param handler The {@link IoHandler} for processing incoming data.
     * @param port The port to listen on. 
     */
    public MinaTcpServer(final ProtocolCodecFactory codecFactory, 
        final IoServiceListener ioServiceListener, final IoHandler handler,
        final int port)
        {
        this(codecFactory, ioServiceListener, handler, port, 
            "MINA-Server-Thread");
        }

    /**
     * Creates a new MINA TCP server.
     * 
     * @param codecFactory The codec factory to use with the acceptor.
     * @param ioServiceListener The listener for IO service events.
     * @param handler The {@link IoHandler} for processing incoming data.
     * @param port The port to listen on. 
     * @param baseThreadName The base name that will be used for threads
     * processing data arriving on the server.
     */
    public MinaTcpServer(final ProtocolCodecFactory codecFactory, 
        final IoServiceListener ioServiceListener, final IoHandler handler,
        final int port, final String baseThreadName)
        {
        m_handler = handler;
        m_port = port;
        final Executor threadPool = Executors.newCachedThreadPool();
        m_acceptor = new SocketAcceptor(
            Runtime.getRuntime().availableProcessors() + 1, threadPool);

        final SocketAcceptorConfig cfg = new SocketAcceptorConfig();
        
        final ThreadModel threadModel = 
            ExecutorThreadModel.getInstance(baseThreadName);
        cfg.setThreadModel(threadModel);

        // Just hoping this method does what it sounds like it does.
        cfg.setDisconnectOnUnbind(true);

        cfg.setReuseAddress(true);
        cfg.getSessionConfig().setReuseAddress(true);
        
        m_acceptor.addListener(ioServiceListener);

        final DefaultIoFilterChainBuilder filterChainBuilder = 
            cfg.getFilterChain();
        final ProtocolCodecFilter codecFilter = 
            new ProtocolCodecFilter(codecFactory);
        filterChainBuilder.addLast("codec", codecFilter);
        final ExecutorService executor = Executors.newCachedThreadPool();
        final ExecutorFilter executorFilter = new ExecutorFilter(executor);
        filterChainBuilder.addLast("threadPool", executorFilter);
        m_acceptor.setDefaultConfig(cfg);
        }

    public void start()
        {
        final InetSocketAddress address = new InetSocketAddress(this.m_port);

        try
            {
            m_acceptor.bind(address, m_handler);
            }
        catch (final IOException e)
            {
            LOG.error("Could not bind!!", e);
            }
        }

    public void stop()
        {
        this.m_acceptor.unbindAll();
        }

    }
