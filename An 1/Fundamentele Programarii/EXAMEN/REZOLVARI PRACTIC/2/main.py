import os
import sys
sys.path.append(os.getcwd() + "\\")

from UI.consola import *

repo_piese = repo_piesa('./data/piese.txt')
validator = PieseValidator()
srv_piese = srv_piesa(repo_piese,validator)
ui = Console(srv_piese)
ui.start()