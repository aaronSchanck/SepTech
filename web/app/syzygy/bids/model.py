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

    userid = db.Column(db.Integer, db.ForeignKey("users.id"))
    user = db.relationship("User")

    bid_amount = db.Column(db.Numeric(10, 2))

    bid_posted_at = db.Column(db.DateTime)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self

    def __repr__(self):
        pass
