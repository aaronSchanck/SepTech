"""/web/app/syzygy/furniture/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    FurnitureResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    FurnitureIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{itemid}.

    FurnitureLoginResource:
        Extends Resource from flask-restx. Acts as a helper class for logging
        in furniture.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import FurnitureInterface
from .model import Furniture
from .schema import FurnitureSchema
from .service import FurnitureService

api = Namespace("Furniture")
log = logging.getLogger(__name__)


@api.route("/")
class FurnitureResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=FurnitureSchema(many=True))
    def get(self):
        """Get all Furnitures"""

        return FurnitureService.get_all()

    @accepts(schema=FurnitureSchema, api=api)
    @responds(schema=FurnitureSchema)
    def post(self):
        """Create a Single Furniture"""

        return FurnitureService.create(request.parsed_obj)


@api.route("/<int:itemid>")
@api.param("itemid", "Furniture database ID")
class FurnitureIdResource(Resource):
    @responds(schema=FurnitureSchema)
    def get(self, itemid: int):
        """Get Single Furniture"""

        return FurnitureService.get_by_id(itemid)

    def delete(self, itemid: int):
        """Delete Single Furniture"""
        from flask import jsonify

        id = FurnitureService.delete_by_id(itemid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=FurnitureSchema, api=api)
    @responds(schema=FurnitureSchema)
    def put(self, itemid: int):
        """Update Single Furniture"""

        changes: FurnitureInterface = request.parsed_obj
        Furniture = FurnitureService.get_by_id(itemid)
        return FurnitureService.update(Furniture, changes)