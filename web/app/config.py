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

log = logging.getLogger(__name__)


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
    SQLALCHEMY_DATABASE_URI = (
        os.getenv("DATABASE_URI")
        or "postgres://postgres:cos420umaine@localhost:5444/syzygy"
    )


class ProductionConfig(BaseConfig):
    CONFIG_NAME = "prod"
    SECRET_KEY = os.getenv(
        "DEV_SECRET_KEY",
        "It is better to be hated for what you are than to be loved for what you are not.",
    )
    DEBUG = False
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    TESTING = False
    SQLALCHEMY_DATABASE_URI = (
        os.getenv("DATABASE_URI")
        or "postgres://postgres:cos420umaine@localhost:5444/syzygy"
    )


class TestingConfig(BaseConfig):
    CONFIG_NAME = "test"
    SECRET_KEY = os.getenv(
        "DEV_SECRET_KEY",
        "Live as if you were to die tomorrow. Learn as if you were to live forever.",
    )
    DEBUG = True
    SQLALCHEMY_TRACK_MODIFICATIONS = False
    TESTING = True
    SQLALCHEMY_DATABASE_URI = (
        os.getenv("DATABASE_URI")
        or "postgres://postgres:cos420umaine@localhost:5444/syzygy"
    )


CONFIGS: List[Type[BaseConfig]] = [
    DevelopmentConfig,
    ProductionConfig,
    TestingConfig,
]

config_by_name = {cfg.CONFIG_NAME: cfg for cfg in CONFIGS}
