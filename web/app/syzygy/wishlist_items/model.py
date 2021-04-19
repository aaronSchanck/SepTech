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

log = logging.getLogger(__name__)


class WishlistItem(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "wishlist_items"

    id = db.Column(db.Integer, primary_key=True)

    itemid = db.Column(db.Integer, db.ForeignKey("items.id"))
    item = db.relationship("Item")

    wishlistid = db.Column(db.Integer, db.ForeignKey("wishlist.id"))
    wishlist = db.relationship("Wishlist", back_populates="wishlist_items")

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    def __init__(self, **kwargs):
        super(WishlistItem, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
