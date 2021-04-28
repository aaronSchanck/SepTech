"""/web/app/syzygy/wishlist/service.py

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

from .model import Wishlist


log = logging.getLogger(__name__)


class WishlistService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Wishlist.query.all()

    @staticmethod
    def get_by_id(id: int) -> Wishlist:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        order = Wishlist.query.get(id)

        return order

    @staticmethod
    def update(order: Wishlist, updates: dict) -> Wishlist:
        """[summary]

        :param order: [description]
        :type order: Wishlist
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: Wishlist
        """

        order.update(updates)
        db.session.commit()
        return order

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a order from the table with the specified id

        :param id: Wishlist's id
        :type id: int
        :return: List containing the deleted order, if found, otherwise an empty
        list
        :rtype: List
        """

        order = WishlistService.get_by_id(id)
        if not order:
            return []
        db.session.delete(order)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> Wishlist:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: Wishlist
        """

        new_order = Wishlist(userid=new_attrs["userid"])

        db.session.add(new_order)
        db.session.commit()

        return new_order

    @staticmethod
    def get_user_active_wishlist(userid: int) -> Wishlist:
        return Wishlist.query.filter(Wishlist.userid == userid).first()

    def create_user_wishlist_if_not_exists(userid: int) -> Wishlist:
        wishlist = WishlistService.get_user_active_wishlist(userid)

        print(wishlist)

        if not wishlist:
            wishlist_data = {"userid": userid}

            wishlist = WishlistService.create(wishlist_data)
        return wishlist

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass