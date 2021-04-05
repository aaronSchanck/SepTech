"""/web/app/syzygy/item_reports/controller.py

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

from .interface import ItemReportInterface
from .model import ItemReport
from .schema import ItemReportSchema
from .service import ItemReportService

api = Namespace("ItemReport")
log = logging.getLogger(__name__)


@api.route("/")
class ItemReportResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ItemReportSchema(many=True))
    def get(self):
        """Get all ItemReports"""

        return ItemReportService.get_all()

    # @accepts(schema=ItemReportSchema, api=api)
    # @responds(schema=ItemReportSchema)
    # def post(self):
    #     """Create a Single ItemReport"""

    #     return ItemReportService.create(request.parsed_obj)
    def post(self):
        """Create a Single ItemReport"""


@api.route("/<int:item_reportid>")
@api.param("item_reportid", "ItemReport database ID")
class ItemReportIdResource(Resource):
    @responds(schema=ItemReportSchema)
    def get(self, item_reportid: int):
        """Get Single ItemReport"""

        return ItemReportService.get_by_id(item_reportid)

    def delete(self, item_reportid: int):
        """Delete Single ItemReport"""
        from flask import jsonify

        id = ItemReportService.delete_by_id(item_reportid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=ItemReportSchema, api=api)
    @responds(schema=ItemReportSchema)
    def put(self, item_reportid: int):
        """Update Single ItemReport"""

        changes: ItemReportInterface = request.parsed_obj
        ItemReport = ItemReportService.get_by_id(item_reportid)
        return ItemReportService.update(ItemReport, changes)