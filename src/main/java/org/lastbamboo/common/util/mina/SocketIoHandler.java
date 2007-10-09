package org.lastbamboo.common.util.mina;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandlerAdapter;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketIoHandler extends IoHandlerAdapter
    {
    
    private final Logger m_log = LoggerFactory.getLogger(getClass());
    private static final String KEY_IN = 
        SocketIoHandler.class.getName()+".in";

    private static final String KEY_OUT = 
        SocketIoHandler.class.getName()+ ".out";

    private int m_readTimeout;

    private int m_writeTimeout;

    private final IoSessionOutputStreamFactory m_osFactory;

    public SocketIoHandler()
        {
        m_osFactory = new IoSessionOutputStreamFactory()
            {
            public ByteBufferIoSessionOutputStream newStream(IoSession session)
                {
                return new ByteBufferIoSessionOutputStream(session);
                }
            };
        }
    
    public SocketIoHandler(final IoSessionOutputStreamFactory osFactory)
        {
        m_osFactory = osFactory;
        }

    /**
     * Returns read timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public int getReadTimeout()
        {
        return m_readTimeout;
        }

    /**
     * Sets read timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public void setReadTimeout(final int readTimeout)
        {
        this.m_readTimeout = readTimeout;
        }

    /**
     * Returns write timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public int getWriteTimeout()
        {
        return m_writeTimeout;
        }

    /**
     * Sets write timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public void setWriteTimeout(int writeTimeout)
        {
        this.m_writeTimeout = writeTimeout;
        }

    /**
     * Initializes streams and timeout settings.
     */
    public void sessionOpened(final IoSession session)
        {
        // Set timeouts
        session.setWriteTimeout(m_writeTimeout);
        session.setIdleTime(IdleStatus.READER_IDLE, m_readTimeout);

        // Create streams
        final InputStream in = new IoSessionInputStream(session, m_readTimeout);
        final OutputStream out = this.m_osFactory.newStream(session);
        session.setAttribute(KEY_IN, in);
        session.setAttribute(KEY_OUT, out);
        final Socket ioSocket = new IoSessionSocket(session, in, out);
        session.setAttribute("SOCKET", ioSocket);
        //onSocket(ioSocket);
        }

    /**
     * Closes streams
     */
    public void sessionClosed(final IoSession session) throws Exception
        {
        m_log.debug("Closing streams!!!");
        final InputStream in = (InputStream) session.getAttribute(KEY_IN);
        final OutputStream out = (OutputStream) session.getAttribute(KEY_OUT);
        try
            {
            in.close();
            }
        finally
            {
            out.close();
            }
        }

    /**
     * Forwards read data to input stream.
     */
    public void messageReceived(final IoSession session, final Object buf)
        {
        final IoSessionInputStream in = 
            (IoSessionInputStream) session.getAttribute(KEY_IN);
        
        in.write((ByteBuffer) buf);
        }

    /**
     * Forwards caught exceptions to input stream.
     */
    public void exceptionCaught(final IoSession session, final Throwable cause)
        {
        final IoSessionInputStream in = (IoSessionInputStream) session
                .getAttribute(KEY_IN);

        IOException e = null;
        if (cause instanceof StreamIoException)
            {
            e = (IOException) cause.getCause();
            }
        else if (cause instanceof IOException)
            {
            e = (IOException) cause;
            }

        if (e != null && in != null)
            {
            in.throwException(e);
            }
        else
            {
            m_log.warn("Unexpected exception.", cause);
            session.close();
            }
        }

    /**
     * Handles read timeout.
     */
    public void sessionIdle(final IoSession session, final IdleStatus status)
        {
        if (status == IdleStatus.READER_IDLE)
            {
            throw new StreamIoException(new SocketTimeoutException(
                    "Read timeout"));
            }
        }

    private static class StreamIoException extends RuntimeException
        {
        private static final long serialVersionUID = 3976736960742503222L;

        private StreamIoException(final IOException cause)
            {
            super(cause);
            }
        }
    }
