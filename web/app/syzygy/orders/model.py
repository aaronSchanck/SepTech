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

from .interface import OrderInterface

log = logging.getLogger(__name__)


class Order(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "orders"

    id = db.Column(db.Integer, primary_key=True)
    userid = db.Column(db.Integer, db.ForeignKey("users.id"))

    ordered = db.Column(db.Boolean)

    date_created = db.Column(db.DateTime)
    date_shipped = db.Column(db.DateTime)
    date_delivered = db.Column(db.DateTime)

    total_price = db.Column(db.Numeric(10, 2))

    def update(self, changes: OrderInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
