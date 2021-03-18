"""/web/app/syzygy/electronics/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    ElectronicResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    ElectronicIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{itemid}.

    ElectronicLoginResource:
        Extends Resource from flask-restx. Acts as a helper class for logging
        in electronics.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import ElectronicInterface
from .model import Electronic
from .schema import ElectronicSchema
from .service import ElectronicService

api = Namespace("Electronic")
log = logging.getLogger(__name__)


@api.route("/")
class ElectronicResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ElectronicSchema(many=True))
    def get(self):
        """Get all Electronics"""

        return ElectronicService.get_all()

    @accepts(schema=ElectronicSchema, api=api)
    @responds(schema=ElectronicSchema)
    def post(self):
        """Create a Single Electronic"""

        return ElectronicService.create(request.parsed_obj)


@api.route("/<int:itemid>")
@api.param("itemid", "Electronic database ID")
class ElectronicIdResource(Resource):
    @responds(schema=ElectronicSchema)
    def get(self, itemid: int):
        """Get Single Electronic"""

        return ElectronicService.get_by_id(itemid)

    def delete(self, itemid: int):
        """Delete Single Electronic"""
        from flask import jsonify

        id = ElectronicService.delete_by_id(itemid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=ElectronicSchema, api=api)
    @responds(schema=ElectronicSchema)
    def put(self, itemid: int):
        """Update Single Electronic"""

        changes: ElectronicInterface = request.parsed_obj
        Electronic = ElectronicService.get_by_id(itemid)
        return ElectronicService.update(Electronic, changes)