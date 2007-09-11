package org.lastbamboo.common.util.mina;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * {@link ProtocolCodecFactory} for protocols that can be demultiplexed.
 * 
 * @param <T>
 *      The type that the created encoders are designed to encode.
 */
public interface DemuxableProtocolCodecFactory<T>
    {
    /**
     * Creates a new encoder.
     * 
     * @return A new {@link ProtocolEncoder}.
     */
    ProtocolEncoder newEncoder();

    /**
     * Returns a new (or reusable) instance of {@link ProtocolDecoder} which
     * decodes binary or protocol-specific data into message objects.
     * 
     * @return A new {@link DemuxableProtocolDecoder} for decoding a particular
     * protocol.
     */
    DemuxableProtocolDecoder newDecoder();
    
    /**
     * Gets the class this factory is designed to encode.
     * 
     * @return The {@link Class} this factory is designed to encode.
     */
    Class<T> getClassToEncode();

    /**
     * Determines whether or not this codec factory is capable of decoding
     * the specified data.
     * 
     * @param in The data to decode.
     * @return <code>true</code> if this decoder can decode the data, 
     * otherwise <code>false</code>.
     */
    boolean canDecode(ByteBuffer in);
    }
