package org.lastbamboo.common.util.mina;

import org.apache.mina.common.IdleStatus;
import org.apache.mina.common.IoHandler;
import org.apache.mina.common.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link IoHandler} that allows STUN to be demultiplexed with other protocols. 
 */
public class DemuxingIoHandler implements IoHandler
    {

    private final Logger m_log = LoggerFactory.getLogger(getClass());
    
    private final Class m_class1;
    private final Class m_class2;
    private final IoHandler m_ioHandler1;
    private final IoHandler m_ioHandler2;

    /**
     * Creates a new {@link IoHandler} that demultiplexes encoded and decoded
     * messages between STUN and another protocol.
     * 
     * @param class1 The message class for the first protocol.
     * @param ioHandler1 The {@link IoHandler} for the first protocol.
     * @param class2 The message class for the second protocol.
     * @param ioHandler2 The {@link IoHandler} for the second protocol.
     */
    public DemuxingIoHandler(final Class class1, final IoHandler ioHandler1,
        final Class class2, final IoHandler ioHandler2)
        {
        if (ioHandler1 == null)
            {
            throw new NullPointerException("Null auxillary handler");
            }
        if (ioHandler2 == null)
            {
            throw new NullPointerException("Null STUN handler");
            }
        m_class1 = class1;
        m_class2 = class2;
        m_ioHandler1 = ioHandler1;
        m_ioHandler2 = ioHandler2;
        }
    
    public void exceptionCaught(final IoSession session, final Throwable cause)
        throws Exception
        {
        m_log.warn("Exception caught", cause);
        this.m_ioHandler1.exceptionCaught(session, cause);
        this.m_ioHandler2.exceptionCaught(session, cause);
        }

    public void messageReceived(final IoSession session, final Object message)
        throws Exception
        {
        m_log.debug("Received message: {}", message);
        final IoHandler handler = getHandlerForMessage(message);
        if (handler != null)
            {
            handler.messageReceived(session, message);
            }
        }

    public void messageSent(final IoSession session, final Object message) 
        throws Exception
        {
        m_log.debug("Sent message: {}", message);
        final IoHandler handler = getHandlerForMessage(message);
        if (handler != null)
            {
            handler.messageSent(session, message);
            }
        }

    private IoHandler getHandlerForMessage(final Object message)
        {
        if (this.m_class1.isAssignableFrom(message.getClass()))
            {
            return this.m_ioHandler1;
            }
        else if (this.m_class2.isAssignableFrom(message.getClass()))
            {
            return this.m_ioHandler2;
            }
        else
            {
            m_log.warn("Could not find IoHandler for message: {}", message);
            return null;
            }
        }

    public void sessionClosed(final IoSession session) throws Exception
        {
        this.m_ioHandler1.sessionClosed(session);
        this.m_ioHandler2.sessionClosed(session);
        }

    public void sessionCreated(final IoSession session) throws Exception
        {
        this.m_ioHandler1.sessionCreated(session);
        this.m_ioHandler2.sessionCreated(session);
        }

    public void sessionIdle(final IoSession session, final IdleStatus status)
        throws Exception
        {
        this.m_ioHandler1.sessionIdle(session, status);
        this.m_ioHandler2.sessionIdle(session, status);
        }

    public void sessionOpened(IoSession session) throws Exception
        {
        this.m_ioHandler1.sessionOpened(session);
        this.m_ioHandler2.sessionOpened(session);
        }

    }
