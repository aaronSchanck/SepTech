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

from .interface import BusinessReportInterface
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

    # @accepts(schema=BusinessReportSchema, api=api)
    # @responds(schema=BusinessReportSchema)
    # def post(self):
    #     """Create a Single BusinessReport"""

    #     return BusinessReportService.create(request.parsed_obj)
    def post(self):
        """Create a Single BusinessReport"""


@api.route("/<int:business_reportid>")
@api.param("business_reportid", "BusinessReport database ID")
class BusinessReportIdResource(Resource):
    @responds(schema=BusinessReportSchema)
    def get(self, business_reportid: int):
        """Get Single BusinessReport"""

        return BusinessReportService.get_by_id(business_reportid)

    def delete(self, business_reportid: int):
        """Delete Single BusinessReport"""
        from flask import jsonify

        id = BusinessReportService.delete_by_id(business_reportid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=BusinessReportSchema, api=api)
    @responds(schema=BusinessReportSchema)
    def put(self, business_reportid: int):
        """Update Single BusinessReport"""

        changes: BusinessReportInterface = request.parsed_obj
        BusinessReport = BusinessReportService.get_by_id(business_reportid)
        return BusinessReportService.update(BusinessReport, changes)