using System;

namespace Networking.Requests;

[Serializable]
public class GetUserRequest : Request
{
    private string username;

    public GetUserRequest(string username)
    {
        this.username = username;
    }
    
    public string Username
    {
        get => username;
        set => username = value;
    }
}