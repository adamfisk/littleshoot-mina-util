package org.lastbamboo.common.util.mina;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

import org.apache.mina.common.IoSession;
import org.lastbamboo.common.util.NotYetImplementedException;

/**
 * A socket implementation that wraps a MINA {@link IoSession} in a 
 * {@link Socket} interface.
 */
public final class IoSessionSocket extends Socket
    {
    
    private final IoSession m_ioSession;
    private final InputStream m_in;
    private final OutputStream m_out;

    /**
     * Creates a new {@link Socket} subclass that works with MINA sessions.
     * 
     * @param ioSession The MINA {@link IoSession}.
     * @param in The {@link InputStream}.
     * @param out The {@link OutputStream}.
     */
    public IoSessionSocket(final IoSession ioSession, 
        final InputStream in, final OutputStream out)
        {
        this.m_ioSession = ioSession;
        this.m_in = in;
        this.m_out = out;
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bind(final SocketAddress address) throws IOException
        {
        // For now, we do not allow binding of the local address.  The ephemeral
        // port will just be chosen.
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void close() throws IOException
        {
        this.m_ioSession.close();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(final SocketAddress address) throws IOException
        {
        connect (address, 60000);
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(final SocketAddress address, final int timeout) 
        throws IOException
        {

        }

    /**
     * {@inheritDoc}
     */
    @Override
    public SocketChannel getChannel()
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public InetAddress getInetAddress()
        {
        return ((InetSocketAddress)this.m_ioSession.getRemoteAddress()).getAddress();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getInputStream() throws IOException
        {
        return this.m_in;
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getKeepAlive() throws SocketException
        {
        return false;
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public InetAddress getLocalAddress()
        {
        return ((InetSocketAddress)this.m_ioSession.getLocalAddress()).getAddress();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLocalPort()
        {
        return ((InetSocketAddress)this.m_ioSession.getLocalAddress()).getPort();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public SocketAddress getLocalSocketAddress()
        {
        return this.m_ioSession.getLocalAddress();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getOOBInline() throws SocketException
        {
        return false;
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public OutputStream getOutputStream() throws IOException
        {
        return this.m_out;
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPort()
        {
        return ((InetSocketAddress)this.m_ioSession.getRemoteAddress()).getPort();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int getReceiveBufferSize() 
        throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public SocketAddress getRemoteSocketAddress()
        {
        return this.m_ioSession.getRemoteAddress();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getReuseAddress () throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int getSendBufferSize() throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSoLinger() throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized int getSoTimeout() throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getTcpNoDelay() throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTrafficClass() throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound()
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isClosed()
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isConnected()
        {
        return this.m_ioSession.isConnected();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInputShutdown()
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOutputShutdown()
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendUrgentData(final int data) throws IOException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setKeepAlive(final boolean on) throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOOBInline(final boolean on) throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPerformancePreferences(final int connectionTime,
        final int latency, final int bandwidth)
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void setReceiveBufferSize(final int size) 
        throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReuseAddress(final boolean on) throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSendBufferSize(final int size) throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSoLinger(final boolean on, final int linger) 
        throws SocketException
        {
        // Ignored since we don't know how to handle it for now.
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSoTimeout(final int timeout) 
        throws SocketException
        {
        // Ignored since we don't know how to handle it for now.
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTcpNoDelay(final boolean on) throws SocketException
        {
        // Ignored since we don't know how to handle it for now.
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTrafficClass(final int tc) throws SocketException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdownInput() throws IOException
        {
        throw new NotYetImplementedException ();
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdownOutput() throws IOException
        {
        throw new NotYetImplementedException ();
        }
    }
