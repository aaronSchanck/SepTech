"""/web/app/syzygy/users/model.py

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

    __tablename__ = "users"

    userid = Column(Integer, primary_key=True)
    email = Column(String(127), unique=True, nullable=False)
    password = Column(String(127), nullable=False)
    first_name = Column(String(127))
    last_name = Column(String(127))
    date_of_birth = Column(Date)
    created_at = Column(DateTime)
    modified_at = Column(DateTime)
    phone_number = Column(String(15))
    password_salt1 = Column(String(63))
    password_salt2 = Column(String(63))

    def update(self, changes: UserInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
