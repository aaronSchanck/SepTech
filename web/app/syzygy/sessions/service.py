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
    def update(session: Session, updates: dict) -> Session:
        """[summary]

        :param session: [description]
        :type session: Session
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: Session
        """

        session.update(updates)

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
    def create(new_attrs: dict) -> Session:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: Session
        """

        new_session = Session()

        db.session.add(new_session)
        db.session.commit()

        return new_session

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass