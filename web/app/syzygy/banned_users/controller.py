"""/web/app/syzygy/banned_users/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    BannedUserResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    BannedUserIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import BannedUserInterface
from .model import BannedUser
from .schema import BannedUserSchema, BannedUserSchema
from .service import BannedUserService

api = Namespace("BannedUser")
log = logging.getLogger(__name__)


@api.route("/")
class BannedUserResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BannedUserSchema(many=True))
    def get(self):
        """Get all BannedUser"""

        return BannedUserService.get_all()

    @accepts(schema=BannedUserSchema, api=api)
    @responds(schema=BannedUserSchema)
    def post(self):
        """Create a Single BannedUser"""

        return BannedUserService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "BannedUser database ID")
class BannedUserIdResource(Resource):
    @responds(schema=BannedUserSchema)
    def get(self, id: int):
        """Get Single BannedUser"""

        return BannedUserService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single BannedUser"""
        from flask import jsonify

        id = BannedUserService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BannedUserSchema, api=api)
    @responds(schema=BannedUserSchema)
    def put(self, id: int):
        """Update Single BannedUser"""

        changes: BannedUserInterface = request.parsed_obj
        BannedUser = BannedUserService.get_by_id(id)
        return BannedUserService.update(BannedUser, changes)