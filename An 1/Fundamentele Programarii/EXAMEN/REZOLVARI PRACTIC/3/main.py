import os
import sys
sys.path.append(os.getcwd() + "\\")

from UI.consola import *

repo = repo_melodie('./data/melodii.txt')
srv = srv_melodie(repo)
ui = Console(srv)

ui.start()