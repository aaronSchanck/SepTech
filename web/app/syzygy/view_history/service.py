"""/web/app/syzygy/view_history/service.py

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
from typing import List

from datetime import datetime

from app import db
from libs.response import ErrResponse, NormalResponse

from .model import ViewHistory


log = logging.getLogger(__name__)


class ViewHistoryService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return ViewHistory.query.all()

    @staticmethod
    def get_by_id(id: int) -> ViewHistory:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        order = ViewHistory.query.get(id)

        return order

    @staticmethod
    def update(order: ViewHistory, updates: dict) -> ViewHistory:
        """[summary]

        :param order: [description]
        :type order: ViewHistory
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: ViewHistory
        """

        order.update(updates)
        db.session.commit()
        return order

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a order from the table with the specified id

        :param id: View History's id
        :type id: int
        :return: List containing the deleted order, if found, otherwise an empty
        list
        :rtype: List
        """

        order = ViewHistoryService.get_by_id(id)
        if not order:
            return []
        db.session.delete(order)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> ViewHistory:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: ViewHistory
        """

        new_order = ViewHistory(userid=new_attrs["userid"])

        db.session.add(new_order)
        db.session.commit()

        return new_order

    @staticmethod
    def get_user_active_view_history(userid: int) -> ViewHistory:
        return ViewHistory.query.filter(ViewHistory.userid == userid).first()

    def create_user_view_history_if_not_exists(userid: int) -> ViewHistory:
        view_history = ViewHistoryService.get_user_active_view_history(userid)

        print(view_history)

        if not view_history:
            view_history_data = {"userid": userid}

            view_history = ViewHistoryService.create(view_history_data)
        return view_history

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass