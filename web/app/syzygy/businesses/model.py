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

from .interface import BusinessInterface

log = logging.getLogger(__name__)


class Business(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "businesses"

    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(255), unique=True, nullable=False)
    password = db.Column(db.LargeBinary(127), nullable=False)
    full_name = db.Column(db.String(255))
    date_of_birth = db.Column(db.Date)
    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    # products = db.Column(db.)

    def update(self, changes: BusinessInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
