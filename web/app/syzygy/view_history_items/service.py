"""/web/app/syzygy/view_history_items/service.py

Author: Adam Green (adam.green1@maine.edu)
        Ashley Drexler (ashley.drexler@maine.edu)


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

from app import db
from flask import Response
from libs.response import ErrResponse, NormalResponse

from .model import ViewHistoryItem
from ..view_history.model import ViewHistory
from ..items.service import ItemService

log = logging.getLogger(__name__)


class ViewHistoryItemService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return ViewHistoryItem.query.all()

    @staticmethod
    def get_by_id(id: int) -> ViewHistoryItem:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        view_history_item = ViewHistoryItem.query.get(id)

        return view_history_item

    @staticmethod
    def update(view_history_item: ViewHistoryItem, updates: dict) -> ViewHistoryItem:
        """[summary]

        :param view_history_item: [description]
        :type view_history_item: ViewHistoryItem
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: ViewHistoryItem
        """

        view_history_item.update(updates)
        db.session.commit()
        return view_history_item

    @staticmethod
    def delete_by_id(id: int) -> ViewHistoryItem:
        """Deletes a view_history_item from the table with the specified id

        :param id: ViewHistoryItem's id
        :type id: int
        :return: List containing the deleted view_history_item, if found, otherwise an empty
        list
        :rtype: List
        """

        view_history_item = ViewHistoryItemService.get_by_id(id)
        if not view_history_item:
            return None
        db.session.delete(view_history_item)
        db.session.commit()
        return view_history_item

    @staticmethod
    def create(new_attrs: dict) -> ViewHistoryItem:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: ViewHistoryItem
        """

        new_view_history_item = ViewHistoryItem(
            itemid=new_attrs["itemid"],
            view_history_id=new_attrs["viewhistoryid"],
        )

        db.session.add(new_view_history_item)
        db.session.commit()

        return new_view_history_item

    @staticmethod
    def view_history_item_from_item(
        view_history_id: int, itemid: int
    ) -> (ViewHistoryItem, Response):
        view_history_item = ViewHistoryItemService.check_item_exists(view_history_id, itemid)

        print(view_history_item)

        if view_history_item is not None:
            return None, ErrResponse("Item exists in view history", 400)

        item = ItemService.get_by_id(itemid)

        print(item)

        view_history_item_data = {
            "itemid": itemid,
            "viewhistoryid": view_history_id,
        }

        view_history = ViewHistoryItemService.create(view_history_item_data)

        return view_history, NormalResponse("Success", 200)

    @staticmethod
    def check_item_exists(view_history_id: int, itemid: int):
        view_history_item = (
            ViewHistoryItem.query.filter(ViewHistoryItem.view_history_id == view_history_id)
            .filter(ViewHistoryItem.itemid == itemid)
            .first()
        )

        return view_history_item

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass
