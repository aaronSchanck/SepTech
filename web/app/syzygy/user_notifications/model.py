"""/web/app/syzygy/users/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging
from datetime import datetime

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

    viewed = db.Column(db.Boolean)
    viewed_at = db.Column(db.DateTime)

    content = db.Column(db.String)

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    def __init__(self, **kwargs):
        super(UserNotification, self).__init__(**kwargs)

        created_at = datetime.now()
        modified_at = datetime.now()

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
