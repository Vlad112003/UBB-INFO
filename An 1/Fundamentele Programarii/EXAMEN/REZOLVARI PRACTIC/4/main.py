import os
import sys
sys.path.append(os.getcwd() + "\\")

from UI.consola import *

repo = repo_carte('./data/carti.txt')
srv = srv_carte(repo)
ui = Console(srv)

ui.start()