"""/web/app/syzygy/subscriptions/model.py

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


class Subscription(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "subscriptions"

    id = db.Column(db.Integer, primary_key=True)

    userid = db.Column(db.Integer)
    user = db.relationship()

    tier = db.Column(db.Integer)

    recurring = db.Column(db.Boolean)

    subscription_ends = db.Column(db.DateTime)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
