"""/web/app/syzygy/orders/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    WishlistResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    WishlistIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import Wishlist
from .schema import WishlistSchema
from .service import WishlistService

api = Namespace("Wishlist")
log = logging.getLogger(__name__)


@api.route("/")
class WishlistResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=WishlistSchema(many=True))
    def get(self):
        """Get all Wishlists"""

        return WishlistService.get_all()

    @accepts(schema=WishlistSchema, api=api)
    @responds(schema=WishlistSchema)
    def post(self):
        """Create a Single Wishlist"""

        return WishlistService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "Wishlist database ID")
class WishlistIdResource(Resource):
    @responds(schema=WishlistSchema)
    def get(self, id: int):
        """Get Single Wishlist"""

        return WishlistService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single Wishlist"""
        from flask import jsonify

        id = WishlistService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=WishlistSchema, api=api)
    @responds(schema=WishlistSchema)
    def put(self, id: int):
        """Update Single Wishlist"""

        updates = request.parsed_obj
        order = WishlistService.get_by_id(id)
        return WishlistService.update(order, updates)