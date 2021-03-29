"""/web/app/syzygy/banned/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    BannedResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    BannedIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import BannedInterface
from .model import Banned
from .schema import BannedSchema, BannedSchema
from .service import BannedService

api = Namespace("Banned")
log = logging.getLogger(__name__)


@api.route("/")
class BannedResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BannedSchema(many=True))
    def get(self):
        """Get all Banneds"""

        return BannedService.get_all()

    @accepts(schema=BannedSchema, api=api)
    @responds(schema=BannedSchema)
    def post(self):
        """Create a Single Banned"""

        return BannedService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "Banned database ID")
class BannedIdResource(Resource):
    @responds(schema=BannedSchema)
    def get(self, id: int):
        """Get Single Banned"""

        return BannedService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single Banned"""
        from flask import jsonify

        id = BannedService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BannedSchema, api=api)
    @responds(schema=BannedSchema)
    def put(self, id: int):
        """Update Single Banned"""

        changes: BannedInterface = request.parsed_obj
        Banned = BannedService.get_by_id(id)
        return BannedService.update(Banned, changes)