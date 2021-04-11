"""/web/app/syzygy/business_reports/controller.py

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

from .model import BusinessReport
from .schema import BusinessReportSchema
from .service import BusinessReportService

api = Namespace("BusinessReport")
log = logging.getLogger(__name__)


@api.route("/")
class BusinessReportResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=BusinessReportSchema(many=True))
    def get(self):
        """Get all BusinessReports"""

        return BusinessReportService.get_all()

    @accepts(schema=BusinessReportSchema, api=api)
    @responds(schema=BusinessReportSchema)
    def post(self):
        """Create a Single BusinessReport"""

        return BusinessReportService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "BusinessReport database ID")
class BusinessReportIdResource(Resource):
    @responds(schema=BusinessReportSchema)
    def get(self, id: int):
        """Get Single BusinessReport"""

        return BusinessReportService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single BusinessReport"""
        from flask import jsonify

        id = BusinessReportService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BusinessReportSchema, api=api)
    @responds(schema=BusinessReportSchema)
    def put(self, id: int):
        """Update Single BusinessReport"""

        updates = request.parsed_obj
        business_report = BusinessReportService.get_by_id(id)

        return BusinessReportService.update(business_report, updates)