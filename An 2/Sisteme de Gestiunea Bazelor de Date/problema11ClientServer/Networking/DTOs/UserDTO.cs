using System;

namespace Networking.DTOs;

[Serializable]
public class UserDTO
{
    public string username { get; set; }
    public string password { get; set; }
    

    public UserDTO(string username, string password)
    {
        this.username = username;
        this.password = password;
    }
}