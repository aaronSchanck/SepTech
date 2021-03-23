"""/web/app/syzygy/images/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Image
from .interface import ImageInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)


class ImageService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Image.query.all()

    @staticmethod
    def get_by_id(image_id: int) -> Image:
        """[summary]

        :param image_id: [description]
        :type image_id: int
        :return: [description]
        :rtype: [type]
        """
        return Image.query.get(image_id)

    @staticmethod
    def update(
        item: Image, Image_change_updates: ImageInterface
    ) -> Image:
        """[summary]

        :param item: The Image to update in the database
        :type item: Image
        :param Image_change_updates: Dictionary object containing the new changes
        to update the Image model object with
        :type Image_change_updates: ImageInterface
        :return: The updated Image model object
        :rtype: Image
        """
        item.update(Image_change_updates)
        db.session.commit()
        return item

    @staticmethod
    def delete_by_id(image_id: int) -> List:
        """Deletes a item from the table with the specified image_id

        :param image_id: Image's image_id
        :type image_id: int
        :return: List containing the deleted item, if found, otherwise an empty
        list
        :rtype: List
        """

        item = ImageService.get_by_id(image_id)
        if not item:
            return []
        db.session.delete(item)
        db.session.commit()
        return [image_id]

    @staticmethod
    def create(new_attrs: ImageInterface) -> Image:
        """Creates a item object from the ImageInterface TypedDict

        :param new_attrs: A dictionary with the input into a Image model
        :type new_attrs: ImageInterface
        :return: A new item object based on the input
        :rtype: Image
        """

        new_item = Image(
            image_data=new_attrs["image_data"]
        )

        db.session.add(new_item)
        db.session.commit()

        return new_item


def NormalResponse(response: dict, status: int) -> Response:
    """Function to return a normal response (200-299)

    :param response: Dictionary object with the content to be sent in the response
    :type response: dict
    :param status: Status code along with the response
    :type status: int
    :return: Response object with related response and status code
    :rtype: Response
    """

    return Response(
        mimetype="application/json", response=json.dumps(response), status=status
    )


def ErrResponse(response: str, status: int) -> Response:
    """Helper function to create an error response (400-499)

    :param response: String specifying the error message to send
    :type response: str
    :param status: Status code along with the response
    :type status: int
    :return: Response object with related response and status code
    :rtype: Response
    """

    return Response(
        mimetype="application/json",
        response=json.dumps({"error": response}),
        status=status,
    )
