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
    user = db.relationship("User", back_populates="orders")

    ordered = db.Column(db.Boolean)

    date_created = db.Column(db.DateTime)
    date_shipped = db.Column(db.DateTime)
    date_delivered = db.Column(db.DateTime)

    total_price = db.Column(db.Numeric(10, 2))

    order_items = db.relationship("OrderItem", back_populates="order")

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
