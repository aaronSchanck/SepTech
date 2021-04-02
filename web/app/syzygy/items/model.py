"""/web/app/syzygy/items/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from .interface import ItemInterface

log = logging.getLogger(__name__)


class Item(db.Model):
    __tablename__ = "items"

    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(127))
    quantity = db.Column(db.Integer)
    posted_at = db.Column(db.DateTime)
    sellerid = db.Column(db.Integer, db.ForeignKey("businesses.id"))  # businessid
    seller = db.relationship("Business", backref="items")
    price = db.Column(db.Numeric(10, 2))
    can_buy = db.Column(db.Boolean)
    can_bid = db.Column(db.Boolean)
    highest_bid = db.Column(db.Numeric(10, 2))
    highest_bid_user = db.Column(db.Integer)  # userid
    bidding_ends = db.Column(db.DateTime)
    quality = db.Column(db.String(63))
    category_id = db.Column(db.Integer, db.ForeignKey("categories.id"))

    thumbnail = db.Column(db.Integer)
    images = db.String
    item_variants = db.Column(db.ARRAY(db.Integer))  # array of itemid
    description = db.Column(db.Text())
    attributes = db.Column(db.JSON)

    def update(self, changes: ItemInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
