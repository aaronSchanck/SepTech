"""/web/app/syzygy/businesses/model.py

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


class Business(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "businesses"

    id = db.Column(db.Integer, primary_key=True)
    business_name = db.Column(db.String, nullable=False)
    email = db.Column(db.String(255), unique=True, nullable=False)
    password = db.Column(db.LargeBinary(127), nullable=False)
    owner_full_name = db.Column(db.String(255))
    phone_number = db.Column(db.String(11))

    password_salt = db.Column(db.String(63))

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    password_reset_code = db.Column(db.String(6))
    password_reset_timeout = db.Column(db.DateTime)

    last_successful_login = db.Column(db.DateTime)
    last_unsuccessful_login = db.Column(db.DateTime)

    description = db.Column(db.String)

    profile_picture = db.Column(db.String)

    def __init__(self, **kwargs):
        super(Business, self).__init__(**kwargs)

        self.created_at = datetime.now()
        self.modified_at = datetime.now()

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        self.modified_at = datetime.now()

        return self

    def __repr__(self):
        pass
