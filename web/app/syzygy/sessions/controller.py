"""/web/app/syzygy/sessions/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    SessionResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    SessionIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import SessionInterface
from .model import Session
from .schema import SessionSchema, SessionSchema
from .service import SessionService

api = Namespace("Session")
log = logging.getLogger(__name__)


@api.route("/")
class SessionResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=SessionSchema(many=True))
    def get(self):
        """Get all Sessions"""

        return SessionService.get_all()

    @accepts(schema=SessionSchema, api=api)
    @responds(schema=SessionSchema)
    def post(self):
        """Create a Single Session"""

        return SessionService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "Session database ID")
class SessionIdResource(Resource):
    @responds(schema=SessionSchema)
    def get(self, id: int):
        """Get Single Session"""

        return SessionService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single Session"""
        from flask import jsonify

        id = SessionService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=SessionSchema, api=api)
    @responds(schema=SessionSchema)
    def put(self, id: int):
        """Update Single Session"""

        changes: SessionInterface = request.parsed_obj
        Session = SessionService.get_by_id(id)
        return SessionService.update(Session, changes)