from app import db
from .model import User
from .interface import UserInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)

class UserService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return User.query.all()

    @staticmethod
    def get_by_id(userid: int) -> User:
        """[summary]

        :param userid: [description]
        :type userid: int
        :return: [description]
        :rtype: [type]
        """
        return User.query.get(userid)

    @staticmethod
    def get_by_email(email: str) -> User:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """
        return User.query.filter(User.email == email).first()

    @staticmethod
    def get_by_username(username: str) -> User:
        """[summary]

        :param username: [description]
        :type username: str
        :return: [description]
        :rtype: [type]
        """
        return User.query.filter(User.username == username).first()

    @staticmethod
    def update(user: User, User_change_updates: UserInterface) -> User:
        """[summary]

        :param user: [description]
        :type user: User
        :param User_change_updates: [description]
        :type User_change_updates: UserInterface
        :return: [description]
        :rtype: User
        """
        user.update(User_change_updates)
        db.session.commit()
        return user

    @staticmethod
    def delete_by_id(userid: int) -> List:
        """[summary]

        :param userid: [description]
        :type userid: int
        :return: [description]
        :rtype: [type]
        """

        user = User.query.filter(User.userid == userid).first()
        if not user:
            return []
        db.session.delete(user)
        db.session.commit()
        return [userid]

    @staticmethod
    def create(new_attrs: UserInterface) -> User:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: UserInterface
        :return: [description]
        :rtype: User
        """

        new_user = User(
            username=new_attrs["username"],
            email=new_attrs["email"],
            password=new_attrs["password"],
            first_name=new_attrs["first_name"],
            last_name=new_attrs["last_name"],
            date_of_birth=new_attrs["date_of_birth"],
            created_at=new_attrs["created_at"],
            modified_at=new_attrs["modified_at"],
        )

        db.session.add(new_user)
        db.session.commit()

        return new_user

    @staticmethod
    def login(username: str, password: str) -> User:
        """Checks user credentials against database. If a user is found, then
        send the user information back to the client.

        :param username: Username of the user
        :type username: str
        :param password: [description]
        :type password: str
        :return: [description]
        :rtype: User
        """

        log.debug(f"Username: {username}\tPassword: {password}")

        if not username:
            return ErrResponse("No email/username entered", 400)

        if not password:
            return ErrResponse("No password entered", 400)

        if re.match(r"(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)", username):
            email = username
        else:
            email = ""

        user = (
            UserService.get_by_email(email)
            if email
            else UserService.get_by_username(username)
        )

        print(user)

        if user is None:
            return ErrResponse("Incorrect email", 400)

        if user.password != password:
            return ErrResponse("Incorrect password", 400)

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
