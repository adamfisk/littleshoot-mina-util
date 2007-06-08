package org.lastbamboo.common.util.mina;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.common.ByteBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Apache MINA utility functions.
 */
public class MinaUtils
    {
    
    private static final Logger LOG = LoggerFactory.getLogger(MinaUtils.class);
    
    private static final CharsetDecoder DECODER =
        Charset.forName("US-ASCII").newDecoder();

    /**
     * Useful for debugging.  Turns the given buffer into an ASCII string.
     * 
     * @param buf The buffer to convert to a string.
     * @return The string.
     */
    public static String toAsciiString(final ByteBuffer buf)
        {
        DECODER.reset();
        final int position = buf.position();
        final int limit = buf.limit();
        try
            {
            final String bufString = buf.getString(DECODER);
            buf.position(position);
            buf.limit(limit);
            return bufString;
            }
        catch (final CharacterCodingException e)
            {
            LOG.error("Could not decode: "+buf, e);
            return StringUtils.EMPTY;
            }
        }

    /**
     * Copies the specified buffer to a byte array.
     * 
     * @param buf The buffer to copy.
     * @return The byte array.
     */
    public static byte[] toByteArray(final ByteBuffer buf)
        {
        final byte[] bytes = new byte[buf.capacity()];
        buf.get(bytes);
        return bytes;
        }


    /**
     * Puts an unsigned byte into the buffer.
     * 
     * @param bb The buffer.
     * @param value The value to insert.
     */
    public static void putUnsignedByte(final ByteBuffer bb, final int value)
        {
        bb.put((byte) (value & 0xff));
        }

    /**
     * Puts an unsigned byte into the buffer.
     * 
     * @param bb The buffer.
     * @param position The index in the buffer to insert the value.
     * @param value The value to insert.
     */
    public static void putUnsignedByte(final ByteBuffer bb, final int position, 
        final int value)
        {
        bb.put(position, (byte) (value & 0xff));
        }

    /**
     * Puts an unsigned byte into the buffer.
     * 
     * @param bb The buffer.
     * @param value The value to insert.
     */
    public static void putUnsignedShort(final ByteBuffer bb, final int value)
        {
        bb.putShort((short) (value & 0xffff));
        }

    /**
     * Puts an unsigned byte into the buffer.
     * 
     * @param bb The buffer.
     * @param position The index in the buffer to insert the value.
     * @param value The value to insert.
     */
    public static void putUnsignedShort(final ByteBuffer bb, final int position, 
        final int value)
        {
        bb.putShort(position, (short) (value & 0xffff));
        }

    /**
     * Puts an unsigned byte into the buffer.
     * 
     * @param bb The buffer.
     * @param value The value to insert.
     */
    public static void putUnsignedInt(final ByteBuffer bb, final long value)
        {
        bb.putInt((int) (value & 0xffffffffL));
        }

    /**
     * Puts an unsigned byte into the buffer.
     * 
     * @param bb The buffer.
     * @param position The index in the buffer to insert the value.
     * @param value The value to insert.
     */
    public static void putUnsignedInt(final ByteBuffer bb, final int position, 
        final long value)
        {
        bb.putInt(position, (int) (value & 0xffffffffL));
        }
    }
