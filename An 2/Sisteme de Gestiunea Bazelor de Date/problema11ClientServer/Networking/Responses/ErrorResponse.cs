using System;

namespace Networking.Responses
{
    [Serializable]
    public class ErrorResponse : Response
    {
        private string message;

        public ErrorResponse(string message)
        {
            this.message = message;
        }

        public string Message
        {
            get => message;
            set => message = value;
        }
        
        
    }
}