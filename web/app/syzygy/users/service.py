

from app import db
from .model import User
from .interface import UserInterface
from flask import Response
import json

from typing import Array


class UserService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return User.query.all()

    @staticmethod
    def get_by_id(userid: int):
        """[summary]

        :param userid: [description]
        :type userid: int
        :return: [description]
        :rtype: [type]
        """
        return User.query.get(userid)

    @staticmethod
    def get_by_email(email: str):
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """
        return User.query.filter(User.email == email).first()

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
    def delete_by_id(userid: int) -> list:
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
            modified_at=new_attrs["modified_at"])

        db.session.add(new_user)
        db.session.commit()

        return new_user

    @staticmethod
    def login(email: str, password: str) -> User:
        """
        Checks user credentials against database. If a user is found, then send
        the user information back to the client.
        """

        if not email or not password:
            return ErrResponse("No credentials entered", 400)
        user = UserService.get_by_email(email)

        if user is None:
            return ErrResponse("Incorrect email", 400)

        if user.password != password:
            return ErrResponse("Incorrect password", 400)

        # generate JWT token and concatenate

        return user


def ErrResponse(response: str, status: int):
    """
    Response function that returns an error response to the client
    """

    return Response(
        mimetype="application/json",
        response=json.dumps({"error": response}),
        status=status
    )


def NormalResponse(response: dict, status: int):
    """
    Response function that returns a non-error response to the client
    """

    return Response(
        mimetype="application/json",
        response=json.dumps(response),
        status=status
    )
