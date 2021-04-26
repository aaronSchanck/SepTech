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

from datetime import datetime

log = logging.getLogger(__name__)


class User(db.Model):
    """A User entity in the users table"""

    __tablename__ = "users"

    id = db.Column(db.Integer, primary_key=True)

    username = db.Column(db.String)
    email = db.Column(db.String(255), unique=True, nullable=False)
    password = db.Column(db.LargeBinary(127), nullable=False)
    full_name = db.Column(db.String(255))
    date_of_birth = db.Column(db.Date)

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    # billing address information
    # billing_addressid = db.Column(db.Integer, db.ForeignKey("addresses.id"))
    # billing_address = db.relationship(
    #     "Address",
    #     foreign_keys=billing_addressid,
    #     backref="user_billaddr",
    #     uselist=False,
    # )

    # # # mailing address information
    # mailing_addressid = db.Column(db.Integer, db.ForeignKey("addresses.id"))
    # mailing_address = db.relationship(
    #     "Address",
    #     foreign_keys=mailing_addressid,
    #     backref="user_mailaddr",
    #     uselist=False,
    # )

    # ban information
    banned = db.Column(db.Boolean)
    ban_end = db.Column(db.DateTime)
    # past_bans = db.relationship("UserBan")

    phone_number = db.Column(db.String(11))
    password_salt = db.Column(db.String(63))

    password_reset_code = db.Column(db.String(6))
    password_reset_timeout = db.Column(db.DateTime)

    # security data regarding login attempts
    last_successful_login = db.Column(db.DateTime)
    last_unsuccessful_login = db.Column(db.DateTime)

    # user orders
    orders = db.relationship("Order", back_populates="user")

    # bids
    bids = db.relationship("Bid", back_populates="user")

    # wishlist
    wishlist = db.relationship("Wishlist", back_populates="user", uselist=False)

    # reviews
    item_reviews = db.relationship("ItemReview", back_populates="user")

    # settings

    admin_level = db.Column(db.Integer)

    def __init__(self, **kwargs):
        super(User, self).__init__(**kwargs)

        self.created_at = datetime.now()
        self.modified_at = datetime.now()

        self.admin_level = 0

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        self.modified_at = datetime.now()

        return self

    def __eq__(self, other):
        return isinstance(other, User) and self.id == other.id