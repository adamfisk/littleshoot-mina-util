package org.lastbamboo.common.util.mina;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.common.WriteFuture;

/**
 * An abstract utility class for creating {@link OutputStream}s from 
 * MINA {@link IoSession}s for arbitrary message types.  This allows users
 * to create streams from specialized methods other than {@link ByteBuffer}s.
 *
 * @param <T> The type of message it's an {@link OutputStream} for.
 */
public abstract class AbstractIoSessionOutputStream<T> extends OutputStream 
    {
    protected final IoSession m_ioSession;

    protected WriteFuture m_lastWriteFuture;

    protected AbstractIoSessionOutputStream(final IoSession session) 
        {
        this.m_ioSession = session;
        }

    @Override
    public void close() throws IOException 
        {
        try
            {
            flush();
            }
        finally
            {
            m_ioSession.close().join();
            }
        }

    protected void checkClosed() throws IOException
        {
        if (!m_ioSession.isConnected())
            {
            throw new IOException("The session has been closed.");
            }
        }

    protected synchronized void write(final T message) throws IOException
        {
        checkClosed();
        m_lastWriteFuture = m_ioSession.write(message);
        //m_lastWriteFuture.join(session.getWriteTimeoutInMillis());
        }

    @Override
    public synchronized void flush() throws IOException
        {
        if (m_lastWriteFuture == null)
            {
            return;
            }

        m_lastWriteFuture.join();
        if (!m_lastWriteFuture.isWritten())
            {
            throw new IOException(
                    "The bytes could not be written to the session");
            }
        }
    }