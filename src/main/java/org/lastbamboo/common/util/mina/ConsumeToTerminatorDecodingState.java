/*
 * Copyright 2006 The asyncWeb Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lastbamboo.common.util.mina;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


/**
 * Consumes until a fixed (ASCII) character is reached.
 * The terminator is skipped.
 * 
 * @author irvingd
 * @author trustin
 * @version $Rev: 215 $, $Date: 2006-11-27 01:13:35 -0500 (Mon, 27 Nov 2006) $
 */
public abstract class ConsumeToTerminatorDecodingState implements DecodingState 
    {
    
    private ByteBuffer m_buffer;

    private final byte m_terminator1;

    private final byte m_terminator2;

    private byte m_foundTerminator;

    /**
     * Creates a new instance.
     * 
     * @param terminator The terminator.
     */
    protected ConsumeToTerminatorDecodingState(final byte terminator)
        {
        m_terminator1 = terminator;
        m_terminator2 = -1;
        }
    
    /**
     * Creates a new instance.
     * 
     * @param terminator The terminator.
     */
    protected ConsumeToTerminatorDecodingState(final byte terminator1,
        final byte terminator2)
        {
        m_terminator1 = terminator1;
        m_terminator2 = terminator2;
        }

    public DecodingState decode(final ByteBuffer in, 
        final ProtocolDecoderOutput out) throws Exception
        {
        int beginPos = in.position();
        int terminatorPos = -1;
        int limit = in.limit();

        for (int i = beginPos; i < limit; i++)
            {
            byte b = in.get(i);
            if (b == this.m_terminator1 || b == this.m_terminator2)
                {
                this.m_foundTerminator = b;
                terminatorPos = i;
                break;
                }
            }

        if (terminatorPos >= 0)
            {
            final ByteBuffer product;

            if (beginPos < terminatorPos)
                {
                in.limit(terminatorPos);

                if (m_buffer == null)
                    {
                    product = in.slice();
                    }
                else
                    {
                    m_buffer.put(in);
                    product = m_buffer.flip();
                    m_buffer = null;
                    }

                in.limit(limit);
                }
            else
                {
                // When input contained only terminator rather than actual
                // data...
                if (m_buffer == null)
                    {
                    product = ByteBuffer.allocate(1);
                    product.limit(0);
                    }
                else
                    {
                    product = m_buffer.flip();
                    m_buffer = null;
                    }
                }
            
            in.position(terminatorPos + 1);
            return finishDecode(this.m_foundTerminator, product, out);
            }
        else
            {
            if (m_buffer == null)
                {
                m_buffer = ByteBuffer.allocate(in.remaining());
                m_buffer.setAutoExpand(true);
                }
            m_buffer.put(in);
            return this;
            }
        }

    protected abstract DecodingState finishDecode(byte foundTerminator,
        ByteBuffer product, ProtocolDecoderOutput out) throws Exception;
    }
