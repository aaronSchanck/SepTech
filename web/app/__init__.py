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

log = logging.getLogger(__name__)

db = SQLAlchemy()


def create_app(env=None):
    from app.config import config_by_name
    from app.routes import register_routes

    app = Flask(__name__)
    app.config.from_object(config_by_name[env or "test"])
    api = Api(app, title="Centauri API", version="0.1.0")

    register_routes(api, app)
    db.init_app(app)

    app.route("/", methods=["GET"])
    def index():
        return jsonify("healthy")

    return app
