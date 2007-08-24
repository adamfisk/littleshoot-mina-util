package org.lastbamboo.common.util.mina;

/**
 * A {@link StateMachineProtocolDecoder} that can be used with a protocol
 * that should be dumultiplexed with other protocols.
 */
public class DemuxingStateMachineProtocolDecoder 
    extends StateMachineProtocolDecoder implements DemuxableProtocolDecoder
    {

    /**
     * Creates a new {@link DemuxingStateMachineProtocolDecoder}.
     * 
     * @param stateMachine The state machine that will transition between
     * states.
     */
    public DemuxingStateMachineProtocolDecoder(
        final DecodingStateMachine stateMachine)
        {
        super(stateMachine);
        }
    
    public boolean atMessageBoundary()
        {
        // The underlying state machine MUST always return null when at a 
        // message boundary for this to work.
        return this.m_currentState == null;
        }

    }
