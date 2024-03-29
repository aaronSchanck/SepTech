"""/web/app/syzygy/categories/model.py

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


class Category(db.Model):
    """SQLAlchemy Model for a Category entry in the database.

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "categories"

    id = db.Column(db.Integer, primary_key=True)
    category_1 = db.Column(db.String(32))
    category_2 = db.Column(db.String(32))
    category_3 = db.Column(db.String(32))
    category_4 = db.Column(db.String(32))
    category_5 = db.Column(db.String(32))

    items = db.relationship("Item", backref="category")

    def __init__(self, **kwargs):
        super(Category, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
