package org.lastbamboo.common.util.mina;

import org.apache.mina.common.ByteBuffer;
import org.lastbamboo.common.util.UByte;
import org.lastbamboo.common.util.UByteImpl;
import org.lastbamboo.common.util.UInt;
import org.lastbamboo.common.util.UIntImpl;
import org.lastbamboo.common.util.UShort;
import org.lastbamboo.common.util.UShortImpl;

/**
 * An implementation of the byte buffer extension interface.
 */
public final class ByteBufferExtImpl implements ByteBufferExt
    {
    /**
     * The delegate byte buffer.
     */
    private final ByteBuffer m_delegate;
    
    /**
     * Constructs a new byte buffer extension.
     * 
     * @param delegate
     *      The underlying delegate byte buffer.
     */
    public ByteBufferExtImpl
            (final ByteBuffer delegate)
        {
        m_delegate = delegate;
        }
    
    /**
     * {@inheritDoc}
     */
    public ByteBufferExt flip
            ()
        {
        m_delegate.flip ();
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public byte get
		    ()
        {
        return m_delegate.get ();
        }

    /**
     * {@inheritDoc}
     */
    public ByteBuffer getDelegate
            ()
        {
        return m_delegate;
        }

    /**
     * {@inheritDoc}
     */
    public UByte getUByte
            ()
        {
        return new UByteImpl (m_delegate.get ());
        }

    /**
     * {@inheritDoc}
     */
    public UByte getUByte
            (final int index)
        {
        return new UByteImpl (m_delegate.get (index));
        }

    /**
     * {@inheritDoc}
     */
    public UInt getUInt
            ()
        {
        return new UIntImpl (m_delegate.getInt ());
        }

    /**
     * {@inheritDoc}
     */
    public UInt getUInt
            (final int index)
        {
        return new UIntImpl (m_delegate.getInt (index));
        }

    /**
     * {@inheritDoc}
     */
    public UShort getUShort
            ()
        {
        return new UShortImpl (m_delegate.getShort ());
        }

    /**
     * {@inheritDoc}
     */
    public UShort getUShort
            (final int index)
        {
        return new UShortImpl (m_delegate.getShort (index));
        }

    /**
     * {@inheritDoc}
     */
    public boolean hasRemaining
            ()
        {
        return m_delegate.hasRemaining ();
        }

    /**
     * {@inheritDoc}
     */
    public int limit
            ()
        {
        return m_delegate.limit ();
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt limit
            (final int newLimit)
        {
        m_delegate.limit (newLimit);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public int position
            ()
        {
        return m_delegate.position ();
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt position
            (final int newPosition)
        {
        m_delegate.position (newPosition);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt put
            (final byte b)
        {
        m_delegate.put (b);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt put
            (final byte[] src)
        {
        m_delegate.put (src);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putInt
            (final int i)
        {
        m_delegate.putInt (i);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putInt
            (final int index,
             final int i)
        {
        m_delegate.putInt (index, i);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putShort
            (final int index,
             final short s)
        {
        m_delegate.putInt (index, s);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putShort
            (final short s)
        {
        m_delegate.putShort (s);
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putUByte
            (final int index,
             final UByte b)
        {
        m_delegate.put (index, b.toByte ());
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putUByte
            (final UByte b)
        {
        m_delegate.put (b.toByte ());
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putUInt
            (final int index,
             final UInt i)
        {
        m_delegate.putInt (index, i.toInt ());
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putUInt
            (final UInt i)
        {
        m_delegate.putInt (i.toInt ());
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putUShort
            (final int index,
             final UShort s)
        {
        m_delegate.putShort (index, s.toShort ());
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt putUShort
            (final UShort s)
        {
        m_delegate.putShort (s.toShort ());
        return this;
        }

    /**
     * {@inheritDoc}
     */
    public ByteBufferExt get
            (final byte[] dst)
        {
        m_delegate.get (dst);
        return this;
        }
    }
