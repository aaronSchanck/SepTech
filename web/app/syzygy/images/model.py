"""/web/app/syzygy/images/model.py

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

from .interface import ItemInterface

log = logging.getLogger(__name__)


class ItemImage(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "item_images"

    itemid = Column(Integer, primary_key=True)
    # image_data = Column(bytearray)

    def update(self, changes: ItemInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
