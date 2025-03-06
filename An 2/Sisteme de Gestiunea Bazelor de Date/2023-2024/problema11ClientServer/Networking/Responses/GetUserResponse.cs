using System;
using Model;
using Networking.DTOs;

namespace Networking.Responses
{
    [Serializable]
    public class GetUserResponse : Response
    {
        private UserDTO user { get; set; }
        
        public GetUserResponse(UserDTO user)
        {
            this.user = user;
        }
        
        public UserDTO User
        {
            get => user;
            set => user = value;
        }
        
        
    }
}