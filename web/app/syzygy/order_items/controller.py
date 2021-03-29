"""/web/app/syzygy/users/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    OrderItemResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    OrderItemIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{userid}.

    OrderItemLoginResource:
        Extends Resource from flask-restx. Acts as a helper class for logging
        in users.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import OrderItemInterface
from .model import OrderItem
from .schema import OrderItemSchema
from .service import OrderItemService

api = Namespace("OrderItem")
log = logging.getLogger(__name__)


@api.route("/")
class OrderItemResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=OrderItemSchema(many=True))
    def get(self):
        """Get all OrderItems"""

        return OrderItemService.get_all()

    @accepts(schema=OrderItemSchema, api=api)
    @responds(schema=OrderItemSchema)
    def post(self):
        """Create a Single OrderItem"""

        return OrderItemService.create(request.parsed_obj)


@api.route("/<int:userid>")
@api.param("userid", "OrderItem database ID")
class OrderItemIdResource(Resource):
    @responds(schema=OrderItemSchema)
    def get(self, userid: int):
        """Get Single OrderItem"""

        return OrderItemService.get_by_id(userid)

    def delete(self, userid: int):
        """Delete Single OrderItem"""
        from flask import jsonify

        id = OrderItemService.delete_by_id(userid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=OrderItemSchema, api=api)
    @responds(schema=OrderItemSchema)
    def put(self, userid: int):
        """Update Single OrderItem"""

        changes: OrderItemInterface = request.parsed_obj
        OrderItem = OrderItemService.get_by_id(userid)
        return OrderItemService.update(OrderItem, changes)

@api.route("/<email>")
@api.param("email", "OrderItem database email")
class OrderItemEmailResource(Resource):
    @responds(schema=OrderItemSchema)
    def get(self, email: int):
        return OrderItemService.get_by_email(email)


@api.route("/login")
class OrderItemLoginResource(Resource):
    @accepts(
        dict(name="email", type=str, help="A user's email"),
        dict(name="password", type=str, help="A user's password"),
        api=api,
    )
    @responds(schema=OrderItemSchema)
    def post(self):
        """Login with user credentials"""

        email = request.parsed_args["email"]
        password = request.parsed_args["password"]

        return OrderItemService.login(email, password)
