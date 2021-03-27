"""/web/app/syzygy/users/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import OrderItems
from .interface import OrderItemsInterface
from flask import Response
import json
import logging
from utils.auth import encrypt_pw
import bcrypt

import re

from typing import List

log = logging.getLogger(__name__)


class OrderItemsService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return OrderItems.query.all()

    @staticmethod
    def get_by_id(userid: int) -> OrderItems:
        """[summary]

        :param userid: [description]
        :type userid: int
        :return: [description]
        :rtype: [type]
        """
        user = OrderItems.query.get(userid)

        if user is None:
            return ErrResponse("Requested user doesn't exist", 400)

        return user

    @staticmethod
    def get_by_email(email: str) -> OrderItems:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """
        user = OrderItems.query.filter(OrderItems.email == email).first()

        if user is None:
            return ErrResponse("Requested user doesn't exist", 400)

        return user

    @staticmethod
    def update(user: OrderItems, OrderItems_change_updates: OrderItemsInterface) -> OrderItems:
        """[summary]

        :param user: The OrderItems to update in the database
        :type user: OrderItems
        :param OrderItems_change_updates: Dictionary object containing the new changes
        to update the OrderItems model object with
        :type OrderItems_change_updates: OrderItemsInterface
        :return: The updated OrderItems model object
        :rtype: OrderItems
        """
        user.update(OrderItems_change_updates)
        db.session.commit()
        return user

    @staticmethod
    def delete_by_id(userid: int) -> List:
        """Deletes a user from the table with the specified userid

        :param userid: OrderItems's userid
        :type userid: int
        :return: List containing the deleted user, if found, otherwise an empty
        list
        :rtype: List
        """

        user = OrderItemsService.get_by_id(userid)
        if not user:
            return []
        db.session.delete(user)
        db.session.commit()
        return [userid]

    @staticmethod
    def create(new_attrs: OrderItemsInterface) -> OrderItems:
        """Creates a user object from the OrderItemsInterface TypedDict

        :param new_attrs: A dictionary with the input into a OrderItems model
        :type new_attrs: OrderItemsInterface
        :return: A new user object based on the input
        :rtype: OrderItems
        """

        encrypted_pw = encrypt_pw(new_attrs["password"])

        new_user = OrderItems(
            email=new_attrs["email"],
            password=encrypted_pw,
            full_name=new_attrs["full_name"],
            date_of_birth=new_attrs["date_of_birth"],
            created_at=new_attrs["created_at"],
            modified_at=new_attrs["modified_at"],
            phone_number=new_attrs["phone_number"],
            password_salt1=new_attrs["password_salt1"],
        )

        db.session.add(new_user)
        db.session.commit()

        return new_user

    @staticmethod
    def login(email: str, password: str) -> OrderItems:
        """Checks user credentials against database. If a user is found, then
        send the user information back to the client.

        :param email: OrderItems's email
        :type email: str
        :param password: OrderItems's password
        :type password: str
        :return: OrderItems model from the table with the specified email and
        password
        :rtype: OrderItems
        """

        log.debug(f"email: {email}\tPassword: {password}")

        if not email:
            return ErrResponse("No email entered", 400)

        if not password:
            return ErrResponse("No password entered", 400)

        user = OrderItemsService.get_by_email(email)

        if user is None:
            log.info("No user was found for supplied email")
            return ErrResponse("Incorrect email", 400)

        if not bcrypt.checkpw(password.encode("utf-8"), user.password):
            log.info("No user was found for supplied password")
            return ErrResponse("Incorrect password", 400)

        log.info(f"OrderItems {user.userid} was found and returned to client")

        # generate JWT token and concatenate

        return user


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
