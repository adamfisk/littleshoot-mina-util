package org.lastbamboo.common.util.mina;

import org.littleshoot.mina.common.ByteBuffer;
import org.lastbamboo.common.util.UByte;
import org.lastbamboo.common.util.UInt;
import org.lastbamboo.common.util.UShort;

/**
 * An extension to byte buffers that provides handling of unsigned values.  This
 * is a wrapper around a MINA byte buffer.  However, since
 * <code>ByteBuffer</code> is not an interface, we have to duplicate a lot of
 * interface declarations here.
 */
public interface ByteBufferExt
    {
    /**
     * Flips this buffer.
     * 
     * @see ByteBuffer#flip()
     * 
     * @return
     *      This buffer.
     */
    ByteBufferExt flip
            ();
    
    /**
     * Returns the next byte in this buffer.
     * 
     * @see ByteBuffer#get()
     * 
     * @return
     *      The next byte in this buffer.
     */
    byte get
            ();
    
    /**
     * Transfers bytes from this buffer into a given destination array.
     * 
     * @see ByteBuffer#get(byte[])
     * 
     * @param dst
     *      The destination buffer.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt get
            (byte[] dst);
    
    /**
     * Returns the underlying <code>ByteBuffer</code>.  We need this method
     * because of the fact that <code>ByteBuffer</code> is implemented as a
     * class.  Some algorithms may rely on a <code>ByteBuffer</code> type.  This
     * method should only be used in such cases.
     * 
     * @return
     *      The underlying <code>ByteBuffer</code>.
     */
    ByteBuffer getDelegate
            ();
    
    /**
     * Returns the next unsigned byte in this buffer.  The position is
     * incremented by 1.
     * 
     * @return
     *      The next unsigned byte in this buffer.
     */
    UByte getUByte
            ();
    
    /**
     * Returns the unsigned byte at a given absolute index.  The position of
     * this buffer is unchanged.
     * 
     * @param index
     *      The index.
     *      
     * @return
     *      The unsigned byte at a given absolute index.
     */
    UByte getUByte
            (int index);
    
    /**
     * Returns the next unsigned integer in this buffer.  The position is
     * incremented by 4.
     * 
     * @return
     *      The next unsigned integer in this buffer.
     */
    UInt getUInt
            ();
    
    /**
     * Returns the unsigned integer at a given absolute index.  The position of
     * this buffer is unchanged.
     * 
     * @param index
     *      The index.
     *      
     * @return
     *      The unsigned integer at a given absolute index.
     */
    UInt getUInt
            (int index);
    
    /**
     * Returns the next unsigned short in this buffer.  The position is
     * incremented by 2.
     * 
     * @return
     *      The next unsigned short in this buffer.
     */
    UShort getUShort
            ();
    
    /**
     * Returns the unsigned short at a given absolute index.  The position of
     * this buffer is unchanged.
     * 
     * @param index
     *      The index.
     *      
     * @return
     *      The unsigned short at a given absolute index.
     */
    UShort getUShort
            (int index);
    
    /**
     * Returns whether this buffer still has elements between the current
     * position and limit.
     * 
     * @see ByteBuffer#hasRemaining()
     * 
     * @return
     *      Whether this buffer still has elements between the current position
     *      and limit.
     */
    boolean hasRemaining
            ();
    
    /**
     * Returns the limit of this buffer.
     * 
     * @see ByteBuffer#limit()
     * 
     * @return
     *      The limit of this buffer.
     */
    int limit
            ();
    
    /**
     * Sets the limit of this buffer.
     * 
     * @see ByteBuffer#limit(int)
     * 
     * @param newLimit
     *      The new limit.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt limit
            (int newLimit);
    
    /**
     * Returns the position of this buffer.
     * 
     * @see ByteBuffer#position()
     * 
     * @return
     *      The position of this buffer.
     */
    int position
            ();
    
    /**
     * Sets the position of this buffer.
     * 
     * @see ByteBuffer#position(int)
     * 
     * @param newPosition
     *      The new position.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt position
            (int newPosition);
    
    /**
     * Puts a byte into this buffer.  The position is incremeted by 1.
     * 
     * @see ByteBuffer#put(byte)
     * 
     * @param b
     *      The byte.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt put
            (byte b);
    
    /**
     * Puts a byte array into this buffer.  The position is incremeted by the
     * length of the byte array.
     * 
     * @see ByteBuffer#put(byte[])
     * 
     * @param src
     *      The byte array.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt put
            (byte[] src);
    
    /**
     * Puts an integer into this buffer.  The position is incremeted by 4.
     * 
     * @see ByteBuffer#putInt(int)
     * 
     * @param i
     *      The integer.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putInt
            (int i);
    
    /**
     * Puts an integer into this buffer at an absolute index.  The position is
     * unchanged.
     * 
     * @see ByteBuffer#putInt(int,int)
     * 
     * @param index
     *      The index.
     * @param i
     *      The integer.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putInt
            (int index,
             int i);
    
    /**
     * Puts a short into this buffer at an absolute index.  The position is
     * unchanged.
     * 
     * @see ByteBuffer#putShort(int,short)
     * 
     * @param index
     *      The index.
     * @param s
     *      The short.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putShort
            (int index,
             short s);
    
    /**
     * Puts an integer into this buffer.  The position is incremeted by 2.
     * 
     * @see ByteBuffer#putShort(short)
     * 
     * @param s
     *      The short.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putShort
            (short s);
    
    /**
     * Puts an unsigned byte into this buffer at an absolute index.  The
     * position is unchanged.
     * 
     * @param index
     *      The index.
     * @param b
     *      The unsigned byte.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putUByte
            (int index,
             UByte b);
    
    /**
     * Puts an unsigned byte into this buffer.  The position is incremeted by 1.
     * 
     * @param b
     *      The unsigned byte.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putUByte
            (UByte b);
    
    /**
     * Puts an unsigned integer into this buffer at an absolute index.  The
     * position is unchanged.
     * 
     * @param index
     *      The index.
     * @param i
     *      The unsigned integer.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putUInt
            (int index,
             UInt i);
    
    /**
     * Puts an unsigned integer into this buffer.  The position is incremeted by
     * 4.
     * 
     * @param i
     *      The unsigned integer.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putUInt
            (UInt i);
    
    /**
     * Puts an unsigned short into this buffer at an absolute index.  The
     * position is unchanged.
     * 
     * @param index
     *      The index.
     * @param s
     *      The unsigned short.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putUShort
            (int index,
             UShort s);
    
    /**
     * Puts an unsigned short into this buffer.  The position is incremeted by
     * 2.
     * 
     * @param s
     *      The unsigned short.
     *      
     * @return
     *      This buffer.
     */
    ByteBufferExt putUShort
            (UShort s);
    }
