
from validare.validator_client import ValidatorClient
from validare.validator_carte import ValidatorCarte

from infrastructura.repo_clienti import RepoClienti
from infrastructura.repo_carti import RepoCarti

from business.service_clienti import ServiceClienti
from business.service_carti import ServiceCarti

from prezentare.consola import UI


if __name__ == '__main__':
    validator_client = ValidatorClient()
    validator_carte = ValidatorCarte()
    repo_clienti = RepoClienti()
    repo_carti = RepoCarti()
    service_clienti = ServiceClienti(validator_client, repo_clienti)
    service_carti = ServiceCarti(validator_carte, repo_carti, repo_clienti)
    consola = UI(service_clienti, service_carti)
    consola.run()