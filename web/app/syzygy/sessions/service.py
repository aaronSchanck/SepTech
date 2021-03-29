"""/web/app/syzygy/session/service.py

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
import secrets
from datetime import datetime, timedelta
from typing import List

from app.globals import *
import bcrypt
from app import db
from flask import Response
from utils.auth import encrypt_pw

from .interface import SessionInterface
from .model import Session

log = logging.getLogger(__name__)


class SessionService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Session.query.all()

    @staticmethod
    def get_by_id(id: int) -> Session:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        session = Session.query.get(id)

        # if session is None:
        #     return ErrResponse("Requested session doesn't exist", 400)

        return session

    @staticmethod
    def update(
        session: Session, Session_change_updates: SessionInterface
    ) -> Session:
        """[summary]

        :param session: The Session to update in the database
        :type session: Session
        :param Session_change_updates: Dictionary object containing the new changes
        to update the Session model object with
        :type Session_change_updates: SessionInterface
        :return: The updated Session model object
        :rtype: Session
        """
        session.update(Session_change_updates)
        session.modified_at = datetime.now()

        db.session.commit()
        return session

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a session from the table with the specified id

        :param id: Session's id
        :type id: int
        :return: List containing the deleted session, if found, otherwise an empty
        list
        :rtype: List
        """

        session = SessionService.get_by_id(id)
        if not session:
            return []
        db.session.delete(session)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: SessionInterface) -> Session:
        """Creates a session object from the SessionInterface TypedDict

        :param new_attrs: A dictionary with the input into a Session model
        :type new_attrs: SessionInterface
        :return: A new session object based on the input
        :rtype: Session
        """

        new_session = Session()

        db.session.add(new_session)
        db.session.commit()

        return new_session


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
