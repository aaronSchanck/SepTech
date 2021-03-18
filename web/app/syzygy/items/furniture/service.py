"""/web/app/syzygy/furniture/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Furniture
from .interface import FurnitureInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)


class FurnitureService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Furniture.query.all()

    @staticmethod
    def get_by_id(itemid: int) -> Furniture:
        """[summary]

        :param itemid: [description]
        :type itemid: int
        :return: [description]
        :rtype: [type]
        """
        return Furniture.query.get(itemid)

    @staticmethod
    def get_by_email(email: str) -> Furniture:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """
        return Furniture.query.filter(Furniture.email == email).first()

    @staticmethod
    def get_by_name(name: str) -> Furniture:
        """[summary]

        :param name: [description]
        :type name: str
        :return: [description]
        :rtype: [type]
        """
        return Furniture.query.filter(Furniture.name == name).first()

    @staticmethod
    def update(furniture: Furniture, furniture_change_updates: FurnitureInterface) -> Furniture:
        """[summary]

        :param furniture: The Furniture to update in the database
        :type furniture: Furniture
        :param Furniture_change_updates: Dictionary object containing the new changes
        to update the Furniture model object with
        :type Furniture_change_updates: FurnitureInterface
        :return: The updated Furniture model object
        :rtype: Furniture
        """
        furniture.update(furniture_change_updates)
        db.session.commit()
        return furniture

    @staticmethod
    def delete_by_id(itemid: int) -> List:
        """Deletes a furniture from the table with the specified itemid

        :param itemid: Furniture's itemid
        :type itemid: int
        :return: List containing the deleted furniture, if found, otherwise an empty
        list
        :rtype: List
        """

        furniture = FurnitureService.get_by_id(itemid)
        if not furniture:
            return []
        db.session.delete(furniture)
        db.session.commit()
        return [itemid]

    @staticmethod
    def create(new_attrs: FurnitureInterface) -> Furniture:
        """Creates a furniture object from the FurnitureInterface TypedDict

        :param new_attrs: A dictionary with the input into a Furniture model
        :type new_attrs: FurnitureInterface
        :return: A new furniture object based on the input
        :rtype: Furniture
        """

        new_furniture = Furniture(
            name=new_attrs["name"], discriminator=new_attrs["discriminator"], subcategory_1=new_attrs["subcategory_1"], subcategory_2=new_attrs["subcategory_2"], subcategory_3=new_attrs["subcategory_3"], subcategory_4=new_attrs["subcategory_4"], subcategory_5=new_attrs["subcategory_5"],
        )

        db.session.add(new_furniture)
        db.session.commit()

        return new_furniture

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
