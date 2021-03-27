"""/web/app/syzygy/items/categories/model.py

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

from .interface import CategoryInterface

log = logging.getLogger(__name__)


class Category(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "categories"

    id = Column(Integer, primary_key=true)
    category_1 = Column(String(32))
    category_2 = Column(String(32))
    category_3 = Column(String(32))
    category_4 = Column(String(32))
    category_5 = Column(String(32))

    items = db.relationship("Item", backref="category")

    # items = db.relationship("Item", backref="category")

    def update(self, changes: CategoryInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
