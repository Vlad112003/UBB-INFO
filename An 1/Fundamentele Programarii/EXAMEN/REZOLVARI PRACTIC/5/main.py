import os
import sys
sys.path.append(os.getcwd() + "\\")

from UI.consola import *

repo = repo_produs('./data/produse.txt')
srv = srv_produs(repo)
ui = Consola(srv)

ui.start()