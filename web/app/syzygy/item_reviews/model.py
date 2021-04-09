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

from .interface import ItemReviewInterface

log = logging.getLogger(__name__)


class ItemReview(db.Model):
    __tablename__ = "item_reviews"

    id = db.Column(db.Integer, primary_key=True)

    def update(self, changes: ItemReviewInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self

    def __repr__(self):
        pass
