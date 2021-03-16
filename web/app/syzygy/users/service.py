"""/web/app/syzygy/users/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

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
    def update(localUser: User, User_change_updates: UserInterface) -> User:
        """[summary]

        :param localUser: The User to update in the database
        :type localUser: User
        :param User_change_updates: Dictionary object containing the new changes
        to update the User model object with
        :type User_change_updates: UserInterface
        :return: The updated User model object
        :rtype: User
        """
        localUser.update(User_change_updates)
        db.session.commit()
        return localUser

    @staticmethod
    def delete_by_id(userid: int) -> List:
        """Deletes a localUser from the table with the specified userid

        :param userid: User's userid
        :type userid: int
        :return: List containing the deleted localUser, if found, otherwise an empty
        list
        :rtype: List
        """

        localUser = UserService.get_by_id(userid)
        if not localUser:
            return []
        db.session.delete(localUser)
        db.session.commit()
        return [userid]

    @staticmethod
    def create(new_attrs: UserInterface) -> User:
        """Creates a localUser object from the UserInterface TypedDict

        :param new_attrs: A dictionary with the input into a User model
        :type new_attrs: UserInterface
        :return: A new localUser object based on the input
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
        """Checks localUser credentials against database. If a localUser is found, then
        send the localUser information back to the client.

        :param username: User's username or email, to be figured out in the
        function
        :type username: str
        :param password: User's password
        :type password: str
        :return: User model from the table with the specified username/email and
        password
        :rtype: User
        """

        log.debug(f"Username: {username}\tPassword: {password}")

        if not username:
            return ErrResponse("No username/email entered", 400)

        if not password:
            return ErrResponse("No password entered", 400)

        # check to see if the username matches on email. If so, then the localUser
        # input a email address in the box instead of a username
        if re.match(r"(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$)", username):
            log.info("Input username was thought to be an email address")
            email = username
        else:
            log.info("Input username was thought to be a username")
            email = ""

        # get localUser structure from email address or username, whichever was
        # supplied
        localUser = (
            UserService.get_by_email(email)
            if email
            else UserService.get_by_username(username)
        )

        if localUser is None:
            log.info("No localUser was found for supplied username/email")
            return ErrResponse("Incorrect username/email", 400)

        if localUser.password != password:
            log.info("No localUser was found for supplied password")
            return ErrResponse("Incorrect password", 400)

        log.info(f"User {localUser.userid} was found and returned to client")

        # generate JWT token and concatenate

        return localUser


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
