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
from sqlalchemy import *

from .interface import ItemInterface

log = logging.getLogger(__name__)


class Item(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "items"

    itemid = Column(Integer, primary_key=True)
    name = Column(String(127))
    discriminator = Column(String(63))
    quantity = Column(Integer)
    posted_at = Column(DateTime)
    seller = Column(Integer) # businessid
    price = Column(Numeric(10,2)))
    can_buy = Column(Boolean)
    can_bid = Column(Boolean)
    highest_bid = Column(Numeric(10,2))
    highest_bid_user = Column(Integer) #userid
    bidding_ends = Column(DateTime)
    quality = Column(String(63))
    item_variants = Column(ARRAY(Integer)) # array of itemid
    attributes = Column(JSON)



    __mapper_args__ = {"polymorphic_identity": "item", "polymorphic_on": discriminator}

    def update(self, changes: ItemInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
