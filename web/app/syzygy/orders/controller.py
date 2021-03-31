"""/web/app/syzygy/orders/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    OrderResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    OrderIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{orderid}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import OrderInterface
from .model import Order
from .schema import OrderSchema
from .service import OrderService

api = Namespace("Order")
log = logging.getLogger(__name__)


@api.route("/")
class OrderResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=OrderSchema(many=True))
    def get(self):
        """Get all Orders"""

        return OrderService.get_all()

    @accepts(schema=OrderSchema, api=api)
    @responds(schema=OrderSchema)
    def post(self):
        """Create a Single Order"""

        return OrderService.create(request.parsed_obj)


@api.route("/<int:orderid>")
@api.param("orderid", "Order database ID")
class OrderIdResource(Resource):
    @responds(schema=OrderSchema)
    def get(self, orderid: int):
        """Get Single Order"""

        return OrderService.get_by_id(orderid)

    def delete(self, orderid: int):
        """Delete Single Order"""
        from flask import jsonify

        id = OrderService.delete_by_id(orderid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=OrderSchema, api=api)
    @responds(schema=OrderSchema)
    def put(self, orderid: int):
        """Update Single Order"""

        changes: OrderInterface = request.parsed_obj
        Order = OrderService.get_by_id(orderid)
        return OrderService.update(Order, changes)