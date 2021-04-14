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
        aforementioned class. Must be routed to with {baseurl}/{id}.

"""

import json
import logging
import os
import io
from pathlib import Path
from typing import List
import zipfile

from marshmallow import fields

import werkzeug
from app import app
from flask import request, send_file
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource, reqparse
from libs.response import SchemaErrResponse

from .model import Item
from .schema import ImageSchema, ItemSchema, SearchSchema
from .service import ItemService

from webargs.flaskparser import use_args, use_kwargs

api = Namespace("Item")
log = logging.getLogger(__name__)

item_schema = ItemSchema()
item_schema_many = ItemSchema(many=True)

image_schema = ImageSchema()


@api.route("/")
class ItemResource(Resource):
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


@api.route("/search")
class ItemSearchResource(Resource):
    @use_args(SearchSchema(), location="query")
    def get(self, args):
        print(args)

        items = ItemService.search(
            search_str="", filters={}, page=args["page"], page_size=5
        )

        return item_schema_many.dump(items)


@api.route("/search/amount")
class ItemSearchResource(Resource):
    @use_args(SearchSchema(), location="query")
    def get(self, args):
        print("Amount query")

        return ItemService.search_amount("")


@api.route("/search/images")
class ItemImageSearchResource(Resource):
    def get(self):
        print("Image query")

        ids = request.args["ids"].split(",")

        print(ids)

        image_dir = os.path.join("images", "items")

        data = io.BytesIO()

        with zipfile.ZipFile(data, mode="w") as z:
            for id in ids:
                item = ItemService.get_by_id(id)

                item_image_dir = os.path.join(image_dir, f"item_{id}")

                thumbnail = item.thumbnail or 0

                thumbnail_file = f"images_{thumbnail}.jpg"
                z.write(
                    os.path.join(item_image_dir, thumbnail_file),
                    arcname=os.path.join("thumbnails", f"thumbnail_{id}.jpg"),
                )

        data.seek(0)

        zip_filename = f"thumbnails.zip"

        print("Sending")

        return send_file(
            data,
            mimetype="application/zip",
            as_attachment=True,
            attachment_filename=zip_filename,
        )


@api.route("/search/<search_str>")
@api.param("search_str", "Item search string")
class ItemSearchQueryResource(Resource):
    @use_args(SearchSchema(), location="query")
    def get(self, args, search_str: str):
        print(args)
        print(search_str)

        items = ItemService.search(
            search_str=search_str, filters={}, page=args["page"], page_size=5
        )

        return item_schema_many.dump(items)


@api.route("/search/<search_str>/amount")
@api.param("search_str", "Item search string")
class ItemSearchQueryAmountResource(Resource):
    def get(self, search_str: str):
        print("Amount query")

        print(search_str)

        return ItemService.search_amount(search_str=search_str)


@api.route("/<int:id>")
@api.param("id", "Item database ID")
class ItemIdResource(Resource):
    @responds(schema=ItemSchema)
    def get(self, id: int):
        """Get Single Item"""

        return ItemService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single Item"""
        from flask import jsonify

        id = ItemService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=ItemSchema, api=api)
    @responds(schema=ItemSchema)
    def put(self, id: int):
        """Update Single Item"""

        return ItemService.update(item, request.parsed_obj)


@api.route("/create")
class ItemCreateResource(Resource):
    def post(self):
        item_req = request.form

        dat = item_req.to_dict()["itemEntity"]
        obj = json.loads(dat)

        item_vali = item_schema.validate(obj)

        if item_vali:
            return SchemaErrResponse(item_vali, 402)

        item_data = item_schema.load(obj)

        item = ItemService.create(item_data)

        # create images dir
        item_images_path = os.path.join(
            app.config["UPLOAD_FOLDER"], "items", f"item_{item.id}"
        )

        try:
            os.mkdir(item_images_path)
        except OSError:
            pass

        # parse images from request files object

        images = ItemService.parse_images(item_images_path, request.files)

        updates = {"images": images, "thumbnail": 0 if len(images) > 0 else -1}

        ItemService.update(item, updates)

        return item_schema.dump(item)


# @api.route("/images/<int:id>")
# class ItemImagesResource(Resource):
#     def get(self, id: int):
#         output_dir = os.path.join("output", "image_zips")
#         try:
#             os.makedirs(output_dir)
#         except OSError:
#             pass

#         filename = f"item_{id}_images.zip"
#         images_dir = os.path.join("images", "items", f"item_{id}")

#         data = io.BytesIO()

#         with zipfile.ZipFile(data, mode="w") as z:
#             for file in os.listdir(images_dir):
#                 z.write(
#                     os.path.join(images_dir, file), arcname=os.path.join("images", file)
#                 )

#         data.seek(0)

#         print("diag", id)

#         return send_file(
#             data,
#             mimetype="application/zip",
#             as_attachment=True,
#             attachment_filename=filename,
#         )
