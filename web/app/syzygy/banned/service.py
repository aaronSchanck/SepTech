"""/web/app/syzygy/banned/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
import re
import secrets
from datetime import datetime, timedelta
from typing import List

from app.globals import *
import bcrypt
from app import db
from flask import Response
from utils.auth import encrypt_pw

from .interface import BannedInterface
from .model import Banned

log = logging.getLogger(__name__)


class BannedService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Banned.query.all()

    @staticmethod
    def get_by_id(id: int) -> Banned:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        banned = Banned.query.get(id)

        return banned

    @staticmethod
    def update(banned: Banned, Banned_change_updates: BannedInterface) -> Banned:
        """[summary]

        :param banned: The Banned to update in the database
        :type banned: Banned
        :param Banned_change_updates: Dictionary object containing the new changes
        to update the Banned model object with
        :type Banned_change_updates: BannedInterface
        :return: The updated Banned model object
        :rtype: Banned
        """
        banned.update(Banned_change_updates)
        banned.modified_at = datetime.now()

        db.session.commit()
        return banned

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a banned from the table with the specified id

        :param id: Banned's id
        :type id: int
        :return: List containing the deleted banned, if found, otherwise an empty
        list
        :rtype: List
        """

        banned = BannedService.get_by_id(id)
        if not banned:
            return []
        db.session.delete(banned)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: BannedInterface) -> Banned:
        """Creates a banned object from the BannedInterface TypedDict

        :param new_attrs: A dictionary with the input into a Banned model
        :type new_attrs: BannedInterface
        :return: A new banned object based on the input
        :rtype: Banned
        """

        new_banned = Banned()

        db.session.add(new_banned)
        db.session.commit()

        return new_banned


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
