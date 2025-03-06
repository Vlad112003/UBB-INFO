using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Threading;
using Model;
using Networking.DTOs;
using Networking.Requests;
using Networking.Responses;
using Services;
using Services.DTOs;

namespace Networking
{
    public class ServerProxy : IService 
    {

        private string host;
        private int port;

        private IObserver client;
        private NetworkStream stream;
        
        private IFormatter formatter;
        private TcpClient connection;
        
        private Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle _waitHandle;

        public ServerProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }
        
        
        public bool Login(string username, string password, IObserver client)
        {
            InitializeConnection();
            var user = new UserDTO(username, password);
            SendRequest(new LoginRequest(user));
            var response = ReadResponse();
            if (response is OkResponse)
            {
                this.client = client;
                return true;
            }

            if (response is ErrorResponse)
            {
                var err = (ErrorResponse)response;
                CloseConnection();
                throw new Exception(err.Message);
            }

            return false;
        }
        
        private void CloseConnection()
        {
            finished = true;
            try
            {
                stream.Close();

                connection.Close();
                _waitHandle.Close();
                client = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        
        private Response ReadResponse()
        {
            Response response = null;
            try
            {
                _waitHandle.WaitOne();
                lock (responses)
                {
                    // Monitor.Wait(responses); 
                    response = responses.Dequeue();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }

            return response;
        }
        
        private void SendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new Exception("Error sending object " + e);
            }
        }
        
        
        private void InitializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                _waitHandle = new AutoResetEvent(false);
                StartReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        
        private void StartReader()
        {
            var tw = new Thread(Run);
            tw.Start();
        }

        public virtual void Run()
        {
            while (!finished)
                try
                {
                    var response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (response is RacesUpdatedResponse)
                    {
                        HandleUpdate((RacesUpdatedResponse)response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((Response)response);
                        }

                        _waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }
        }
        
        private void HandleUpdate(RacesUpdatedResponse response)
        {
            try
            {
                client.RaceAdded();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        
        public User GetUserByUsername(string username)
        {
            Request request = new GetUserRequest(username);
            SendRequest(request);
            var response = ReadResponse();
            if (response is GetUserResponse)
            {
                var userResponse = (GetUserResponse)response;
                var user = DTOUtils.getFromDTO(userResponse.User);
                return user;
            }

            if (response is ErrorResponse)
            {
                var err = (ErrorResponse)response;
                throw new Exception(err.Message);
            }

            return null;
        }
        

        public void Logout(User user, IObserver client)
        {
            var userDTO = DTOUtils.getDTO(user);
            Request request = new LogoutRequest(userDTO);
            SendRequest(request);
            var response = ReadResponse();
            CloseConnection();
            if (response is ErrorResponse)
            {
                var err = (ErrorResponse)response;
                throw new Exception(err.Message);
            }
        }

        public IEnumerable<Race> getRaces()
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable<Entry> getEntries()
        {
            throw new System.NotImplementedException();
        }

        public User getUser(string username, string password)
        {
            var user = GetUserByUsername(username);
            return user;
        
        }

        public IEnumerable<Networking.DTOs.RaceDTO> racesDTO()
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable<Participant> getParticipants()
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable participantsDTO()
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable<string> getTeams()
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable getParticipantDTOforTeam(string team)
        {
            return null;
        }

        public IEnumerable getRacesDTO()
        {
            // SendRequest();
            return null;
        }

        public IEnumerable getEngineCapacities()
        {
            return null;
        }

        public User getUserByUsername(string username)
        {
            Request request = new GetUserRequest(username);
            SendRequest(request);
            var response = ReadResponse();
            if (response is GetUserResponse)
            {
                var userResponse = (GetUserResponse)response;
                var user = DTOUtils.getFromDTO(userResponse.User);
                return user;
            }
            
            if (response is ErrorResponse)
            {
                var err = (ErrorResponse)response;
                throw new Exception(err.Message);
            }

            return null;
        }

        public void addParticipant(string username, string team, string engine)
        {
            throw new System.NotImplementedException();
        }

        public IEnumerable<string> engineCapacities()
        {
            IEnumerable<string> capacities = new List<string>();
            capacities.Append("500");
            capacities.Append("1000");
            capacities.Append("750");
            return capacities;
        }

        public IEnumerable<string> capacities()
        {
            IEnumerable<string> capacities = new List<string>();
            capacities.Append("500");
            capacities.Append("1000");
            capacities.Append("750");
            return capacities;
        }

        public Participant getParticipant(string name, string team, string engine)
        {
            return null;
        }

        public void createEntry(Participant p)
        {
            throw new System.NotImplementedException();
        }
    }
}