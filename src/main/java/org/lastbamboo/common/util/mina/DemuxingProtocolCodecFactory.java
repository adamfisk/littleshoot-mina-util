package org.lastbamboo.common.util.mina;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Codec factory that can demultiplex incoming data between multiple protocols.
 */
public class DemuxingProtocolCodecFactory implements ProtocolCodecFactory
    {
    
    private final Logger m_log = LoggerFactory.getLogger(getClass());

    private final List<DemuxableProtocolCodecFactory> m_codecFactories =
        new LinkedList<DemuxableProtocolCodecFactory>();
    
    /**
     * Creates a new {@link DemuxingProtocolCodecFactory} with the specified
     * encoders and decoders.
     * 
     * @param firstCodecFactory The codec factory for one protocol.
     * @param secondCodecFactory The codec factory for another protocol.
     */
    public DemuxingProtocolCodecFactory(
        final DemuxableProtocolCodecFactory firstCodecFactory,
        final DemuxableProtocolCodecFactory secondCodecFactory)
        {
        m_codecFactories.add(firstCodecFactory);
        m_codecFactories.add(secondCodecFactory);
        }

    public ProtocolDecoder getDecoder() 
        {
        m_log.debug("Returning decoder...");
        synchronized (this.m_codecFactories)
            {
            return new DemuxingProtocolDecoder(this.m_codecFactories);
            }
        }

    public ProtocolEncoder getEncoder() 
        {
        m_log.debug("Returning encoder...");
        synchronized (this.m_codecFactories)
            {
            return new DemuxingProtocolEncoder(this.m_codecFactories);
            }
        }
    
    private static final class DemuxingProtocolEncoder 
        implements ProtocolEncoder
        {

        private final Logger m_encoderLogger = 
            LoggerFactory.getLogger(getClass());
        private final Map<Class, DemuxableProtocolCodecFactory> m_encoderFactories =
            new HashMap<Class, DemuxableProtocolCodecFactory>();

        private DemuxingProtocolEncoder(
            final List<DemuxableProtocolCodecFactory> factories)
            {
            for (final DemuxableProtocolCodecFactory factory : factories)
                {
                this.m_encoderFactories.put(factory.getClassToEncode(), factory);
                }
            }

        public void dispose(final IoSession session) throws Exception
            {
            // TODO Auto-generated method stub
            
            }

        public void encode(final IoSession session, final Object message, 
            final ProtocolEncoderOutput out) throws Exception
            {
            this.m_encoderLogger.debug("Encoding message: {}", message);
            // This only currently works for messages that have an interface
            // two levels up, such as messages with a parent abstract class
            // that implements an interface -- the way we typically do things.
            final Class[] interfaces = 
                message.getClass().getSuperclass().getInterfaces();
            
            if (interfaces.length == 0)
                {
                m_encoderLogger.warn("No interfaces for message: {}", message);
                return;
                }
            
            m_encoderLogger.debug("Accessing encoder for interfaces: {}", 
                interfaces);
            
            
            final DemuxableProtocolCodecFactory factory = 
                getEncoderFor(interfaces);
                
            if (factory == null)
                {
                m_encoderLogger.warn("Could not get encoder for: " + message);
                return;
                }
            final ProtocolEncoder encoder = factory.newEncoder();
            encoder.encode(session, message, out);
            }

        private DemuxableProtocolCodecFactory getEncoderFor(
            final Class[] interfaces)
            {
            for (int i = 0; i < interfaces.length; i++)
                {
                final DemuxableProtocolCodecFactory factory = 
                    this.m_encoderFactories.get(interfaces[i]);
                if (factory != null)
                    {
                    return factory;
                    }
                }
            m_encoderLogger.warn("No encoder for any of: {}", interfaces);
            return null;
            }
        
        }

    private static final class DemuxingProtocolDecoder 
        implements ProtocolDecoder
        {
        private final Logger m_decoderLog = LoggerFactory.getLogger(getClass());
        private DemuxableProtocolDecoder m_currentDecoder;
        private final List<DemuxableProtocolCodecFactory> m_codecFactories;

        private DemuxingProtocolDecoder(
            final List<DemuxableProtocolCodecFactory> factories)
            {
            this.m_codecFactories = 
                new LinkedList<DemuxableProtocolCodecFactory>();
            this.m_codecFactories.addAll(factories);
            }
        
        public void decode(final IoSession session, final ByteBuffer in, 
            final ProtocolDecoderOutput out) throws Exception
            {
            while (in.hasRemaining())
                {
                if (this.m_currentDecoder == null || 
                    this.m_currentDecoder.atMessageBoundary())
                    {
                    this.m_currentDecoder = selectDecoder(in);
                    }
                this.m_currentDecoder.decode(session, in, out);
                }
            }

        /**
         * Selects the first decoder that is capable of decoding the message.
         * 
         * @param in The {@link ByteBuffer} to decode.
         * @return The decoder capable of decoding the data.
         */
        private DemuxableProtocolDecoder selectDecoder(final ByteBuffer in)
            {
            int limit = in.limit();
            int pos = in.position();
            for (final DemuxableProtocolCodecFactory decoderFactory : this.m_codecFactories)
                {
                try
                    {
                    if (decoderFactory.canDecode(in))
                        {
                        m_decoderLog.debug("Returning decoder from factory: {}", 
                            decoderFactory);
                        return decoderFactory.newDecoder();
                        }
                    }
                finally
                    {
                    in.position(pos);
                    in.limit(limit);
                    }
                }
            m_decoderLog.warn("Did not understand buffer: {}", 
                MinaUtils.toAsciiString(in));
            throw new IllegalArgumentException("Could not read data: " + 
                MinaUtils.toAsciiString(in));
            }

        public void dispose(final IoSession session) throws Exception
            {
            }

        public void finishDecode(final IoSession session, 
            final ProtocolDecoderOutput out) throws Exception
            {
            }
        
        }
    }
