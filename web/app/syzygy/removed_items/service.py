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

from .interface import RemovedItemInterface
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

        # if removed_item is None:
        #     return ErrResponse("Requested removed_item doesn't exist", 400)

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
    def update(
        removed_item: RemovedItem, RemovedItem_change_updates: RemovedItemInterface
    ) -> RemovedItem:
        """[summary]

        :param removed_item: The RemovedItem to update in the database
        :type removed_item: RemovedItem
        :param RemovedItem_change_updates: Dictionary object containing the new changes
        to update the RemovedItem model object with
        :type RemovedItem_change_updates: RemovedItemInterface
        :return: The updated RemovedItem model object
        :rtype: RemovedItem
        """
        removed_item.update(RemovedItem_change_updates)
        removed_item.modified_at = datetime.now()

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
    def create(new_attrs: RemovedItemInterface) -> RemovedItem:
        """Creates a removed_item object from the RemovedItemInterface TypedDict

        :param new_attrs: A dictionary with the input into a RemovedItem model
        :type new_attrs: RemovedItemInterface
        :return: A new removed_item object based on the input
        :rtype: RemovedItem
        """

        new_removed_item = RemovedItem()

        db.session.add(new_removed_item)
        db.session.commit()

        return new_removed_item