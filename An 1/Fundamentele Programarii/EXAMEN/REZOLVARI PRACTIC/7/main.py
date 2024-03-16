import os
import sys
sys.path.append(os.getcwd() + "\\")

from UI.consola import *

repo = repo_contact('./data/contacte.txt')
validator = validators()
srv = srv_contact(repo,validator)
ui = Console(srv)

ui.start()