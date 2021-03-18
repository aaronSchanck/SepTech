"""/web/app/syzygy/items/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db
from sqlalchemy import Column, ForeignKey, String, Column, Integer
from sqlalchemy.orm import relationship

from .interface import ElectronicInterface

from ..model import Item

log = logging.getLogger(__name__)


class Electronic(Item):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "electronics"

    itemid = Column(Integer, ForeignKey('items.itemid'), primary_key=True)

    # items = relationship("Item", backref="electronic")


    __mapper_args__ = {
        'polymorphic_identity': 'electronic'
    }

    def update(self, changes: ElectronicInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
