#!/usr/bin/env python3
import sys

from flask import Flask

from api.services import app_api
from api.utils.log import setup_logger

logger = setup_logger()

app = Flask(__name__)

app.register_blueprint(app_api)

if __name__ == '__main__':
    try:
        port = int(sys.argv[1])
    except Exception as e:
        port = 5000

    app.run(host='0.0.0.0', port=port, debug=False, use_reloader=False)
