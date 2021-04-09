"""/web/app/syzygy/bids/model.py

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

from .interface import BidInterface

log = logging.getLogger(__name__)


class Bid(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "bids"

    id = db.Column(db.Integer, primary_key=True)

    def update(self, changes: BidInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self

    def __repr__(self):
        pass
