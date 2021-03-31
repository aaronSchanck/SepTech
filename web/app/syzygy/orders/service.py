"""/web/app/syzygy/orders/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Order
from .interface import OrderInterface
from flask import Response
import json
import logging
from utils.auth import encrypt_pw
import bcrypt

import re

from typing import List

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
    def get_by_id(orderid: int) -> Order:
        """[summary]

        :param orderid: [description]
        :type orderid: int
        :return: [description]
        :rtype: [type]
        """
        order = Order.query.get(orderid)

        return order

    @staticmethod
    def update(order: Order, Order_change_updates: OrderInterface) -> Order:
        """[summary]

        :param order: The Order to update in the database
        :type order: Order
        :param Order_change_updates: Dictionary object containing the new changes
        to update the Order model object with
        :type Order_change_updates: OrderInterface
        :return: The updated Order model object
        :rtype: Order
        """
        order.update(Order_change_updates)
        db.session.commit()
        return order

    @staticmethod
    def delete_by_id(orderid: int) -> List:
        """Deletes a order from the table with the specified orderid

        :param orderid: Order's orderid
        :type orderid: int
        :return: List containing the deleted order, if found, otherwise an empty
        list
        :rtype: List
        """

        order = OrderService.get_by_id(orderid)
        if not order:
            return []
        db.session.delete(order)
        db.session.commit()
        return [orderid]

    @staticmethod
    def create(new_attrs: OrderInterface) -> Order:
        """Creates a order object from the OrderInterface TypedDict

        :param new_attrs: A dictionary with the input into a Order model
        :type new_attrs: OrderInterface
        :return: A new order object based on the input
        :rtype: Order
        """

        new_order = Order()

        db.session.add(new_order)
        db.session.commit()

        return new_order


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
