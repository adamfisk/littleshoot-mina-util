package org.lastbamboo.common.util.mina;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Collection;
import java.util.LinkedList;

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
        final byte[] bytes = new byte[buf.remaining()];
        buf.get(bytes);
        return bytes;
        }
    
    /**
     * Splits the specified <code>ByteBuffer</code> into smaller 
     * <code>ByteBuffer</code>s of the specified size.  The remaining bytes 
     * in the buffer must be greater than the chunk size.  This method will
     * create smaller buffers of the specified size until there are fewer 
     * remaining bytes than the chunk size, when it will simply add a buffer
     * the same size as the number of bytes remaining.
     * 
     * @param buffer The <code>ByteBuffer</code> to split.
     * @param chunkSize The size of the smaller buffers to create.
     * @return A <code>Collection</code> of <code>ByteBuffer</code>s of the
     * specified size.  The final buffer in the <code>Collection</code> will
     * have a size > 0 and <= the chunk size.
     */
    public static Collection<ByteBuffer> split(final ByteBuffer buffer, 
        final int chunkSize)
        {
        final Collection<ByteBuffer> buffers = new LinkedList<ByteBuffer>();
        final int originalLimit = buffer.limit();
        
        int totalSent = 0;
        while ((totalSent + chunkSize) < originalLimit)
            {
            buffer.limit(totalSent + chunkSize);
            buffers.add(createBuffer(buffer));            
            totalSent += chunkSize;
            }
        
        // Send any remaining bytes.
        buffer.limit(originalLimit);
        buffers.add(createBuffer(buffer));
        return buffers;
        }
    
    private static ByteBuffer createBuffer(final ByteBuffer buffer)
        {
        // We calculate this here because the final split buffer will not
        // necessarily have a size equal to the chunk size -- it will
        // usually be smaller.
        final ByteBuffer data = ByteBuffer.allocate(
            buffer.limit() - buffer.position());
        
        data.put(buffer);
        data.flip();
        return data;
        }

    /**
     * Splits the specified <code>ByteBuffer</code> into smaller 
     * <code>ByteBuffer</code>s of the specified size.  The remaining bytes 
     * in the buffer must be greater than the chunk size.  This method will
     * create smaller buffers of the specified size until there are fewer 
     * remaining bytes than the chunk size, when it will simply add a buffer
     * the same size as the number of bytes remaining.
     * 
     * @param buffer The <code>ByteBuffer</code> to split.
     * @param chunkSize The size of the smaller buffers to create.
     * @return A <code>Collection</code> of <code>ByteBuffer</code>s of the
     * specified size.  The final buffer in the <code>Collection</code> will
     * have a size > 0 and <= the chunk size.
     */
    public static Collection<byte[]> splitToByteArrays(final ByteBuffer buffer, 
        final int chunkSize)
        {
        final Collection<byte[]> buffers = new LinkedList<byte[]>();
        final int originalLimit = buffer.limit();
        final int originalPosition = buffer.position();
        
        int totalSent = 0;
        while ((totalSent + chunkSize) < originalLimit)
            {
            buffer.limit(totalSent + chunkSize);
            
            // This will just read up to the limit and will increment the
            // position.
            buffers.add(toByteArray(buffer));            
            totalSent += chunkSize;
            }
        
        // Send any remaining bytes.
        buffer.limit(originalLimit);
        buffers.add(toByteArray(buffer));
        
        // Reset to beginning.
        buffer.position(originalPosition);
        buffer.limit(originalLimit);
        return buffers;
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
