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

    id = Column(Integer, primary_key=True)
    email = Column(String(255), unique=True, nullable=False)
    password = Column(LargeBinary(127), nullable=False)
    full_name = Column(String(255))
    date_of_birth = Column(Date)
    created_at = Column(DateTime)
    modified_at = Column(DateTime)
    # billing_address = Column(ForeignKey)
    # mailing address = Column(ForeignKey)
    phone_number = Column(String(10))
    password_salt1 = Column(String(63))
    password_reset_code = Column(String(6))
    password_reset_timeout = Column(DateTime)
    # last_successful_login = Column(DateTime)
    # last_unsuccessful_login = Column(DateTime)

    # order
    orders = db.relationship("Order", backref="user")

    admin_level = Column(Integer)

    def update(self, changes: UserInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
