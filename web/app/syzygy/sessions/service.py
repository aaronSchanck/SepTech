"""/web/app/syzygy/session/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging
from datetime import datetime
from typing import List

from app import db
from libs.response import ErrResponse, NormalResponse

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

        return session

    @staticmethod
    def update(session: Session, Session_change_updates: SessionInterface) -> Session:
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