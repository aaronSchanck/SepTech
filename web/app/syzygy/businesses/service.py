"""/web/app/syzygy/businesses/service.py

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
from libs.auth import encrypt_pw
from libs.response import ErrResponse, NormalResponse

from .interface import BusinessInterface
from .model import Business

log = logging.getLogger(__name__)


class BusinessService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Business.query.all()

    @staticmethod
    def get_by_id(businessid: int) -> Business:
        """[summary]

        :param businessid: [description]
        :type businessid: int
        :return: [description]
        :rtype: [type]
        """
        business = Business.query.get(businessid)

        if business is None:
            return ErrResponse("Requested business doesn't exist", 400)

        return business

    @staticmethod
    def get_by_email(email: str) -> Business:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """

        business = Business.query.filter(Business.email == email).first()

        return business

    @staticmethod
    def update(
        business: Business, Business_change_updates: BusinessInterface
    ) -> Business:
        """[summary]

        :param business: The Business to update in the database
        :type business: Business
        :param Business_change_updates: Dictionary object containing the new changes
        to update the Business model object with
        :type Business_change_updates: BusinessInterface
        :return: The updated Business model object
        :rtype: Business
        """
        business.update(Business_change_updates)
        db.session.commit()
        return business

    @staticmethod
    def delete_by_id(businessid: int) -> List:
        """Deletes a business from the table with the specified businessid

        :param businessid: Business's businessid
        :type businessid: int
        :return: List containing the deleted business, if found, otherwise an empty
        list
        :rtype: List
        """

        business = BusinessService.get_by_id(businessid)
        if not business:
            return []
        db.session.delete(business)
        db.session.commit()
        return [businessid]

    @staticmethod
    def create(new_attrs: BusinessInterface) -> Business:
        """Creates a business object from the BusinessInterface TypedDict

        :param new_attrs: A dictionary with the input into a Business model
        :type new_attrs: BusinessInterface
        :return: A new business object based on the input
        :rtype: Business
        """

        business = BusinessService.get_by_email(new_attrs["email"])

        if business is not None:
            return ErrResponse("Business with email already exists", 400)

        encrypted_pw = encrypt_pw(new_attrs["password"])

        phone_number_reformatted = new_attrs["phone_number"]

        # reformat phone number to remove extraneous (non-numeric) chars
        for c in ["(", ")", "-", " "]:
            if c in new_attrs["phone_number"]:
                phone_number_reformatted.replace(c, "")

        new_business = Business(
            business_name=new_attrs["business_name"],
            owner_full_name=new_attrs["owner_full_name"],
            email=new_attrs["email"],
            password=encrypted_pw,
            created_at=datetime.now(),
            modified_at=datetime.now(),
            phone_number=phone_number_reformatted,
            password_salt=new_attrs["password_salt"],
            description=new_attrs["description"],
        )

        db.session.add(new_business)
        db.session.commit()

        return new_business

    @staticmethod
    def login(email: str, password: str) -> Business:
        """Checks business credentials against database. If a business is found, then
        send the business information back to the client.

        :param email: Business's email
        :type email: str
        :param password: Business's password
        :type password: str
        :return: Business model from the table with the specified email and
        password
        :rtype: Business
        """

        log.debug(f"email: {email}\tPassword: {password}")

        if not email:
            return ErrResponse("No email entered", 400)

        if not password:
            return ErrResponse("No password entered", 400)

        business = BusinessService.get_by_email(email)

        if business is None:
            log.info("No business was found for supplied email")
            return ErrResponse("Incorrect email", 400)

        if not bcrypt.checkpw(password.encode("utf-8"), business.password):
            log.info("No business was found for supplied password")
            return ErrResponse("Incorrect password", 400)

        log.info(f"Business {business.id} was found and returned to client")

        # generate JWT token and concatenate

        return business