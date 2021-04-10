"""/web/app/syzygy/user_bans/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from .interface import UserBanInterface

log = logging.getLogger(__name__)


class UserBan(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "user_bans"

    id = db.Column(db.Integer, primary_key=True)

    userid = db.Column(db.Integer, db.ForeignKey("users.id"))
    user = db.relationship("User")

    ban_start_date = db.Column(db.DateTime())
    ban_end_date = db.Column(db.DateTime())

    ban_reason = db.Column(db.String)

    def update(self, changes: UserBanInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
