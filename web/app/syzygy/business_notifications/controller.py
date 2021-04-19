"""/web/app/syzygy/business_notifications/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import BusinessNotification
from .schema import BusinessNotificationSchema
from .service import BusinessNotificationService

api = Namespace("BusinessNotification")
log = logging.getLogger(__name__)


@api.route("/")
class BusinessNotificationResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BusinessNotificationSchema(many=True))
    def get(self):
        """Get all BusinessNotifications"""

        return BusinessNotificationService.get_all()

    @accepts(schema=BusinessNotificationSchema, api=api)
    @responds(schema=BusinessNotificationSchema)
    def post(self):
        """Create a Single BusinessNotification"""

        return BusinessNotificationService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "BusinessNotification database ID")
class BusinessNotificationIdResource(Resource):
    @responds(schema=BusinessNotificationSchema)
    def get(self, id: int):
        """Get Single BusinessNotification"""

        return BusinessNotificationService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single BusinessNotification"""
        from flask import jsonify

        id = BusinessNotificationService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BusinessNotificationSchema, api=api)
    @responds(schema=BusinessNotificationSchema)
    def put(self, id: int):
        """Update Single BusinessNotification"""

        updates = request.parsed_obj
        business_report = BusinessNotificationService.get_by_id(id)

        return BusinessNotificationService.update(business_report, updates)