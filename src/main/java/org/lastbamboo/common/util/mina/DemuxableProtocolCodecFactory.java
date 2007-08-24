package org.lastbamboo.common.util.mina;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public interface DemuxableProtocolCodecFactory
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
     */
    DemuxableProtocolDecoder newDecoder();
    
    /**
     * Gets the class this factory is designed to encode.
     * 
     * @return The {@link Class} this factory is designed to encode.
     */
    Class getClassToEncode();

    boolean canDecode(ByteBuffer in);
    }
