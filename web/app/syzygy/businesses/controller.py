"""/web/app/syzygy/users/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    BusinessResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    BusinessIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{userid}.

    BusinessLoginResource:
        Extends Resource from flask-restx. Acts as a helper class for logging
        in users.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import BusinessInterface
from .model import Business
from .schema import BusinessSchema
from .service import BusinessService

api = Namespace("Business")
log = logging.getLogger(__name__)


@api.route("/")
class BusinessResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BusinessSchema(many=True))
    def get(self):
        """Get all Businesss"""

        return BusinessService.get_all()

    @accepts(schema=BusinessSchema, api=api)
    @responds(schema=BusinessSchema)
    def post(self):
        """Create a Single Business"""

        return BusinessService.create(request.parsed_obj)


@api.route("/<int:userid>")
@api.param("userid", "Business database ID")
class BusinessIdResource(Resource):
    @responds(schema=BusinessSchema)
    def get(self, userid: int):
        """Get Single Business"""

        return BusinessService.get_by_id(userid)

    def delete(self, userid: int):
        """Delete Single Business"""
        from flask import jsonify

        id = BusinessService.delete_by_id(userid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BusinessSchema, api=api)
    @responds(schema=BusinessSchema)
    def put(self, userid: int):
        """Update Single Business"""

        changes: BusinessInterface = request.parsed_obj
        Business = BusinessService.get_by_id(userid)
        return BusinessService.update(Business, changes)

@api.route("/<email>")
@api.param("email", "Business database email")
class BusinessEmailResource(Resource):
    @responds(schema=BusinessSchema)
    def get(self, email: int):
        return BusinessService.get_by_email(email)


@api.route("/login")
class BusinessLoginResource(Resource):
    @accepts(
        dict(name="email", type=str, help="A user's email"),
        dict(name="password", type=str, help="A user's password"),
        api=api,
    )
    @responds(schema=BusinessSchema)
    def post(self):
        """Login with user credentials"""

        email = request.parsed_args["email"]
        password = request.parsed_args["password"]

        return BusinessService.login(email, password)
