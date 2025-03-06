using System;

namespace Model
{
    public class User : Entity<Guid>
    {
        public string username { get; set; }
        public string password { get; set; }
        
        public User(string username, string password)
        {
            this.username = username;
            this.password = password;
            Id = Guid.NewGuid();
        }
        
        public User(string id, string username, string password)
        {
            this.username = username;
            this.password = password;
            Guid guid = Guid.Parse(id);
            Id = guid;
        }
        
        public User(Guid id, string username, string password)
        {
            this.username = username;
            this.password = password;
            Id = id;
        }

        public override string ToString()
        {
            return $"{nameof(Id)}: {Id}, {nameof(username)}: {username}, {nameof(password)}: {password}";
        }
    }
}