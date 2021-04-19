"""/web/app/syzygy/wishlist_items/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    WishlistItemResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    WishlistItemIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import WishlistItem
from .schema import WishlistItemSchema
from .service import WishlistItemService

api = Namespace("WishlistItem")
log = logging.getLogger(__name__)


@api.route("/")
class WishlistItemResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=WishlistItemSchema(many=True))
    def get(self):
        """Get all WishlistItems"""

        return WishlistItemService.get_all()

    @accepts(schema=WishlistItemSchema, api=api)
    @responds(schema=WishlistItemSchema)
    def post(self):
        """Create a Single WishlistItem"""

        return WishlistItemService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "WishlistItem database ID")
class WishlistItemIdResource(Resource):
    @responds(schema=WishlistItemSchema)
    def get(self, id: int):
        """Get Single WishlistItem"""

        return WishlistItemService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single WishlistItem"""
        from flask import jsonify

        id = WishlistItemService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=WishlistItemSchema, api=api)
    @responds(schema=WishlistItemSchema)
    def put(self, id: int):
        """Update Single WishlistItem"""

        updates = request.parsed_obj
        wishlist_item = WishlistItemService.get_by_id(id)
        return WishlistItemService.update(wishlist_item, updates)