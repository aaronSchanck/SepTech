"""/web/app/syzygy/user_notifications/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    UserNotificationResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    UserNotificationIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .model import UserNotification
from .schema import UserNotificationSchema
from .service import UserNotificationService

api = Namespace("UserNotification")
log = logging.getLogger(__name__)


@api.route("/")
class UserNotificationResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=UserNotificationSchema(many=True))
    def get(self):
        """Get all UserNotifications"""

        return UserNotificationService.get_all()

    @accepts(schema=UserNotificationSchema, api=api)
    @responds(schema=UserNotificationSchema)
    def post(self):
        """Create a Single UserNotification"""

        return UserNotificationService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "UserNotification database ID")
class UserNotificationIdResource(Resource):
    @responds(schema=UserNotificationSchema)
    def get(self, id: int):
        """Get Single UserNotification"""

        return UserNotificationService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single UserNotification"""
        from flask import jsonify

        id = UserNotificationService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=UserNotificationSchema, api=api)
    @responds(schema=UserNotificationSchema)
    def put(self, id: int):
        """Update Single UserNotification"""

        updates = request.parsed_obj
        order = UserNotificationService.get_by_id(id)
        return UserNotificationService.update(order, updates)