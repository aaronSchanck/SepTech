"""/web/app/syzygy/items/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Item
from .interface import ItemInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)


class ItemService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Item.query.all()

    @staticmethod
    def get_by_id(itemid: int) -> Item:
        """[summary]

        :param itemid: [description]
        :type itemid: int
        :return: [description]
        :rtype: [type]
        """
        return Item.query.get(itemid)

    @staticmethod
    def get_by_email(email: str) -> Item:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """
        return Item.query.filter(Item.email == email).first()

    @staticmethod
    def get_by_itemname(itemname: str) -> Item:
        """[summary]

        :param itemname: [description]
        :type itemname: str
        :return: [description]
        :rtype: [type]
        """
        return Item.query.filter(Item.itemname == itemname).first()

    @staticmethod
    def update(item: Item, Item_change_updates: ItemInterface) -> Item:
        """[summary]

        :param item: The Item to update in the database
        :type item: Item
        :param Item_change_updates: Dictionary object containing the new changes
        to update the Item model object with
        :type Item_change_updates: ItemInterface
        :return: The updated Item model object
        :rtype: Item
        """
        item.update(Item_change_updates)
        db.session.commit()
        return item

    @staticmethod
    def delete_by_id(itemid: int) -> List:
        """Deletes a item from the table with the specified itemid

        :param itemid: Item's itemid
        :type itemid: int
        :return: List containing the deleted item, if found, otherwise an empty
        list
        :rtype: List
        """

        item = ItemService.get_by_id(itemid)
        if not item:
            return []
        db.session.delete(item)
        db.session.commit()
        return [itemid]

    @staticmethod
    def create(new_attrs: ItemInterface) -> Item:
        """Creates a item object from the ItemInterface TypedDict

        :param new_attrs: A dictionary with the input into a Item model
        :type new_attrs: ItemInterface
        :return: A new item object based on the input
        :rtype: Item
        """

        new_item = Item(
            name=new_attrs["name"], discriminator=new_attrs["discriminator"]
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