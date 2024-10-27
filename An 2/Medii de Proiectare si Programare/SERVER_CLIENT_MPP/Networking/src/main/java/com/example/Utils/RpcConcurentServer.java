package com.example.Utils;

import com.example.IService;
import com.example.rpcProtocol.RpcReflectionWorker;

import java.net.Socket;

public class RpcConcurentServer extends AbsConcurrentServer {
    private final IService agentieServer;

    public RpcConcurentServer(int port, IService agentieServer) {
        super(port);
        this.agentieServer = agentieServer;
        System.out.println("Agentie- AgentieRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        RpcReflectionWorker worker = new RpcReflectionWorker(agentieServer, client);
        return new Thread(worker);
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}