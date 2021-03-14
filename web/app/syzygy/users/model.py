"""/web/app/syzygy/localUsers/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db
from sqlalchemy import *

from .interface import UserInterface

log = logging.getLogger(__name__)


class User(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    # TODO: Change date_of_birth to Date from DateTime

    __tablename__ = "localUsers"

    userid = Column(Integer, primary_key=True)
    email = Column(String(127), unique=True, nullable=False)
    username = Column(String(127), nullable=False)
    password = Column(String(127), nullable=False)
    first_name = Column(String(127))
    last_name = Column(String(127))
    date_of_birth = Column(DateTime)
    created_at = Column(DateTime)
    modified_at = Column(DateTime)

    def update(self, changes: UserInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
