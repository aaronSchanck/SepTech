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

from datetime import datetime

log = logging.getLogger(__name__)


class OrderItem(db.Model):
    __tablename__ = "order_items"

    id = db.Column(db.Integer, primary_key=True)
    quantity = db.Column(db.Integer)
    price = db.Column(db.Integer)

    itemid = db.Column(db.Integer, db.ForeignKey("items.id"))
    item = db.relationship("Item")

    orderid = db.Column(db.Integer, db.ForeignKey("orders.id"))
    order = db.relationship("Order", back_populates="order_items")

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    def __init__(self, **kwargs):
        super(OrderItem, self).__init__(**kwargs)
        self.created_at = datetime.now()
        self.modified_at = datetime.now()

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        self.modified_at = datetime.now()

        return self
