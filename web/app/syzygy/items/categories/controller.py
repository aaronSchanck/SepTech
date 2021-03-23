"""/web/app/syzygy/items/categories/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import CategoryInterface
from .model import Category
from .schema import CategorySchema
from .service import CategoryService

api = Namespace("Category")
log = logging.getLogger(__name__)


@api.route("/")
class CategoryResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=CategorySchema(many=True))
    def get(self):
        """Get all ItemCategories"""

        return CategoryService.get_all()

    @accepts(schema=CategorySchema, api=api)
    @responds(schema=CategorySchema)
    def post(self):
        """Create a Single Category"""

        return CategoryService.create(request.parsed_obj)


@api.route("/<int:category_id>")
@api.param("category_id", "Category database ID")
class CategoryIdResource(Resource):
    @responds(schema=CategorySchema)
    def get(self, category_id: int):
        """Get a single Category"""

        return CategoryService.get_by_id(category_id)

    @accepts(schema=CategorySchema, api=api)
    @responds(schema=CategorySchema)
    def put(self, itemid: int):
        """Update Single Category"""

        changes: CategoryInterface = request.parsed_obj
        Category = CategoryService.get_by_id(itemid)
        return CategoryService.update(Category, changes)