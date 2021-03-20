"""/web/app/syzygy/address/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db
from sqlalchemy import *

from .interface import AddressInterface

log = logging.getLogger(__name__)


class Address(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "address"

    unit = Column(String(15))
    building = Column(String(15))
    street_name = Column(String(63))
    street_type = Column(String(15))
    city = Column(String(63))
    region = Column(String(15))
    country = Column(String(15))
    address_code = Column(String(15))
    postal_code = Column(String(15))

    def update(self, changes: AddressInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
