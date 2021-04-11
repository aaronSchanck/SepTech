"""/web/app/syzygy/addresses/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    AddressResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    AddressIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{addressid}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import Address
from .schema import AddressSchema
from .service import AddressService

api = Namespace("Address")
log = logging.getLogger(__name__)


@api.route("/")
class AddressResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=AddressSchema(many=True))
    def get(self):
        """Get all Addresss"""

        return AddressService.get_all()

    @accepts(schema=AddressSchema, api=api)
    @responds(schema=AddressSchema)
    def post(self):
        """Create a Single Address"""

        return AddressService.create(request.parsed_obj)


@api.route("/<int:addressid>")
@api.param("addressid", "Address database ID")
class AddressIdResource(Resource):
    @responds(schema=AddressSchema)
    def get(self, addressid: int):
        """Get Single Address"""

        return AddressService.get_by_id(addressid)

    def delete(self, addressid: int):
        """Delete Single Address"""
        from flask import jsonify

        id = AddressService.delete_by_id(addressid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=AddressSchema, api=api)
    @responds(schema=AddressSchema)
    def put(self, addressid: int):
        """Update Single Address"""

        updates = request.parsed_obj
        address = AddressService.get_by_id(addressid)

        return AddressService.update(address, updates)