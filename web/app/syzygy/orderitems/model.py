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

from .interface import UserInterface

log = logging.getLogger(__name__)


class OrderItem(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "orderitems"

    id = db.Column(db.Integer, primary_key=True)
    quantity = db.Column(db.Integer)
    price = db.Column(db.Numeric(10, 2))
    itemid = db.Column(db.Integer, db.ForeignKey("items.id"))
    orderid = db.Column(db.Integer, db.ForeignKey("orders.id"))

    def update(self, changes: UserInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
