"""/web/app/syzygy/banned_users/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
from datetime import datetime
from typing import List

import bcrypt
from app import db
from app.globals import *
from flask import Response

from .interface import BannedUserInterface
from .model import BannedUser

log = logging.getLogger(__name__)


class BannedUserService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return BannedUser.query.all()

    @staticmethod
    def get_by_id(id: int) -> BannedUser:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        banned_user = BannedUser.query.get(id)

        return banned_user

    @staticmethod
    def update(
        banned_user: BannedUser, BannedUser_change_updates: BannedUserInterface
    ) -> BannedUser:
        """[summary]

        :param banned_user: The BannedUser to update in the database
        :type banned_user: BannedUser
        :param BannedUser_change_updates: Dictionary object containing the new changes
        to update the BannedUser model object with
        :type BannedUser_change_updates: BannedUserInterface
        :return: The updated BannedUser model object
        :rtype: BannedUser
        """
        banned_user.update(BannedUser_change_updates)
        banned_user.modified_at = datetime.now()

        db.session.commit()
        return banned_user

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a banned_user from the table with the specified id

        :param id: BannedUser's id
        :type id: int
        :return: List containing the deleted banned_user, if found, otherwise an empty
        list
        :rtype: List
        """

        banned_user = BannedUserService.get_by_id(id)
        if not banned_user:
            return []
        db.session.delete(banned_user)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: BannedUserInterface) -> BannedUser:
        """Creates a banned_user object from the BannedUserInterface TypedDict

        :param new_attrs: A dictionary with the input into a BannedUser model
        :type new_attrs: BannedUserInterface
        :return: A new banned_user object based on the input
        :rtype: BannedUser
        """

        new_banned_user = BannedUser()

        db.session.add(new_banned_user)
        db.session.commit()

        return new_banned_user


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
