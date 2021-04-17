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
from datetime import datetime
from typing import List

import bcrypt
from app import db
from flask import Response
from libs.response import ErrResponse, NormalResponse

from ..items.service import ItemService
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

        new_order_item = OrderItem(
            quantity=new_attrs["quantity"],
            price=new_attrs["price"],
            itemid=new_attrs["itemid"],
            orderid=new_attrs["orderid"],
        )

        db.session.add(new_order_item)
        db.session.commit()

        return new_order_item

    @staticmethod
    def order_item_from_item(
        orderid: int, itemid: int, quantity: int
    ) -> (OrderItem, Response):
        item = ItemService.get_by_id(itemid)

        similar_order_item = OrderItemService.find_similar_items(orderid, itemid)

        order_item_data = {
            "quantity": quantity,
            "price": item.price,
            "itemid": itemid,
            "orderid": orderid,
        }

        if similar_order_item:
            print(similar_order_item.quantity == ItemService.get_item_stock(itemid))
            if similar_order_item.quantity == ItemService.get_item_stock(itemid):
                return None, ErrResponse(
                    "Cart amount already at maximum stock for item", 400
                )

            order_item = OrderItemService.combine_order_items(
                similar_order_item, order_item_data
            )
        else:
            order_item = OrderItemService.create(order_item_data)

        return order_item, NormalResponse("Success", 200)

    @staticmethod
    def combine_order_items(
        order_item1: OrderItem, new_order_item_data: dict
    ) -> OrderItem:
        total_quantity = order_item1.quantity + new_order_item_data["quantity"]
        item_stock = ItemService.get_item_stock(new_order_item_data["itemid"])

        if total_quantity > item_stock:
            total_quantity = item_stock

        order_item_data = {
            "quantity": total_quantity,
        }

        return OrderItemService.update(order_item1, order_item_data)

    @staticmethod
    def find_similar_items(orderid: int, itemid: int) -> OrderItem:
        order_items: List[OrderItem] = (
            OrderItem.query.filter(OrderItem.orderid == orderid)
            .filter(OrderItem.itemid == itemid)
            .all()
        )

        if len(order_items) == 0:
            return None
        elif len(order_items) == 1:
            return order_items[0]
        else:
            merged_order_item = merge_multiple_order_items(order_items)
            return merged_order_item

    @staticmethod
    def merge_multiple_order_items(order_items: List[OrderItem]) -> OrderItem:
        min_create = order_items[0].created_at
        min_create_id = order_items[0].id

        for order_item in order_items:
            if order_item.created_at < min_create:
                min_create = order_item.created_at
                min_create_id = order_item.id

        total_quantity = 0

        for order_item in order_items:
            total_quantity += order_item.quantity

        main_order_item = OrderItemService.get_by_id(id=min_create_id)

        main_order_item = OrderItemService.update(
            main_order_item, {"quantity": total_quantity}
        )

        for order_item in order_items:
            if min_create_id != order_item.id:
                OrderItemService.delete_by_id(order_item.id)

        return main_order_item

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass
