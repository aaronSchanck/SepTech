"""/web/app/syzygy/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from flask import Flask, jsonify
from flask_restx import Api
from flask_sqlalchemy import SQLAlchemy

from utils.images import setup_image_store
from app.images import setup_syzygy_image_store

log = logging.getLogger(__name__)

db = SQLAlchemy()

app = None


def create_app(env=None):
    from app.config import config_by_name
    from app.routes import register_routes

    global app

    app = Flask(__name__)
    app.config.from_object(config_by_name[env or "test"])
    api = Api(app, title="Centauri API", version="0.3.0")

    path = setup_image_store()

    setup_syzygy_image_store(path)

    register_routes(api, app)
    db.init_app(app)

    app.route("/", methods=["GET"])

    def index():
        return jsonify("healthy")

    return app
