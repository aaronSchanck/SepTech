"""/web/app/syzygy/business_reviews/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    BusinessReviewResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    BusinessReviewIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import BusinessReviewInterface
from .model import BusinessReview
from .schema import BusinessReviewSchema
from .service import BusinessReviewService

api = Namespace("BusinessReview")
log = logging.getLogger(__name__)


@api.route("/")
class BusinessReviewResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BusinessReviewSchema(many=True))
    def get(self):
        """Get all BusinessReviews"""

        return BusinessReviewService.get_all()

    @accepts(schema=BusinessReviewSchema, api=api)
    @responds(schema=BusinessReviewSchema)
    def post(self):
        """Create a Single BusinessReview"""

        print(request.parsed_obj)

        return BusinessReviewService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "BusinessReview database ID")
class BusinessReviewIdResource(Resource):
    @responds(schema=BusinessReviewSchema)
    def get(self, id: int):
        """Get Single BusinessReview"""

        print(id)

        return BusinessReviewService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single BusinessReview"""
        from flask import jsonify

        id = BusinessReviewService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BusinessReviewSchema, api=api)
    @responds(schema=BusinessReviewSchema)
    def put(self, id: int):
        """Update Single BusinessReview"""

        changes: BusinessReviewInterface = request.parsed_obj
        BusinessReview = BusinessReviewService.get_by_id(id)
        return BusinessReviewService.update(BusinessReview, changes)