"""/web/app/syzygy/order_items/controller.py

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
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

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


@api.route("/<int:id>")
@api.param("id", "OrderItem database ID")
class OrderItemIdResource(Resource):
    @responds(schema=OrderItemSchema)
    def get(self, id: int):
        """Get Single OrderItem"""

        return OrderItemService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single OrderItem"""
        from flask import jsonify

        id = OrderItemService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=OrderItemSchema, api=api)
    @responds(schema=OrderItemSchema)
    def put(self, id: int):
        """Update Single OrderItem"""

        updates = request.parsed_obj
        order_item = OrderItemService.get_by_id(id)
        return OrderItemService.update(order_item, updates)