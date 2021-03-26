"""/web/app/syzygy/orderdetails/model.py

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

from .interface import OrderDetailInterface

log = logging.getLogger(__name__)


class OrderDetail(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "orderdetails"

    def update(self, changes: OrderDetailInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
