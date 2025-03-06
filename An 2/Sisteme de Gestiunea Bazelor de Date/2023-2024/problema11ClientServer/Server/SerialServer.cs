using System;
using System.Collections.Concurrent;
using System.Net.Sockets;
using System.Threading;
using Networking;
using Networking.utils;
using Services;

namespace Server;

public class SerialServer : ConcurrentServer
{
    private IService server;
    private ClientWorker worker;
    
    public SerialServer(string host, int port, IService server) : base(host, port)
    {
        this.server = server;
        Console.WriteLine("SerialChatServer...");
    }

    protected override Thread createWorker(TcpClient client)
    {
        worker = new ClientWorker(server, client);
        return new Thread(new ThreadStart(worker.Run));
    }
}