"""/web/app/syzygy/business_bans/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    BusinessBanResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    BusinessBanIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import BusinessBanInterface
from .model import BusinessBan
from .schema import BusinessBanSchema, BusinessBanSchema
from .service import BusinessBanService

api = Namespace("BusinessBan")
log = logging.getLogger(__name__)


@api.route("/")
class BusinessBanResource(Resource):
    @responds(schema=BusinessBanSchema(many=True))
    def get(self):
        """Get all BusinessBan entities"""

        return BusinessBanService.get_all()

    @accepts(schema=BusinessBanSchema, api=api)
    @responds(schema=BusinessBanSchema)
    def post(self):
        """Create a single BusinessBan entity"""

        return BusinessBanService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "BusinessBan database ID")
class BusinessBanIdResource(Resource):
    @responds(schema=BusinessBanSchema)
    def get(self, id: int):
        """Get single BusinessBan entity"""

        return BusinessBanService.get_by_id(id)

    def delete(self, id: int):
        """Delete single BusinessBan entity"""
        from flask import jsonify

        id = BusinessBanService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BusinessBanSchema, api=api)
    @responds(schema=BusinessBanSchema)
    def put(self, id: int):
        """Update single BusinessBan entity"""

        changes: BusinessBanInterface = request.parsed_obj
        BusinessBan = BusinessBanService.get_by_id(id)
        return BusinessBanService.update(BusinessBan, changes)