"""/web/app/syzygy/config.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging
import os
from typing import List, Type

from conf.db_config import PROD_DB, DEV_DB, TEST_DB

log = logging.getLogger(__name__)

basedir = os.path.abspath(os.path.dirname(__file__))


class BaseConfig:
    CONFIG_NAME = "base"
    DEBUG = False
    SQLALCHEMY_TRACK_MODIFICATIONS = False


class DevelopmentConfig(BaseConfig):
    CONFIG_NAME = "dev"
    SECRET_KEY = os.getenv(
        "DEV_SECRET_KEY", "Be yourself; everyone else is already taken."
    )
    DEBUG = True
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    TESTING = False
    MAX_CONTENT_LENGTH = 16 * 1024 * 1024
    SQLALCHEMY_DATABASE_URI = os.getenv("DATABASE_URI") or DEV_DB
    UPLOAD_FOLDER = "images"
    ALLOWED_EXTENSIONS = {"txt", "pdf", "png", "jpg", "jpeg", "gif"}


class ProductionConfig(BaseConfig):
    CONFIG_NAME = "prod"
    SECRET_KEY = os.getenv(
        "DEV_SECRET_KEY",
        "It is better to be hated for what you are than to be loved for what you are not.",
    )
    DEBUG = False
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    TESTING = False
    MAX_CONTENT_LENGTH = 16 * 1024 * 1024
    SQLALCHEMY_DATABASE_URI = os.getenv("DATABASE_URI") or PROD_DB
    UPLOAD_FOLDER = "images"
    ALLOWED_EXTENSIONS = {"txt", "pdf", "png", "jpg", "jpeg", "gif"}


class TestingConfig(BaseConfig):
    CONFIG_NAME = "test"
    SECRET_KEY = os.getenv(
        "DEV_SECRET_KEY",
        "Live as if you were to die tomorrow. Learn as if you were to live forever.",
    )
    DEBUG = True
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    TESTING = True
    MAX_CONTENT_LENGTH = 16 * 1024 * 1024
    SQLALCHEMY_DATABASE_URI = os.getenv("DATABASE_URI") or TEST_DB
    UPLOAD_FOLDER = "images"
    ALLOWED_EXTENSIONS = {"txt", "pdf", "png", "jpg", "jpeg", "gif"}


CONFIGS: List[Type[BaseConfig]] = [
    DevelopmentConfig,
    ProductionConfig,
    TestingConfig,
]

config_by_name = {cfg.CONFIG_NAME: cfg for cfg in CONFIGS}
