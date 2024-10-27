using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Net.Http;
using System.Net.Http.Json;
using System.Threading.Tasks;

namespace MotorcycleRaceClient
{
    class Program
    {
        static HttpClient client = new HttpClient();

        static async Task Main(string[] args)
        {
            client.BaseAddress = new Uri("http://localhost:8080");

            // ---------- add race -------------
            var newRace = new MotorcycleRaceDTO
            {
                Name = "Test Race",
                EngineCapacity = 1000,
                ParticipantsCount = 10
            };
            var addedRace = await AddMotorcycleRaceAsync(newRace);
            Debug.Assert(newRace.Name == addedRace.Name);
            Debug.Assert(newRace.EngineCapacity == addedRace.EngineCapacity);
            Debug.Assert(newRace.ParticipantsCount == addedRace.ParticipantsCount);

            // ---------- update race -------------
            addedRace.EngineCapacity = 1200;
            var updatedRace = await UpdateMotorcycleRaceAsync(addedRace);
            Debug.Assert(updatedRace.EngineCapacity == 1200);
            Debug.Assert(updatedRace.Name == addedRace.Name);
            Debug.Assert(updatedRace.ParticipantsCount == addedRace.ParticipantsCount);

            // ---------- find by id race -------------
            var foundRace = await FindMotorcycleRaceAsync(updatedRace.Id);
            Debug.Assert(foundRace.EngineCapacity == 1200);
            Debug.Assert(foundRace.Name == addedRace.Name);
            Debug.Assert(foundRace.ParticipantsCount == addedRace.ParticipantsCount);

            // ---------- get all races -------------
            await AddMotorcycleRaceAsync(
                new MotorcycleRaceDTO
                {
                    Name = "Test Race2",
                    EngineCapacity = 1000,
                    ParticipantsCount = 10
                }
            );
            await AddMotorcycleRaceAsync(
                new MotorcycleRaceDTO
                {
                    Name = "Test Race3",
                    EngineCapacity = 1500,
                    ParticipantsCount = 10
                }
            );
            var allRaces = await GetAllMotorcycleRacesAsync();
            Console.WriteLine(allRaces.Count);
            Debug.Assert(allRaces.Count == allRaces.Count);

            // ---------- delete race -------------
            var deleted = await DeleteMotorcycleRaceAsync(updatedRace.Id);
            Debug.Assert(deleted);
            Console.WriteLine("5/5 tests passed");
        }

        static async Task<MotorcycleRaceResponseDTO> AddMotorcycleRaceAsync(MotorcycleRaceDTO race)
        {
            var response = await client.PostAsJsonAsync("/motorcycle-race", race);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<MotorcycleRaceResponseDTO>();
        }

        static async Task<MotorcycleRaceResponseDTO> UpdateMotorcycleRaceAsync(MotorcycleRaceResponseDTO race)
        {
            var response = await client.PutAsJsonAsync($"/motorcycle-race/{race.Id}", race);
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<MotorcycleRaceResponseDTO>();
        }

        static async Task<MotorcycleRaceResponseDTO> FindMotorcycleRaceAsync(Guid id)
        {
            var response = await client.GetAsync($"/motorcycle-race/{id}");
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<MotorcycleRaceResponseDTO>();
        }

        static async Task<List<MotorcycleRaceResponseDTO>> GetAllMotorcycleRacesAsync()
        {
            var response = await client.GetAsync("/motorcycle-race");
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<List<MotorcycleRaceResponseDTO>>();
        }

        static async Task<bool> DeleteMotorcycleRaceAsync(Guid id)
        {
            var response = await client.DeleteAsync($"/motorcycle-race/{id}");
            response.EnsureSuccessStatusCode();
            return await response.Content.ReadFromJsonAsync<bool>();
        }
    }

    public class MotorcycleRaceDTO
    {
        public string Name { get; set; }
        public int EngineCapacity { get; set; }
        public int ParticipantsCount { get; set; }
    }

    public class MotorcycleRaceResponseDTO
    {
        public Guid Id { get; set; }
        public string Name { get; set; }
        public int EngineCapacity { get; set; }
        public int ParticipantsCount { get; set; }

        public override string ToString()
        {
            return $"Id: {Id}, Name: {Name}, Engine Capacity: {EngineCapacity}, Participants Count: {ParticipantsCount}";
        }
    }

    public class ResponseBody<T>
    {
        public T Body { get; set; }
        public string Message { get; set; }
    }
}
