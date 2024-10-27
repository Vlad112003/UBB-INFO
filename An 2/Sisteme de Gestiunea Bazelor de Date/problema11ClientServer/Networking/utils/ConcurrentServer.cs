using System.Net.Sockets;
using System.Threading;

namespace Networking.utils
{
    public abstract class ConcurrentServer : AbstractServer
    {
        public ConcurrentServer(string host, int port) : base(host, port)
        {
        }
        
        public override void processRequest(TcpClient client)
        {
            var t = createWorker(client);
            t.Start();
        }
        
        protected abstract Thread createWorker(TcpClient client);
    }
}