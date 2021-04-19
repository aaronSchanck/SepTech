"""/web/app/syzygy/notifications/service.py

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

from .model import Notification


log = logging.getLogger(__name__)


class NotificationService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Notification.query.all()

    @staticmethod
    def get_by_id(id: int) -> Notification:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        notification = Notification.query.get(id)

        return notification

    @staticmethod
    def update(notification: Notification, updates: dict) -> Notification:
        """[summary]

        :param notification: [description]
        :type notification: Notification
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: Notification
        """

        notification.update(updates)
        db.session.commit()
        return notification

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a notification from the table with the specified id

        :param id: Notification's id
        :type id: int
        :return: List containing the deleted notification, if found, otherwise an empty
        list
        :rtype: List
        """

        notification = NotificationService.get_by_id(id)
        if not notification:
            return []
        db.session.delete(notification)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> Notification:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: Notification
        """

        new_notification = Notification()

        db.session.add(new_notification)
        db.session.commit()

        return new_notification

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass