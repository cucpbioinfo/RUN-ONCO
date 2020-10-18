#!/usr/bin/env python3

'''
Author          : Neda Peyrone
Create Date     : 19-09-2019
File            : app_api.py
Purpose         : -
'''
import logging

from flask import Blueprint, request, jsonify

from api.constants import app_constant as const
from api.utils import app_util

logger = logging.getLogger(__name__)

app_api = Blueprint('app_api', __name__)


@app_api.route('/executeJob', methods=['POST'])
def execute_job():
    logger.info('I:--START--:--Execute Job--')

    try:
        req = request.get_json()

        input_dir = "{0}/{1}".format(req['incomingDir'], req['dataDir'])
        output_dir = "{0}/{1}".format(req['outgoingDir'], req['dataDir'])
        microsate_file = req['params']['microsateFile']
        normal_bam_file = req['params']['normalBamFile']
        tumor_bam_file = req['params']['tumorBamFile']
        bed_file = req['params']['bedFile']
        output_file = req['params']['outputFile']

        s = "msisensor msi " \
            "-d {0}/{microsateFile} " \
            "-n {0}/{normalBamFile} " \
            "-t {0}/{tumorBamFile} " \
            "-e {0}/{bedFile} " \
            "-o {1}/{outputFile} " \
            "-l 1 -q 1 -b 2"

        command = s.format(input_dir,
                           output_dir,
                           microsateFile = microsate_file,
                           normalBamFile = normal_bam_file,
                           tumorBamFile = tumor_bam_file,
                           bedFile = bed_file,
                           outputFile = output_file)

        logger.info("O:--PrintOut Command--:cmd/{}".format(command))
        (out, errors) = app_util.run_cmd(command.split())
        logger.info('O:--SUCCESS--:--Execute Job--')
    except Exception as e:
        logger.info('O:--FAIL--:--Execute Job--:errorDesc/{}'.format(e))

    return jsonify({}), 200
