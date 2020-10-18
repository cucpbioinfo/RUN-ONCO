#!/usr/bin/env python3

'''
Author          : Neda Peyrone
Create Date     : 05-06-2019
File            : log.py
Purpose         : This python script is used to write log entries to a log file.
'''
import logging
import re
from logging.handlers import TimedRotatingFileHandler

from api.constants import app_constant as const

log_dir = const.LOG_DIR


def setup_logger():
    log_format = "%(asctime)s,%(msecs)d %(name)s %(levelname)s %(message)s"
    log_level = logging.INFO

    handler = TimedRotatingFileHandler("{}/MSISENSOR.log".format(log_dir), when="midnight", interval=1)
    handler.setLevel(log_level)
    formatter = logging.Formatter(log_format)
    handler.setFormatter(formatter)

    # add a suffix which you want
    handler.suffix = "%Y%m%d"

    # need to change the extMatch variable to match the suffix for it
    handler.extMatch = re.compile(r"^\d{8}$")

    logger = logging.getLogger()
    logger.setLevel(logging.DEBUG)
    logger.addHandler(handler)
    return logger
