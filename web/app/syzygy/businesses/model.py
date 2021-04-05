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
    business_name = db.Column(db.String, nullable=False)
    email = db.Column(db.String(255), unique=True, nullable=False)
    password = db.Column(db.LargeBinary(127), nullable=False)
    owner_full_name = db.Column(db.String(255))
    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    profile_picture = db.Column(db.String)

    def update(self, changes: BusinessInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
