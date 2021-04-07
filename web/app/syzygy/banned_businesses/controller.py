"""/web/app/syzygy/banned_businesses/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    BannedBusinessResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    BannedBusinessIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import BannedBusinessInterface
from .model import BannedBusiness
from .schema import BannedBusinessSchema, BannedBusinessSchema
from .service import BannedBusinessService

api = Namespace("BannedBusiness")
log = logging.getLogger(__name__)


@api.route("/")
class BannedBusinessResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BannedBusinessSchema(many=True))
    def get(self):
        """Get all BannedBusiness"""

        return BannedBusinessService.get_all()

    @accepts(schema=BannedBusinessSchema, api=api)
    @responds(schema=BannedBusinessSchema)
    def post(self):
        """Create a Single BannedBusiness"""

        return BannedBusinessService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "BannedBusiness database ID")
class BannedBusinessIdResource(Resource):
    @responds(schema=BannedBusinessSchema)
    def get(self, id: int):
        """Get Single BannedBusiness"""

        return BannedBusinessService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single BannedBusiness"""
        from flask import jsonify

        id = BannedBusinessService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BannedBusinessSchema, api=api)
    @responds(schema=BannedBusinessSchema)
    def put(self, id: int):
        """Update Single BannedBusiness"""

        changes: BannedBusinessInterface = request.parsed_obj
        BannedBusiness = BannedBusinessService.get_by_id(id)
        return BannedBusinessService.update(BannedBusiness, changes)