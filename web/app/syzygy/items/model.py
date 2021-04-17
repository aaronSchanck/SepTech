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

from datetime import datetime

log = logging.getLogger(__name__)


class Item(db.Model):
    """An Item model in the Syzygy database"""

    __tablename__ = "items"

    id = db.Column(db.Integer, primary_key=True)

    name = db.Column(db.String(127))
    quantity = db.Column(db.Integer)
    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    # business
    sellerid = db.Column(db.Integer, db.ForeignKey("businesses.id"))
    seller = db.relationship("Business", backref="items")

    # buyout data
    can_buy = db.Column(db.Boolean)

    price = db.Column(db.Integer())

    # bid data
    can_bid = db.Column(db.Boolean)

    min_bid_increment = db.Column(db.Integer())
    starting_bid = db.Column(db.Integer)

    highest_bid = db.Column(db.Integer())
    bidding_ends = db.Column(db.DateTime)

    # top bid user
    highest_bid_userid = db.Column(db.Integer, db.ForeignKey("users.id"))
    highest_bid_user = db.relationship("User")

    quality = db.Column(db.String(63))
    category_id = db.Column(db.Integer, db.ForeignKey("categories.id"))

    thumbnail = db.Column(db.Integer)
    images = db.Column(db.ARRAY(db.String))
    item_variants = db.Column(db.ARRAY(db.Integer))  # array of itemid
    description = db.Column(db.Text())
    attributes = db.Column(db.JSON)

    def __init__(self, **kwargs):
        super(Item, self).__init__(**kwargs)
        self.created_at = datetime.now()
        self.modified_at = datetime.now()

        self.thumbnail = 0

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        self.modified_at = datetime.now()

        return self