"""/web/app/syzygy/view_history/controller.py

Author: Ashley Drexler (ashley.drexler@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    ViewHistoryResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    ViewHistoryIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import ViewHistory
from .schema import ViewHistorySchema
from .service import ViewHistoryService

api = Namespace("ViewHistory")
log = logging.getLogger(__name__)


@api.route("/")
class ViewHistoryResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ViewHistorySchema(many=True))
    def get(self):
        """Get all View Histories"""

        return ViewHistoryService.get_all()

    @accepts(schema=ViewHistorySchema, api=api)
    @responds(schema=ViewHistorySchema)
    def post(self):
        """Create a Single View History"""

        return ViewHistoryService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "View History database ID")
class ViewHistoryIdResource(Resource):
    @responds(schema=ViewHistorySchema)
    def get(self, id: int):
        """Get Single View History"""

        return ViewHistoryService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single View History"""
        from flask import jsonify

        id = ViewHistoryService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=ViewHistorySchema, api=api)
    @responds(schema=ViewHistorySchema)
    def put(self, id: int):
        """Update Single View History"""

        updates = request.parsed_obj
        order = ViewHistoryService.get_by_id(id)
        return ViewHistoryService.update(order, updates)