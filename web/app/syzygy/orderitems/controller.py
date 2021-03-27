"""/web/app/syzygy/users/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    OrderItemsResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    OrderItemsIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{userid}.

    OrderItemsLoginResource:
        Extends Resource from flask-restx. Acts as a helper class for logging
        in users.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import OrderItemsInterface
from .model import OrderItems
from .schema import OrderItemsSchema
from .service import OrderItemsService

api = Namespace("OrderItems")
log = logging.getLogger(__name__)


@api.route("/")
class OrderItemsResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=OrderItemsSchema(many=True))
    def get(self):
        """Get all OrderItemss"""

        return OrderItemsService.get_all()

    @accepts(schema=OrderItemsSchema, api=api)
    @responds(schema=OrderItemsSchema)
    def post(self):
        """Create a Single OrderItems"""

        return OrderItemsService.create(request.parsed_obj)


@api.route("/<int:userid>")
@api.param("userid", "OrderItems database ID")
class OrderItemsIdResource(Resource):
    @responds(schema=OrderItemsSchema)
    def get(self, userid: int):
        """Get Single OrderItems"""

        return OrderItemsService.get_by_id(userid)

    def delete(self, userid: int):
        """Delete Single OrderItems"""
        from flask import jsonify

        id = OrderItemsService.delete_by_id(userid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=OrderItemsSchema, api=api)
    @responds(schema=OrderItemsSchema)
    def put(self, userid: int):
        """Update Single OrderItems"""

        changes: OrderItemsInterface = request.parsed_obj
        OrderItems = OrderItemsService.get_by_id(userid)
        return OrderItemsService.update(OrderItems, changes)

@api.route("/<email>")
@api.param("email", "OrderItems database email")
class OrderItemsEmailResource(Resource):
    @responds(schema=OrderItemsSchema)
    def get(self, email: int):
        return OrderItemsService.get_by_email(email)


@api.route("/login")
class OrderItemsLoginResource(Resource):
    @accepts(
        dict(name="email", type=str, help="A user's email"),
        dict(name="password", type=str, help="A user's password"),
        api=api,
    )
    @responds(schema=OrderItemsSchema)
    def post(self):
        """Login with user credentials"""

        email = request.parsed_args["email"]
        password = request.parsed_args["password"]

        return OrderItemsService.login(email, password)
