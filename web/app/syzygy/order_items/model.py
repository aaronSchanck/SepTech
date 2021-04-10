"""/web/app/syzygy/order_items/model.py

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


class OrderItem(db.Model):
    __tablename__ = "order_items"

    id = db.Column(db.Integer, primary_key=True)
    quantity = db.Column(db.Integer)
    price = db.Column(db.Numeric(10, 2))

    itemid = db.Column(db.Integer, db.ForeignKey("items.id"))
    item = db.relationship("Item")

    orderid = db.Column(db.Integer, db.ForeignKey("orders.id"))
    order = db.relationship("Order", back_populates="order_items")

    added_at = db.Column(db.DateTime)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
