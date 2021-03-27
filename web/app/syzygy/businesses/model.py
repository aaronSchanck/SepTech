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

    id = Column(Integer, primary_key=True)

    def update(self, changes: BusinessInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
