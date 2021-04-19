"""/web/app/syzygy/notifications/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    NotificationResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    NotificationIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import Notification
from .schema import NotificationSchema
from .service import NotificationService

api = Namespace("Notification")
log = logging.getLogger(__name__)


@api.route("/")
class NotificationResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=NotificationSchema(many=True))
    def get(self):
        """Get all Notifications"""

        return NotificationService.get_all()

    @accepts(schema=NotificationSchema, api=api)
    @responds(schema=NotificationSchema)
    def post(self):
        """Create a Single Notification"""

        return NotificationService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "Notification database ID")
class NotificationIdResource(Resource):
    @responds(schema=NotificationSchema)
    def get(self, id: int):
        """Get Single Notification"""

        return NotificationService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single Notification"""
        from flask import jsonify

        id = NotificationService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=NotificationSchema, api=api)
    @responds(schema=NotificationSchema)
    def put(self, id: int):
        """Update Single Notification"""

        updates = request.parsed_obj
        order = NotificationService.get_by_id(id)
        return NotificationService.update(order, updates)