"""/web/app/syzygy/bids/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Bid
from .interface import BidInterface
from flask import Response
import json
import logging
from libs.auth import encrypt_pw
import bcrypt

from datetime import datetime

import re

from typing import List

log = logging.getLogger(__name__)


class BidService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Bid.query.all()

    @staticmethod
    def get_by_id(id: int) -> Bid:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        bid = Bid.query.get(id)

        if bid is None:
            return ErrResponse("Requested bid doesn't exist", 400)

        return bid

    @staticmethod
    def get_by_email(email: str) -> Bid:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """

        bid = Bid.query.filter(Bid.email == email).first()

        return bid

    @staticmethod
    def update(bid: Bid, Bid_change_updates: BidInterface) -> Bid:
        """[summary]

        :param bid: The Bid to update in the database
        :type bid: Bid
        :param Bid_change_updates: Dictionary object containing the new changes
        to update the Bid model object with
        :type Bid_change_updates: BidInterface
        :return: The updated Bid model object
        :rtype: Bid
        """
        bid.update(Bid_change_updates)
        db.session.commit()
        return bid

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a bid from the table with the specified id

        :param id: Bid's id
        :type id: int
        :return: List containing the deleted bid, if found, otherwise an empty
        list
        :rtype: List
        """

        bid = BidService.get_by_id(id)
        if not bid:
            return []
        db.session.delete(bid)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: BidInterface) -> Bid:
        """Creates a bid object from the BidInterface TypedDict

        :param new_attrs: A dictionary with the input into a Bid model
        :type new_attrs: BidInterface
        :return: A new bid object based on the input
        :rtype: Bid
        """

        new_bid = Bid()

        db.session.add(new_bid)
        db.session.commit()

        return new_bid


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
