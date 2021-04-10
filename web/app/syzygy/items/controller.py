"""/web/app/syzygy/items/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    ItemResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    ItemIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{itemid}.

"""

import json
import logging
from pathlib import Path
from typing import List

import werkzeug
from app import app
from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource, reqparse

from .interface import ItemInterface
from .model import Item
from .schema import ImageSchema, ItemSchema
from .service import ItemService

api = Namespace("Item")
log = logging.getLogger(__name__)

item_schema = ItemSchema()
image_schema = ImageSchema()


@api.route("/test")
class ItemTestingResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ItemSchema(many=True))
    def get(self):
        """Get all Items"""

        return ItemService.get_all()

    @accepts(schema=ItemSchema, api=api)
    @responds(schema=ItemSchema)
    def post(self):
        """Create a Single Item"""

        return ItemService.create(request.parsed_obj)


@api.route("/search/<search_str>")
@api.param("search_str", "Item search string")
class ItemResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ItemSchema(many=True))
    def get(self, search_str: str):
        """Get all Items in search"""

        return ItemService.search(search_str)


@api.route("/<int:itemid>")
@api.param("itemid", "Item database ID")
class ItemIdResource(Resource):
    @responds(schema=ItemSchema)
    def get(self, itemid: int):
        """Get Single Item"""

        return ItemService.get_by_id(itemid)

    def delete(self, itemid: int):
        """Delete Single Item"""
        from flask import jsonify

        id = ItemService.delete_by_id(itemid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=ItemSchema, api=api)
    @responds(schema=ItemSchema)
    def put(self, itemid: int):
        """Update Single Item"""

        changes: ItemInterface = request.parsed_obj
        Item = ItemService.get_by_id(itemid)
        return ItemService.update(Item, changes)


@api.route("/create")
class ItemCreateResource(Resource):
    def post(self):
        # ItemService.parse_images(request.files)
        item_req = request.form

        dat = item_req.to_dict()["itemEntity"]

        obj = json.loads(dat)

        item_vali = item_schema.validate(obj)

        print(item_vali)

        item_data = item_schema.load(obj)

        print(item_data)

        item = ItemService.create(item_data)

        print(item)

        return item_schema.dump(item)

        # image_path = Path(app.config["UPLOAD_FOLDER"] + "/items/file_to_save.jpg")
        # image_file.save(image_path)
