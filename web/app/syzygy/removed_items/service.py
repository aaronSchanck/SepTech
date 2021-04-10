"""/web/app/syzygy/removed_items/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
from datetime import datetime
from typing import List

from app.globals import *
import bcrypt
from app import db
from libs.response import ErrResponse, NormalResponse

from .model import RemovedItem

log = logging.getLogger(__name__)


class RemovedItemService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return RemovedItem.query.all()

    @staticmethod
    def get_by_id(id: int) -> RemovedItem:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        removed_item = RemovedItem.query.get(id)

        return removed_item

    @staticmethod
    def get_by_email(email: str) -> RemovedItem:
        """[summary]

        :param email: [description]
        :type email: str
        :return: [description]
        :rtype: [type]
        """

        removed_item = RemovedItem.query.filter(RemovedItem.email == email).first()

        return removed_item

    @staticmethod
    def update(removed_item: RemovedItem, updates: dict) -> RemovedItem:
        """[summary]

        :param removed_item: [description]
        :type removed_item: RemovedItem
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: RemovedItem
        """

        removed_item.update(updates)

        db.session.commit()
        return removed_item

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a removed_item from the table with the specified id

        :param id: RemovedItem's id
        :type id: int
        :return: List containing the deleted removed_item, if found, otherwise an empty
        list
        :rtype: List
        """

        removed_item = RemovedItemService.get_by_id(id)
        if not removed_item:
            return []
        db.session.delete(removed_item)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> RemovedItem:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: RemovedItem
        """

        new_removed_item = RemovedItem()

        db.session.add(new_removed_item)
        db.session.commit()

        return new_removed_item