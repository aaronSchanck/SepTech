"""/web/app/syzygy/addresses/model.py

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


class Address(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "addresses"

    id = db.Column(db.Integer, primary_key=True)
    unit = db.Column(db.String(15))
    building = db.Column(db.String(15))
    street_name = db.Column(db.String(63))
    street_type = db.Column(db.String(15))
    city = db.Column(db.String(63))
    region = db.Column(db.String(15))
    country = db.Column(db.String(15))
    address_code = db.Column(db.String(15))
    postal_code = db.Column(db.String(15))

    # userid = db.Column(db.Integer, db.ForeignKey("users.id"))

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
