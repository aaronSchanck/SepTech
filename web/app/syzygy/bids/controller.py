"""/web/app/syzygy/bids/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    BidResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    BidIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import BidInterface
from .model import Bid
from .schema import BidSchema
from .service import BidService

api = Namespace("Bid")
log = logging.getLogger(__name__)


@api.route("/")
class BidResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BidSchema(many=True))
    def get(self):
        """Get all Bids"""

        return BidService.get_all()

    @accepts(schema=BidSchema, api=api)
    @responds(schema=BidSchema)
    def post(self):
        """Create a Single Bid"""

        print(request.parsed_obj)

        return BidService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "Bid database ID")
class BidIdResource(Resource):
    @responds(schema=BidSchema)
    def get(self, id: int):
        """Get Single Bid"""

        print(id)

        return BidService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single Bid"""
        from flask import jsonify

        id = BidService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BidSchema, api=api)
    @responds(schema=BidSchema)
    def put(self, id: int):
        """Update Single Bid"""

        changes: BidInterface = request.parsed_obj
        Bid = BidService.get_by_id(id)
        return BidService.update(Bid, changes)


@api.route("/<email>")
@api.param("email", "Bid database email")
class BidEmailResource(Resource):
    @responds(schema=BidSchema)
    def get(self, email: int):
        """Get bid by bid login email"""
        return BidService.get_by_email(email)


@api.route("/login")
class BidLoginResource(Resource):
    @accepts(
        dict(name="email", type=str, help="A bid's email"),
        dict(name="password", type=str, help="A bid's password"),
        api=api,
    )
    @responds(schema=BidSchema)
    def post(self):
        """Login with bid credentials"""
        email = request.parsed_args["email"]
        password = request.parsed_args["password"]

        print(email, password)

        return BidService.login(email, password)
