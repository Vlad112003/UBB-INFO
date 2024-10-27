using System;

namespace Client;

public enum ParticipantAddedEvent
{
    ParticipantAdded
}

public class ParticipantAddedEventArgs : EventArgs
{
    private ParticipantAddedEvent _participantAddedEvent;
    private readonly Object data;

    public ParticipantAddedEventArgs(ParticipantAddedEvent participantAddedEvent, Object data)
    {
        this._participantAddedEvent = participantAddedEvent;
        this.data = data;
    }

    public ParticipantAddedEvent ParticipantAddedEvent
    {
        get { return _participantAddedEvent; }
    }

    public Object Data
    {
        get { return data; }
    }
    
}