"""/web/app/syzygy/images/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    ImageResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    ImageIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{image_id}.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource

from .interface import ImageInterface
from .model import Image
from .schema import ImageSchema
from .service import ImageService

api = Namespace("ImageImage")
log = logging.getLogger(__name__)


@api.route("/")
class ImageImageResource(Resource):
    """[summary]

    Args:
        Resource ([type]): [description]

    Returns:
        [type]: [description]
    """

    @responds(schema=ImageSchema(many=True))
    def get(self):
        """Get all Images"""

        return ImageService.get_all()

    # @accepts(schema=ImageSchema, api=api)
    # @responds(schema=ImageSchema)
    # def post(self):
    #     """Create a Single Image"""

    #     return ImageService.create(request.parsed_obj)


@api.route("/<int:image_id>")
@api.param("image_id", "Image database ID")
class ImageIdResource(Resource):
    @responds(schema=ImageSchema)
    def get(self, image_id: int):
        """Get Single Image"""

        return ImageService.get_by_id(image_id)

    # def delete(self, image_id: int):
    #     """Delete Single Image"""
    #     from flask import jsonify

    #     id = ImageService.delete_by_id(image_id)
    #     return jsonify(dict(status="Success", id=id))

    # @accepts(schema=ImageSchema, api=api)
    # @responds(schema=ImageSchema)
    # def put(self, image_id: int):
    #     """Update Single Image"""

    #     changes: ImageInterface = request.parsed_obj
    #     Image = ImageService.get_by_id(image_id)
    #     return ImageService.update(Image, changes)