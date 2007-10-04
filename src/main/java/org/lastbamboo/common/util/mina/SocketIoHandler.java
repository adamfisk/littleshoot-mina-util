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
import org.apache.mina.util.SessionLog;

public abstract class SocketIoHandler extends IoHandlerAdapter
    {
    private static final String KEY_IN = SocketIoHandler.class.getName()
            + ".in";

    private static final String KEY_OUT = SocketIoHandler.class.getName()
            + ".out";

    private int readTimeout;

    private int writeTimeout;

    private final IoSessionOutputStreamFactory m_osFactory;

    protected SocketIoHandler()
        {
        m_osFactory = new IoSessionOutputStreamFactory()
            {
            public ByteBufferIoSessionOutputStream newStream(IoSession session)
                {
                return new ByteBufferIoSessionOutputStream(session);
                }
            };
        }
    
    protected SocketIoHandler(final IoSessionOutputStreamFactory osFactory)
        {
        m_osFactory = osFactory;
        }

    /**
     * Implement this method to execute your stream I/O logic; <b>please note
     * that you must forward the process request to other thread or thread pool.</b>
     */
    protected abstract void onSocket(Socket sock);

    /**
     * Returns read timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public int getReadTimeout()
        {
        return readTimeout;
        }

    /**
     * Sets read timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public void setReadTimeout(final int readTimeout)
        {
        this.readTimeout = readTimeout;
        }

    /**
     * Returns write timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public int getWriteTimeout()
        {
        return writeTimeout;
        }

    /**
     * Sets write timeout in seconds. The default value is <tt>0</tt>
     * (disabled).
     */
    public void setWriteTimeout(int writeTimeout)
        {
        this.writeTimeout = writeTimeout;
        }

    /**
     * Initializes streams and timeout settings.
     */
    public void sessionOpened(final IoSession session)
        {
        // Set timeouts
        session.setWriteTimeout(writeTimeout);
        session.setIdleTime(IdleStatus.READER_IDLE, readTimeout);

        // Create streams
        final InputStream in = new IoSessionInputStream();
        final OutputStream out = this.m_osFactory.newStream(session);//new IoSessionOutputStream(session);
        session.setAttribute(KEY_IN, in);
        session.setAttribute(KEY_OUT, out);
        final Socket ioSocket = new IoSessionSocket(session, in, out);
        onSocket(ioSocket);
        }

    /**
     * Closes streams
     */
    public void sessionClosed(IoSession session) throws Exception
        {
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
    public void messageReceived(IoSession session, Object buf)
        {
        final IoSessionInputStream in = (IoSessionInputStream) session
                .getAttribute(KEY_IN);
        in.write((ByteBuffer) buf);
        }

    /**
     * Forwards caught exceptions to input stream.
     */
    public void exceptionCaught(IoSession session, Throwable cause)
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
            SessionLog.warn(session, "Unexpected exception.", cause);
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

        public StreamIoException(IOException cause)
            {
            super(cause);
            }
        }
    }