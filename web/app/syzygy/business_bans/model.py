"""/web/app/syzygy/business_bans/model.py

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


class BusinessBan(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "business_bans"

    id = db.Column(db.Integer, primary_key=True)

    businessid = db.Column(db.Integer, db.ForeignKey("businesses.id"))
    business = db.relationship("Business")

    ban_start_date = db.Column(db.DateTime())
    ban_end_date = db.Column(db.DateTime())

    ban_reason = db.Column(db.String)

    def __init__(self, **kwargs):
        super(BusinessBan, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
