"""/web/app/syzygy/item_reviews/model.py

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


class ItemReview(db.Model):
    __tablename__ = "item_reviews"

    id = db.Column(db.Integer, primary_key=True)

    userid = db.Column(db.Integer, db.ForeignKey("users.id"))
    user = db.relationship("User", back_populates="item_reviews")

    itemid = db.Column(db.Integer, db.ForeignKey("items.id"))
    item = db.relationship("Item", back_populates="item_reviews")

    rating = db.Column(db.Float)

    review_content = db.Column(db.String)

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    def __init__(self, **kwargs):
        super(ItemReview, self).__init__(**kwargs)

        self.created_at = datetime.now()
        self.modified_at = datetime.now()

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        self.modified_at = datetime.now()

        return self
