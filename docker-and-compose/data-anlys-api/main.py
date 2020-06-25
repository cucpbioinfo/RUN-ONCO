#!/usr/bin/env python3
import sys

from flask import Flask

from api.services import clustergrammer_api
from api.services import cystoscape_api
from api.services import tmb_api

app = Flask(__name__)

app.register_blueprint(clustergrammer_api)
app.register_blueprint(cystoscape_api)
app.register_blueprint(tmb_api)

if __name__ == '__main__':
    try:
        port = int(sys.argv[1])
    except Exception as e:
        port = 5000

    app.run(host='0.0.0.0', port=port, debug=True)
