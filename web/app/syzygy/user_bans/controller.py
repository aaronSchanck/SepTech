"""/web/app/syzygy/user_bans/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    UserBanResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    UserBanIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import UserBan
from .schema import UserBanSchema
from .service import UserBanService

api = Namespace("UserBan")
log = logging.getLogger(__name__)


@api.route("/")
class UserBanResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=UserBanSchema(many=True))
    def get(self):
        """Get all UserBan entities"""

        return UserBanService.get_all()

    @accepts(schema=UserBanSchema, api=api)
    @responds(schema=UserBanSchema)
    def post(self):
        """Create a single UserBan entity"""

        return UserBanService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "UserBan database ID")
class UserBanIdResource(Resource):
    @responds(schema=UserBanSchema)
    def get(self, id: int):
        """Get single UserBan entity"""

        return UserBanService.get_by_id(id)

    def delete(self, id: int):
        """Delete single UserBan entity"""
        from flask import jsonify

        id = UserBanService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=UserBanSchema, api=api)
    @responds(schema=UserBanSchema)
    def put(self, id: int):
        """Update Single UserBan"""

        changes: UserBanInterface = request.parsed_obj
        UserBan = UserBanService.get_by_id(id)
        return UserBanService.update(UserBan, changes)