"""/web/app/syzygy/removed_items/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    RemovedItemResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    RemovedItemIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import RemovedItemInterface
from .model import RemovedItem
from .schema import RemovedItemSchema, RemovedItemSchema
from .service import RemovedItemService

api = Namespace("RemovedItem")
log = logging.getLogger(__name__)


@api.route("/")
class RemovedItemResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=RemovedItemSchema(many=True))
    def get(self):
        """Get all RemovedItems"""

        return RemovedItemService.get_all()

    @accepts(schema=RemovedItemSchema, api=api)
    @responds(schema=RemovedItemSchema)
    def post(self):
        """Create a Single RemovedItem"""

        return RemovedItemService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "RemovedItem database ID")
class RemovedItemIdResource(Resource):
    @responds(schema=RemovedItemSchema)
    def get(self, id: int):
        """Get Single RemovedItem"""

        return RemovedItemService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single RemovedItem"""
        from flask import jsonify

        id = RemovedItemService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=RemovedItemSchema, api=api)
    @responds(schema=RemovedItemSchema)
    def put(self, id: int):
        """Update Single RemovedItem"""

        changes: RemovedItemInterface = request.parsed_obj
        RemovedItem = RemovedItemService.get_by_id(id)
        return RemovedItemService.update(RemovedItem, changes)