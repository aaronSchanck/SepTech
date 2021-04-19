"""/web/app/syzygy/wishlist_items/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
from typing import List

from datetime import datetime

from app import db
from libs.response import ErrResponse, NormalResponse

from .model import WishlistItem


log = logging.getLogger(__name__)


class WishlistItemService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return WishlistItem.query.all()

    @staticmethod
    def get_by_id(id: int) -> WishlistItem:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        wishlist_item = WishlistItem.query.get(id)

        return wishlist_item

    @staticmethod
    def update(wishlist_item: WishlistItem, updates: dict) -> WishlistItem:
        """[summary]

        :param wishlist_item: [description]
        :type wishlist_item: WishlistItem
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: WishlistItem
        """

        wishlist_item.update(updates)
        db.session.commit()
        return wishlist_item

    @staticmethod
    def delete_by_id(id: int) -> WishlistItem:
        """Deletes a wishlist_item from the table with the specified id

        :param id: WishlistItem's id
        :type id: int
        :return: List containing the deleted wishlist_item, if found, otherwise an empty
        list
        :rtype: List
        """

        wishlist_item = WishlistItemService.get_by_id(id)
        if not wishlist_item:
            return None
        db.session.delete(wishlist_item)
        db.session.commit()
        return wishlist_item

    @staticmethod
    def create(new_attrs: dict) -> WishlistItem:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: WishlistItem
        """

        new_wishlist_item = WishlistItem()

        db.session.add(new_wishlist_item)
        db.session.commit()

        return new_wishlist_item

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass