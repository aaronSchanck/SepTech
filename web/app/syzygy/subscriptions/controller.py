"""/web/app/syzygy/subscriptions/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    SubscriptionResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    SubscriptionIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import SubscriptionInterface
from .model import Subscription
from .schema import SubscriptionSchema, SubscriptionSchema
from .service import SubscriptionService

api = Namespace("Subscription")
log = logging.getLogger(__name__)


@api.route("/")
class SubscriptionResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=SubscriptionSchema(many=True))
    def get(self):
        """Get all Subscription"""

        return SubscriptionService.get_all()

    @accepts(schema=SubscriptionSchema, api=api)
    @responds(schema=SubscriptionSchema)
    def post(self):
        """Create a Single Subscription"""

        return SubscriptionService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "Subscription database ID")
class SubscriptionIdResource(Resource):
    @responds(schema=SubscriptionSchema)
    def get(self, id: int):
        """Get Single Subscription"""

        return SubscriptionService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single Subscription"""
        from flask import jsonify

        id = SubscriptionService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=SubscriptionSchema, api=api)
    @responds(schema=SubscriptionSchema)
    def put(self, id: int):
        """Update Single Subscription"""

        changes: SubscriptionInterface = request.parsed_obj
        Subscription = SubscriptionService.get_by_id(id)
        return SubscriptionService.update(Subscription, changes)