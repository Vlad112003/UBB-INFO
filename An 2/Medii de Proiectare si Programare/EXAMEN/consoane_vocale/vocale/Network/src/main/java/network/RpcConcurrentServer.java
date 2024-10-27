package network;

import network.rpcprotocol.RpcWorker;
import network.utils.AbstractConcurrentServer;
import services.IService;

import java.net.Socket;

public class RpcConcurrentServer extends AbstractConcurrentServer {
    private final IService service;

    public RpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.service = chatServer;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        RpcWorker worker = new RpcWorker(service, client);

        return new Thread(worker);
    }

    @Override
    public void stop() {
        System.out.println("Stopping services ...");
    }
}
