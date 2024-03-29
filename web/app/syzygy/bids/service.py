"""/web/app/syzygy/bids/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging
from typing import List

import bcrypt
from app import db
from libs.response import ErrResponse, NormalResponse
from libs.auth import encrypt_pw

from .model import Bid

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
    def update(bid: Bid, updates: dict) -> Bid:
        """[summary]

        :param bid: The Bid to update in the database
        :type bid: Bid
        :param updates: Dictionary object containing the new changes
        to update the Bid model object with
        :type updates: dict
        :return: The updated Bid model object
        :rtype: Bid
        """
        bid.update(updates)
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
    def create(new_attrs: dict) -> Bid:
        """Creates a bid object from the dict TypedDict

        :param new_attrs: A dictionary with the input into a Bid model
        :type new_attrs: dict
        :return: A new bid object based on the input
        :rtype: Bid
        """

        new_bid = Bid()

        db.session.add(new_bid)
        db.session.commit()

        return new_bid

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass
