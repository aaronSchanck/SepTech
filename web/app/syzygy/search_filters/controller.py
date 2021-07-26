"""/web/app/syzygy/search_filters/controller.py

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

from .model import SearchFilter
from .schema import SearchFilterSchema
from .service import SearchFilterService

api = Namespace("SearchFilter")
log = logging.getLogger(__name__)


@api.route("/")
class SearchFilterResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=SearchFilterSchema(many=True))
    def get(self):
        """Get all SearchFilters"""

        return SearchFilterService.get_all()

    @accepts(schema=SearchFilterSchema, api=api)
    @responds(schema=SearchFilterSchema)
    def post(self):
        """Create a Single SearchFilter"""

        return SearchFilterService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "SearchFilter database ID")
class SearchFilterIdResource(Resource):
    @responds(schema=SearchFilterSchema)
    def get(self, id: int):
        """Get Single SearchFilter"""

        return SearchFilterService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single SearchFilter"""
        from flask import jsonify

        id = SearchFilterService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=SearchFilterSchema, api=api)
    @responds(schema=SearchFilterSchema)
    def put(self, id: int):
        """Update Single SearchFilter"""

        updates = request.parsed_obj
        search_filter = SearchFilterService.get_by_id(id)
        return SearchFilterService.update(search_filter, updates)