#!/usr/bin/env python3

import sys
import urllib.error
import urllib.request

from flask import Blueprint, request, jsonify

from api.constants import constants
from api.utils import app_util

cystoscape_api = Blueprint('cystoscape_api', __name__)


@cystoscape_api.route('/getInteractions', methods=['POST'])
def get_interactions():
    req = request.get_json()
    genes = req['geneList']
    types = req['evidenceTypes']

    dict = {'format': 'tsv-no-header',
            'method': 'network',
            'genes': "%0d".join(genes),
            'species': '9606',
            'app': 'omics-analytics-project'
            }

    url = constants.STRINGDB_REQUEST_TEMPLATE.format(dict['format'], dict['method'], dict['genes'], dict['species'],
                                                     dict['app'])
    print(url)

    try:
        response = urllib.request.urlopen(url)
    except urllib.error.URLError as err:
        error_message = err.read()
        print(error_message)
        sys.exit()
    network_data = [x.decode('utf8').strip().split("\t") for x in response.readlines()]

    # print (lines)
    output = app_util.cystoscape(network_data, types)

    return jsonify(output), 200
