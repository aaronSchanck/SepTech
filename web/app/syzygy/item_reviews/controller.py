"""/web/app/syzygy/item_reviews/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    ItemReviewResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    ItemReviewIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import ItemReviewInterface
from .model import ItemReview
from .schema import ItemReviewSchema
from .service import ItemReviewService

api = Namespace("ItemReview")
log = logging.getLogger(__name__)


@api.route("/")
class ItemReviewResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ItemReviewSchema(many=True))
    def get(self):
        """Get all ItemReviews"""

        return ItemReviewService.get_all()

    @accepts(schema=ItemReviewSchema, api=api)
    @responds(schema=ItemReviewSchema)
    def post(self):
        """Create a Single ItemReview"""

        print(request.parsed_obj)

        return ItemReviewService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "ItemReview database ID")
class ItemReviewIdResource(Resource):
    @responds(schema=ItemReviewSchema)
    def get(self, id: int):
        """Get Single ItemReview"""

        print(id)

        return ItemReviewService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single ItemReview"""
        from flask import jsonify

        id = ItemReviewService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=ItemReviewSchema, api=api)
    @responds(schema=ItemReviewSchema)
    def put(self, id: int):
        """Update Single ItemReview"""

        changes: ItemReviewInterface = request.parsed_obj
        ItemReview = ItemReviewService.get_by_id(id)
        return ItemReviewService.update(ItemReview, changes)