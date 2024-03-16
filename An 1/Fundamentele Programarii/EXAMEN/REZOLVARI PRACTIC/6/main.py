import os
import sys
sys.path.append(os.getcwd() + "\\")

from UI.consola import *

repo = repo_jucator('./data/jucatori.txt')
validatorr = validator()
srv = srv_jucator(repo,validatorr)
ui = Console(srv)

ui.start()