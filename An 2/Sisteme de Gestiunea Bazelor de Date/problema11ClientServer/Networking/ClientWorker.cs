using System;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Model;
using Networking.DTOs;
using Networking.Requests;
using Networking.Responses;
using Services;

namespace Networking;

public class ClientWorker : IObserver
{
    private IService server;
    private TcpClient connection;

    private NetworkStream stream;
    private IFormatter formatter;

    private volatile bool connected;

    public ClientWorker(IService server, TcpClient connection)
    {
        this.server = server;
        this.connection = connection;
        try
        {
            stream = connection.GetStream();
            formatter = new BinaryFormatter();
            connected = true;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }

    public virtual void Run()
    {
        while (connected)
        {
            try
            {
                var request = formatter.Deserialize(stream);
                object response = HandleRequest((Request)request);
                if (response != null) SendResponse((Response)response);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

            // try
            // {
            //     Thread.Sleep(1000);
            // }
            // catch (Exception e)
            // {
            //     Console.WriteLine(e.StackTrace);
            // }
        }

        try
        {
            stream.Close();
            connection.Close();
        }
        catch (Exception e)
        {
            Console.WriteLine("Error " + e);
        }
    }

    private Response HandleRequest(Request request)
    {
        Response response = null;
        if (request is LoginRequest)
        {
            Console.WriteLine("Login request ...");
            var loginRequest = (LoginRequest)request;
            User user = DTOUtils.getFromDTO(loginRequest.User);
            try
            {
                bool result;
                lock (server)
                {
                    result = server.Login(user.username, user.password, this);
                }

                if (result)
                {

                    return new OkResponse();
                }
                else
                {
                    return new ErrorResponse("Authentication failed");
                }

            }
            catch (Exception e)
            {
                connected = false;
                return new ErrorResponse(e.Message);
            }
        }

        if (request is LogoutRequest)
        {
            Console.WriteLine("Logout request ...");
            var logoutRequest = (LogoutRequest)request;
            User user = DTOUtils.getFromDTO(logoutRequest.User);
            try
            {
                lock (server)
                {
                    server.Logout(user, this);
                }

                return new OkResponse();
            }
            catch (Exception e)
            {
                connected = false;
                return new ErrorResponse(e.Message);
            }
        }

        if (request is GetUserRequest)
            {
                Console.WriteLine("GetUser request ...");
                var getUserRequest = (GetUserRequest)request;
                string username = getUserRequest.Username;
                try
                {
                    User user;
                    lock (server)
                    {
                        user = server.getUserByUsername(username);
                    }
                    
                    return new GetUserResponse(DTOUtils.getDTO(user));

                }
                catch (Exception e)
                {
                    connected = false;
                    return new ErrorResponse(e.Message);
                }

            
        }
        return response;
    }


private void SendResponse(Response response)
    {
        Console.WriteLine("sending response " + response);
        lock (stream)
        {
            formatter.Serialize(stream, response);
            stream.Flush();
        }
    }
    
    
    public void RaceAdded()
    {
        // try
        // {
        //     SendResponse(new RacesUpdatedResponse());
        // }
        // catch (Exception e)
        // {
        //     Console.WriteLine(e.StackTrace);
        // }
    }
}