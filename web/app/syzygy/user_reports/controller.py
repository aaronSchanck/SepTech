"""/web/app/syzygy/user_reports/controller.py

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

from .model import UserReport
from .schema import UserReportSchema
from .service import UserReportService

api = Namespace("UserReport")
log = logging.getLogger(__name__)


@api.route("/")
class UserReportResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=UserReportSchema(many=True))
    def get(self):
        """Get all UserReports"""

        return UserReportService.get_all()

    @accepts(schema=UserReportSchema, api=api)
    @responds(schema=UserReportSchema)
    def post(self):
        """Create a Single UserReport"""

        return UserReportService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "UserReport database ID")
class UserReportIdResource(Resource):
    @responds(schema=UserReportSchema)
    def get(self, id: int):
        """Get Single UserReport"""

        return UserReportService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single UserReport"""
        from flask import jsonify

        id = UserReportService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=UserReportSchema, api=api)
    @responds(schema=UserReportSchema)
    def put(self, id: int):
        """Update Single UserReport"""
        user_report = UserReportService.get_by_id(id)

        return UserReportService.update(user_report, request.parsed_obj)