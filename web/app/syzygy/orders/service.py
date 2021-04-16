"""/web/app/syzygy/orders/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
from typing import List

from datetime import datetime

from app import db
from libs.response import ErrResponse, NormalResponse

from .model import Order


log = logging.getLogger(__name__)


class OrderService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Order.query.all()

    @staticmethod
    def get_by_id(id: int) -> Order:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        order = Order.query.get(id)

        return order

    @staticmethod
    def update(order: Order, updates: dict) -> Order:
        """[summary]

        :param order: [description]
        :type order: Order
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: Order
        """

        order.update(updates)
        db.session.commit()
        return order

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a order from the table with the specified id

        :param id: Order's id
        :type id: int
        :return: List containing the deleted order, if found, otherwise an empty
        list
        :rtype: List
        """

        order = OrderService.get_by_id(id)
        if not order:
            return []
        db.session.delete(order)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> Order:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: Order
        """

        new_order = Order(
            userid=new_attrs["userid"],
            ordered=new_attrs["ordered"],
            date_created=datetime.now(),
        )

        db.session.add(new_order)
        db.session.commit()

        return new_order

    @staticmethod
    def get_user_active_order(userid: int) -> list:
        return (
            Order.query.filter(Order.userid == userid)
            .filter(Order.ordered == False)
            .first()
        )

    def create_user_order_if_not_exists(userid: int) -> Order:
        order = OrderService.get_user_active_order(userid)

        print(order)

        if not order:
            order_data = {"userid": userid, "ordered": False}

            order = OrderService.create(order_data)
        return order

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass