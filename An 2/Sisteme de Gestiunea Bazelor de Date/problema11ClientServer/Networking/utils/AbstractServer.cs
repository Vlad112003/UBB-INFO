using System;
using System.Net;
using System.Net.Sockets;

namespace Networking.utils
{
    public abstract class AbstractServer
    {
        private TcpListener server;
        private string host;
        private int port;
        
        public AbstractServer(string host, int port)
        {
            this.host = host;
            this.port = port;
        }
        
        public void Start()
        {
            var adr = IPAddress.Parse(host);
            var ep = new IPEndPoint(adr, port);
            server = new TcpListener(ep);
            server.Start();
            while (true)
            {
                Console.WriteLine("Waiting for clients ...");
                var client = server.AcceptTcpClient();
                Console.WriteLine("Client connected ...");
                processRequest(client);
            }
        }
        
        public abstract void processRequest(TcpClient client);
    }
}