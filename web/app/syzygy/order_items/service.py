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

from .interface import OrderItemInterface
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
    def update(
        order_item: OrderItem, OrderItem_change_updates: OrderItemInterface
    ) -> OrderItem:
        """[summary]

        :param order_item: The OrderItem to update in the database
        :type order_item: OrderItem
        :param OrderItem_change_updates: Dictionary object containing the new changes
        to update the OrderItem model object with
        :type OrderItem_change_updates: OrderItemInterface
        :return: The updated OrderItem model object
        :rtype: OrderItem
        """
        order_item.update(OrderItem_change_updates)
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
    def create(new_attrs: OrderItemInterface) -> OrderItem:
        """Creates a order_item object from the OrderItemInterface TypedDict

        :param new_attrs: A dictionary with the input into a OrderItem model
        :type new_attrs: OrderItemInterface
        :return: A new order_item object based on the input
        :rtype: OrderItem
        """

        new_order_item = OrderItem()

        db.session.add(new_order_item)
        db.session.commit()

        return new_order_item