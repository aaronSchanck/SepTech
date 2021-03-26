"""/web/app/syzygy/orderdetails/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import OrderDetail
from .interface import OrderDetailInterface
from flask import Response
import json
import logging
from utils.auth import encrypt_pw
import bcrypt

import re

from typing import List

log = logging.getLogger(__name__)


class OrderDetailService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return OrderDetail.query.all()

    @staticmethod
    def get_by_id(orderdetailid: int) -> OrderDetail:
        """[summary]

        :param orderdetailid: [description]
        :type orderdetailid: int
        :return: [description]
        :rtype: [type]
        """
        orderdetail = OrderDetail.query.get(orderdetailid)

        if orderdetail is None:
            return ErrResponse("Requested orderdetail doesn't exist", 400)

        return orderdetail

    @staticmethod
    def update(
        orderdetail: OrderDetail, OrderDetail_change_updates: OrderDetailInterface
    ) -> OrderDetail:
        """[summary]

        :param orderdetail: The OrderDetail to update in the database
        :type orderdetail: OrderDetail
        :param OrderDetail_change_updates: Dictionary object containing the new changes
        to update the OrderDetail model object with
        :type OrderDetail_change_updates: OrderDetailInterface
        :return: The updated OrderDetail model object
        :rtype: OrderDetail
        """
        orderdetail.update(OrderDetail_change_updates)
        db.session.commit()
        return orderdetail

    @staticmethod
    def delete_by_id(orderdetailid: int) -> List:
        """Deletes a orderdetail from the table with the specified orderdetailid

        :param orderdetailid: OrderDetail's orderdetailid
        :type orderdetailid: int
        :return: List containing the deleted orderdetail, if found, otherwise an empty
        list
        :rtype: List
        """

        orderdetail = OrderDetailService.get_by_id(orderdetailid)
        if not orderdetail:
            return []
        db.session.delete(orderdetail)
        db.session.commit()
        return [orderdetailid]

    @staticmethod
    def create(new_attrs: OrderDetailInterface) -> OrderDetail:
        """Creates a orderdetail object from the OrderDetailInterface TypedDict

        :param new_attrs: A dictionary with the input into a OrderDetail model
        :type new_attrs: OrderDetailInterface
        :return: A new orderdetail object based on the input
        :rtype: OrderDetail
        """

        new_orderdetail = OrderDetail()

        db.session.add(new_orderdetail)
        db.session.commit()

        return new_orderdetail


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
