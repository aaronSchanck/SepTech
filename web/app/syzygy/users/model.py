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

from .interface import UserInterface

log = logging.getLogger(__name__)


class User(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "users"

    id = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(255), unique=True, nullable=False)
    password = db.Column(db.LargeBinary(127), nullable=False)
    full_name = db.Column(db.String(255))
    date_of_birth = db.Column(db.Date)
    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    billing_addressid = db.Column(db.Integer, db.ForeignKey("addresses.id"))
    billing_address = db.relationship(
        "Address",
        foreign_keys=billing_addressid,
        backref="user_billaddr",
        uselist=False,
    )

    mailing_addressid = db.Column(db.Integer, db.ForeignKey("addresses.id"))
    mailing_address = db.relationship(
        "Address",
        foreign_keys=mailing_addressid,
        backref="user_mailaddr",
        uselist=False,
    )

    phone_number = db.Column(db.String(11))
    password_salt1 = db.Column(db.String(63))
    password_reset_code = db.Column(db.String(6))
    password_reset_timeout = db.Column(db.DateTime)
    last_successful_login = db.Column(db.DateTime)
    last_unsuccessful_login = db.Column(db.DateTime)

    # order
    orders = db.relationship("Order", backref="user")

    admin_level = db.Column(db.Integer)

    def update(self, changes: UserInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
