"""/web/app/syzygy/banned_businesses/service.py

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

from .interface import BannedBusinessInterface
from .model import BannedBusiness

log = logging.getLogger(__name__)


class BannedBusinessService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return BannedBusiness.query.all()

    @staticmethod
    def get_by_id(id: int) -> BannedBusiness:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        banned_business = BannedBusiness.query.get(id)

        return banned_business

    @staticmethod
    def update(
        banned_business: BannedBusiness,
        BannedBusiness_change_updates: BannedBusinessInterface,
    ) -> BannedBusiness:
        """[summary]

        :param banned_business: The BannedBusiness to update in the database
        :type banned_business: BannedBusiness
        :param BannedBusiness_change_updates: Dictionary object containing the new changes
        to update the BannedBusiness model object with
        :type BannedBusiness_change_updates: BannedBusinessInterface
        :return: The updated BannedBusiness model object
        :rtype: BannedBusiness
        """
        banned_business.update(BannedBusiness_change_updates)
        banned_business.modified_at = datetime.now()

        db.session.commit()
        return banned_business

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a banned_business from the table with the specified id

        :param id: BannedBusiness's id
        :type id: int
        :return: List containing the deleted banned_business, if found, otherwise an empty
        list
        :rtype: List
        """

        banned_business = BannedBusinessService.get_by_id(id)
        if not banned_business:
            return []
        db.session.delete(banned_business)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: BannedBusinessInterface) -> BannedBusiness:
        """Creates a banned_business object from the BannedBusinessInterface TypedDict

        :param new_attrs: A dictionary with the input into a BannedBusiness model
        :type new_attrs: BannedBusinessInterface
        :return: A new banned_business object based on the input
        :rtype: BannedBusiness
        """

        new_banned_business = BannedBusiness()

        db.session.add(new_banned_business)
        db.session.commit()

        return new_banned_business


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
