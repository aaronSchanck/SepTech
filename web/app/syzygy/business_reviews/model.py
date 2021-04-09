"""/web/app/syzygy/business_reviews/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from .interface import BusinessReviewInterface

log = logging.getLogger(__name__)


class BusinessReview(db.Model):
    __tablename__ = "business_reviews"

    id = db.Column(db.Integer, primary_key=True)

    def update(self, changes: BusinessReviewInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self

    def __repr__(self):
        pass
