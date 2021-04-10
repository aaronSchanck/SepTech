"""/web/app/syzygy/categories/controller.py

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

from .model import Category
from .schema import CategorySchema
from .service import CategoryService

from ..items.model import Item
from ..items.schema import ItemSchema

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
        """Get all Categories"""

        return CategoryService.get_all()

    @accepts(schema=CategorySchema, api=api)
    @responds(schema=CategorySchema)
    def post(self):
        """Create a single Category"""

        return CategoryService.create(request.parsed_obj)


@api.route("/<int:category_id>")
@api.response(404, "Category not found")
@api.param("category_id", "Category database ID")
class CategoryIdResource(Resource):
    @responds(schema=CategorySchema)
    def get(self, category_id: int):
        """Get a single Category"""

        return CategoryService.get_by_id(category_id)

    @accepts(schema=CategorySchema, api=api)
    @responds(schema=CategorySchema)
    def put(self, itemid: int):
        """Update single Category"""

        updates = request.parsed_obj
        category = CategoryService.get_by_id(itemid)
        return CategoryService.update(category, changes)


@api.route("/get_unique_categories")
class MainCategoryResource(Resource):
    def get(self):
        """Get unique top level categories"""
        distinct = []
        for category in Category.query.with_entities(Category.category_1).distinct():
            distinct.append(category[0])
        return distinct


@api.route("/<category_1_name>/items")
@api.param("category_name", "Category name")
class CategoryItemOneResource(Resource):
    # @responds(schema=ItemSchema(many=True))
    def get(self, category_name: str):
        """Get all items within <category_1_name>"""

        ids = []

        for category in Category.query.filter(
            Category.category_1 == category_1_name
        ).all():
            ids.append(category.category_id)

        item_ids = []

        for cat_id in ids:
            for item in Item.query.filter(Item.category_id == cat_id).all():
                item_ids.append(item.id)

        return item_ids


@api.route("/<category_1_name>/categories")
@api.param("category_1_name", "Category_1 name")
class CategoryOneResource(Resource):
    def get(self, category_1_name: str):
        """Get distinct categories in category <category_1_name>"""
        distinct = []
        for category in (
            Category.query.with_entities(Category.category_2)
            .filter(Category.category_1 == category_1_name)
            .distinct()
        ):
            distinct.append(category[0])
        return distinct


@api.route("/<category_1_name>/<category_2_name>/items")
@api.param("category_1_name", "First category name")
@api.param("category_2_name", "Second category name")
class CategoryItemTwoResource(Resource):
    # @responds(schema=ItemSchema(many=True))
    def get(self, category_1_name: str, category_2_name: str):
        """Get all items within category <category_1_name>/<category_2_name>"""
        ids = []

        for category in (
            Category.query.filter(Category.category_1 == category_1_name)
            .filter(Category.category_2 == category_2_name)
            .all()
        ):
            ids.append(category.category_id)

        item_ids = []

        for cat_id in ids:
            for item in Item.query.filter(Item.category_id == cat_id).all():
                item_ids.append(item.id)

        return item_ids


@api.route("/<category_1_name>/<category_2_name>/categories")
@api.param("category_1_name", "First category name")
@api.param("category_2_name", "Second category name")
class CategoryTwoResource(Resource):
    def get(self, category_1_name: str, category_2_name: str):
        """Get distinct categories in category <category_1_name>/<category_2_name>"""
        distinct = []
        for category in (
            Category.query.with_entities(Category.category_3)
            .filter(Category.category_1 == category_1_name)
            .filter(Category.category_2 == category_2_name)
            .distinct()
        ):
            distinct.append(category[0])
        return distinct


@api.route("/<category_1_name>/<category_2_name>/<category_3_name>/items")
@api.param("category_1_name", "First category name")
@api.param("category_2_name", "Second category name")
@api.param("category_3_name", "Third category name")
class CategoryItemThreeResource(Resource):
    # @responds(schema=ItemSchema(many=True))
    def get(self, category_1_name: str, category_2_name: str, category_3_name: str):
        """Get all items within category <category_1_name>/<category_2_name>/<category_3_name>"""
        ids = []

        for category in (
            Category.query.filter(Category.category_1 == category_1_name)
            .filter(Category.category_2 == category_2_name)
            .filter(Category.category_3 == category_3_name)
            .all()
        ):
            ids.append(category.category_id)

        item_ids = []

        for cat_id in ids:
            for item in Item.query.filter(Item.category_id == cat_id).all():
                item_ids.append(item.id)

        return item_ids


@api.route("/<category_1_name>/<category_2_name>/<category_3_name>/categories")
@api.param("category_name", "Category name")
class CategoryThreeResource(Resource):
    def get(self, category_1_name: str, category_2_name: str, category_3_name: str):
        """Get a single Category"""
        distinct = []
        for category in (
            Category.query.with_entities(Category.category_4)
            .filter(Category.category_1 == category_1_name)
            .filter(Category.category_2 == category_2_name)
            .filter(Category.category_3 == category_3_name)
            .distinct()
        ):
            distinct.append(category[0])
        return distinct


@api.route(
    "/<category_1_name>/<category_2_name>/<category_3_name>/<category_4_name>/items"
)
@api.param("category_1_name", "First category name")
@api.param("category_2_name", "Second category name")
@api.param("category_3_name", "Third category name")
@api.param("category_4_name", "Fourth category name")
class CategoryItemFourResource(Resource):
    # @responds(schema=ItemSchema(many=True))
    def get(
        self,
        category_1_name: str,
        category_2_name: str,
        category_3_name: str,
        cateogry_4_name: str,
    ):
        """Get all items within category <category_1_name>/<category_2_name>/<category_3_name>/<category_4_name>"""
        ids = []

        for category in (
            Category.query.filter(Category.category_1 == category_1_name)
            .filter(Category.category_2 == category_2_name)
            .filter(Category.category_3 == category_3_name)
            .filter(Category.category_4 == category_4_name)
            .all()
        ):
            ids.append(category.category_id)

        item_ids = []

        for cat_id in ids:
            for item in Item.query.filter(Item.category_id == cat_id).all():
                item_ids.append(item.id)

        return item_ids


@api.route(
    "/<category_1_name>/<category_2_name>/<category_3_name>/<category_4_name>/categories"
)
@api.param("category_1_name", "First category name")
@api.param("category_2_name", "Second category name")
@api.param("category_3_name", "Third category name")
@api.param("category_4_name", "Fourth category name")
class CategoryFourResource(Resource):
    def get(
        self,
        category_1_name: str,
        category_2_name: str,
        category_3_name: str,
        cateogry_4_name: str,
    ):
        """Get distinct categories in category <category_1_name>/<category_2_name>/<category_3_name>/<category_4_name>"""
        distinct = []
        for category in (
            Category.query.with_entities(Category.category_5)
            .filter(Category.category_1 == category_1_name)
            .filter(Category.category_2 == category_2_name)
            .filter(Category.category_3 == category_3_name)
            .filter(Category.category_4 == category_4_name)
            .distinct()
        ):
            distinct.append(category[0])
        return distinct


@api.route(
    "/<category_1_name>/<category_2_name>/<category_3_name>/<category_4_name>/<category_5_name>/items"
)
@api.param("category_1_name", "First category name")
@api.param("category_2_name", "Second category name")
@api.param("category_3_name", "Third category name")
@api.param("category_4_name", "Fourth category name")
@api.param("category_5_name", "Fifth category name")
class CategoryItemFiveResource(Resource):
    # @responds(schema=ItemSchema(many=True))
    def get(
        self,
        category_1_name: str,
        category_2_name: str,
        category_3_name: str,
        category_4_name: str,
        category_5_name: str,
    ):
        """Get distinct categories in category <category_1_name>/<category_2_name>/<category_3_name>/<category_4_name>/<category_5_name>"""
        ids = []

        for category in (
            Category.query.filter(Category.category_1 == category_1_name)
            .filter(Category.category_2 == category_2_name)
            .filter(Category.category_3 == category_3_name)
            .filter(Category.category_4 == category_4_name)
            .filter(Category.category_5 == category_5_name)
            .all()
        ):
            ids.append(category.category_id)

        item_ids = []

        for cat_id in ids:
            for item in Item.query.filter(Item.category_id == cat_id).all():
                item_ids.append(item.id)

        return item_ids
