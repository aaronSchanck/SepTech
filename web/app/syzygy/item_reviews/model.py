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

log = logging.getLogger(__name__)


class ItemReview(db.Model):
    __tablename__ = "item_reviews"

    id = db.Column(db.Integer, primary_key=True)

    def __init__(self, **kwargs):
        super(ItemReview, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self

    def __repr__(self):
        pass
