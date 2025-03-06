using System;
using Model;

namespace Persistence;

public interface UserRepoInterface : RepoInterface<Guid, User>
{
    
}