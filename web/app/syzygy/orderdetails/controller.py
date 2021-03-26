"""/web/app/syzygy/orderdetails/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    OrderDetailResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    OrderDetailIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{orderdetailid}.

    OrderDetailLoginResource:
        Extends Resource from flask-restx. Acts as a helper class for logging
        in orderdetails.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import OrderDetailInterface
from .model import OrderDetail
from .schema import OrderDetailSchema
from .service import OrderDetailService

api = Namespace("OrderDetail")
log = logging.getLogger(__name__)


@api.route("/")
class OrderDetailResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=OrderDetailSchema(many=True))
    def get(self):
        """Get all OrderDetails"""

        return OrderDetailService.get_all()

    @accepts(schema=OrderDetailSchema, api=api)
    @responds(schema=OrderDetailSchema)
    def post(self):
        """Create a Single OrderDetail"""

        return OrderDetailService.create(request.parsed_obj)


@api.route("/<int:orderdetailid>")
@api.param("orderdetailid", "Order detail database ID")
class OrderDetailIdResource(Resource):
    @responds(schema=OrderDetailSchema)
    def get(self, orderdetailid: int):
        """Get Single OrderDetail"""

        return OrderDetailService.get_by_id(orderdetailid)

    def delete(self, orderdetailid: int):
        """Delete Single OrderDetail"""
        from flask import jsonify

        id = OrderDetailService.delete_by_id(orderdetailid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=OrderDetailSchema, api=api)
    @responds(schema=OrderDetailSchema)
    def put(self, orderdetailid: int):
        """Update Single OrderDetail"""

        changes: OrderDetailInterface = request.parsed_obj
        OrderDetail = OrderDetailService.get_by_id(orderdetailid)
        return OrderDetailService.update(OrderDetail, changes)