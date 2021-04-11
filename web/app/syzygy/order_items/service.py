"""/web/app/syzygy/order_items/service.py

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
from typing import List

import bcrypt
from app import db
from libs.response import ErrResponse, NormalResponse

from .model import OrderItem

log = logging.getLogger(__name__)


class OrderItemService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return OrderItem.query.all()

    @staticmethod
    def get_by_id(id: int) -> OrderItem:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        order_item = OrderItem.query.get(id)

        return order_item

    @staticmethod
    def update(order_item: OrderItem, updates: dict) -> OrderItem:
        """[summary]

        :param order_item: [description]
        :type order_item: OrderItem
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: OrderItem
        """

        order_item.update(updates)
        db.session.commit()

        return order_item

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a order_item from the table with the specified id

        :param id: OrderItem's id
        :type id: int
        :return: List containing the deleted order_item, if found, otherwise an empty
        list
        :rtype: List
        """

        order_item = OrderItemService.get_by_id(id)
        if not order_item:
            return []
        db.session.delete(order_item)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> OrderItem:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: OrderItem
        """

        new_order_item = OrderItem()

        db.session.add(new_order_item)
        db.session.commit()

        return new_order_item

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass