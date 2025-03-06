using System;
using Model;

namespace Networking.DTOs;

public class DTOUtils
{
    public static User getFromDTO(UserDTO userDTO)
    {
        User user = new User(userDTO.username, userDTO.password);
        Guid id = Guid.NewGuid();
        user.Id = id;
        return user;
    }
    
    public static UserDTO getDTO(User user)
    {
        return new UserDTO(user.username, user.password);
    }
}