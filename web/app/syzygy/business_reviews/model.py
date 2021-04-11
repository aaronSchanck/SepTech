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

log = logging.getLogger(__name__)


class BusinessReview(db.Model):
    __tablename__ = "business_reviews"

    id = db.Column(db.Integer, primary_key=True)

    businessid = db.Column(db.Integer, db.ForeignKey("businesses.id"))
    business = db.relationship("Business")

    reviewerid = db.Column(db.Integer, db.ForeignKey("users.id"))
    reviewer = db.relationship("User")

    created_at = db.Column(db.DateTime())

    rating = db.Column(db.Integer)

    comment = db.Column(db.String)

    def __init__(self, **kwargs):
        super(BusinessReview, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self

    def __repr__(self):
        pass
