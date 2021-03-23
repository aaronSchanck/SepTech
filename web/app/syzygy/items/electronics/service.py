"""/web/app/syzygy/electronics/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Electronic
from .interface import ElectronicInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)


class ElectronicService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Electronic.query.all()

    @staticmethod
    def get_by_id(itemid: int) -> Electronic:
        """[summary]

        :param itemid: [description]
        :type itemid: int
        :return: [description]
        :rtype: [type]
        """
        return Electronic.query.get(itemid)

    @staticmethod
    def get_by_email(email: str) -> Electronic:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """
        return Electronic.query.filter(Electronic.email == email).first()

    @staticmethod
    def get_by_name(name: str) -> Electronic:
        """[summary]

        :param name: [description]
        :type name: str
        :return: [description]
        :rtype: [type]
        """
        return Electronic.query.filter(Electronic.name == name).first()

    @staticmethod
    def update(electronic: Electronic, Electronic_change_updates: ElectronicInterface) -> Electronic:
        """[summary]

        :param electronic: The Electronic to update in the database
        :type electronic: Electronic
        :param Electronic_change_updates: Dictionary object containing the new changes
        to update the Electronic model object with
        :type Electronic_change_updates: ElectronicInterface
        :return: The updated Electronic model object
        :rtype: Electronic
        """
        electronic.update(Electronic_change_updates)
        db.session.commit()
        return electronic

    @staticmethod
    def delete_by_id(itemid: int) -> List:
        """Deletes a electronic from the table with the specified itemid

        :param itemid: Electronic's itemid
        :type itemid: int
        :return: List containing the deleted electronic, if found, otherwise an empty
        list
        :rtype: List
        """

        electronic = ElectronicService.get_by_id(itemid)
        if not electronic:
            return []
        db.session.delete(electronic)
        db.session.commit()
        return [itemid]

    @staticmethod
    def create(new_attrs: ElectronicInterface) -> Electronic:
        """Creates a electronic object from the ElectronicInterface TypedDict

        :param new_attrs: A dictionary with the input into a Electronic model
        :type new_attrs: ElectronicInterface
        :return: A new electronic object based on the input
        :rtype: Electronic
        """

        new_electronic = Electronic(
            name=new_attrs["name"], discriminator=new_attrs["discriminator"], subcategory_1=new_attrs["subcategory_1"], subcategory_2=new_attrs["subcategory_2"], subcategory_3=new_attrs["subcategory_3"], subcategory_4=new_attrs["subcategory_4"], subcategory_5=new_attrs["subcategory_5"],
        )

        db.session.add(new_electronic)
        db.session.commit()

        return new_electronic

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