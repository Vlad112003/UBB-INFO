import os
import sys
sys.path.append(os.getcwd() + '\\')

from UI.consola import *

repo = repo_emisiune('./data/emisiuni.txt')
srv = srv_emisiune(repo)
ui = Console(srv)

ui.start()