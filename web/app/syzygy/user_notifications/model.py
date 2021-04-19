"""/web/app/syzygy/users/model.py

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


class UserNotification(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "user_notifications"

    id = db.Column(db.Integer, primary_key=True)

    userid = db.Column(db.Integer, db.ForeignKey("users.id"))
    user = db.relationship("User", back_populates="user_notifications")

    def __init__(self, **kwargs):
        super(UserNotification, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
