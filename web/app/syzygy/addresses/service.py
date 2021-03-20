"""/web/app/syzygy/address/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Address
from .interface import AddressInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)


class AddressService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Address.query.all()

    @staticmethod
    def get_by_id(addressid: int) -> Address:
        """[summary]

        :param addressid: [description]
        :type addressid: int
        :return: [description]
        :rtype: [type]
        """
        return Address.query.get(addressid)

    @staticmethod
    def get_by_email(email: str) -> Address:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """
        return Address.query.filter(Address.email == email).first()

    @staticmethod
    def update(address: Address, Address_change_updates: AddressInterface) -> Address:
        """[summary]

        :param address: The Address to update in the database
        :type address: Address
        :param Address_change_updates: Dictionary object containing the new changes
        to update the Address model object with
        :type Address_change_updates: AddressInterface
        :return: The updated Address model object
        :rtype: Address
        """
        address.update(Address_change_updates)
        db.session.commit()
        return address

    @staticmethod
    def delete_by_id(addressid: int) -> List:
        """Deletes a address from the table with the specified addressid

        :param addressid: Address's addressid
        :type addressid: int
        :return: List containing the deleted address, if found, otherwise an empty
        list
        :rtype: List
        """

        address = AddressService.get_by_id(addressid)
        if not address:
            return []
        db.session.delete(address)
        db.session.commit()
        return [addressid]

    @staticmethod
    def create(new_attrs: AddressInterface) -> Address:
        """Creates a address object from the AddressInterface TypedDict

        :param new_attrs: A dictionary with the input into a Address model
        :type new_attrs: AddressInterface
        :return: A new address object based on the input
        :rtype: Address
        """

        new_address = Address(
            email=new_attrs["email"],
            password=new_attrs["password"],
            first_name=new_attrs["first_name"],
            last_name=new_attrs["last_name"],
            date_of_birth=new_attrs["date_of_birth"],
            created_at=new_attrs["created_at"],
            modified_at=new_attrs["modified_at"],
        )

        db.session.add(new_address)
        db.session.commit()

        return new_address

    @staticmethod
    def create_address(email, password, salt, first_name, last_name, phone_number):
        new_address = Address(
            email=email,
            password=password,  #hash again
            first_name=first_name,
            last_name=last_name,
            phone_number=phone_number,
            password_salt1=salt
            date_of_birth=
        )

    @staticmethod
    def login(email: str, password: str) -> Address:
        """Checks address credentials against database. If a address is found, then
        send the address information back to the client.

        :param email: Address's email
        :type email: str
        :param password: Address's password
        :type password: str
        :return: Address model from the table with the specified email and
        password
        :rtype: Address
        """

        log.debug(f"email: {email}\tPassword: {password}")

        if not email:
            return ErrResponse("No email entered", 400)

        if not password:
            return ErrResponse("No password entered", 400)

        address = AddressService.get_by_email(email)

        if address is None:
            log.info("No address was found for supplied email")
            return ErrResponse("Incorrect email", 400)

        if address.password != password:
            log.info("No address was found for supplied password")
            return ErrResponse("Incorrect password", 400)

        log.info(f"Address {address.addressid} was found and returned to client")

        # generate JWT token and concatenate

        return address


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
