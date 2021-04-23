"""/web/app/syzygy/view_history_items/controller.py

Author: Adam Green (adam.green1@maine.edu)
        Ashley Drexler (ashley.drexler@maine.edu)


This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    ViewHistoryItemResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    ViewHistoryItemIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import ViewHistoryItem
from .schema import ViewHistoryItemSchema
from .service import ViewHistoryItemService

api = Namespace("ViewHistoryItem")
log = logging.getLogger(__name__)


@api.route("/")
class ViewHistoryItemResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ViewHistoryItemSchema(many=True))
    def get(self):
        """Get all ViewHistoryItem"""

        return ViewHistoryItemService.get_all()

    @accepts(schema=ViewHistoryItemSchema, api=api)
    @responds(schema=ViewHistoryItemSchema)
    def post(self):
        """Create a Single ViewHistoryItem"""

        return ViewHistoryItemService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "ViewHistoryItem database ID")
class ViewHistoryItemIdResource(Resource):
    @responds(schema=ViewHistoryItemSchema)
    def get(self, id: int):
        """Get Single ViewHistoryItem"""

        return ViewHistoryItemService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single ViewHistoryItem"""
        from flask import jsonify

        id = ViewHistoryItemService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=ViewHistoryItemSchema, api=api)
    @responds(schema=ViewHistoryItemSchema)
    def put(self, id: int):
        """Update Single ViewHistoryItem"""

        updates = request.parsed_obj
        view_history_item = ViewHistoryItemService.get_by_id(id)
        return ViewHistoryItemService.update(view_history_item, updates)