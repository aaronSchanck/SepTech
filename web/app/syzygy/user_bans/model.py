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

log = logging.getLogger(__name__)


class UserBan(db.Model):
    """UserBan model for the user_bans table"""

    __tablename__ = "user_bans"

    id = db.Column(db.Integer, primary_key=True)

    userid = db.Column(db.Integer, db.ForeignKey("users.id"))
    user = db.relationship("User")

    ban_start_date = db.Column(db.DateTime())
    ban_end_date = db.Column(db.DateTime())

    ban_reason = db.Column(db.String)

    def __init__(self, **kwargs):
        super(UserBan, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
