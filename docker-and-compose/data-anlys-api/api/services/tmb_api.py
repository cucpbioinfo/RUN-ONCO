#!/usr/bin/env python3

import time
import json

from flask import Blueprint, request, jsonify

from api.utils import data_preprocess as preprocessor

tmb_api = Blueprint('tmb_api', __name__)


@tmb_api.route('/calcTmbScore', methods=['POST'])
def calc_tmb_score():
    start_time = time.time()
    data = request.get_json()
    variants = preprocessor.tmb_score(data['items'], data['exomeSize'])
    print("--- %s seconds ---" % (time.time() - start_time))
    json_string = json.dumps(variants, default=lambda o: o.__dict__, ensure_ascii=False)
    return json_string, 200
