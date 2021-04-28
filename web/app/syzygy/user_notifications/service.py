"""/web/app/syzygy/user_notifications/service.py

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

from .model import UserNotification


log = logging.getLogger(__name__)


class UserNotificationService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return UserNotification.query.all()

    @staticmethod
    def get_by_id(id: int) -> UserNotification:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        user_notification = UserNotification.query.get(id)

        return user_notification

    @staticmethod
    def update(user_notification: UserNotification, updates: dict) -> UserNotification:
        """[summary]

        :param user_notification: [description]
        :type user_notification: UserNotification
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: UserNotification
        """

        user_notification.update(updates)
        db.session.commit()
        return user_notification

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a user_notification from the table with the specified id

        :param id: UserNotification's id
        :type id: int
        :return: List containing the deleted user_notification, if found, otherwise an empty
        list
        :rtype: List
        """

        user_notification = UserNotificationService.get_by_id(id)
        if not user_notification:
            return []
        db.session.delete(user_notification)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> UserNotification:
        """Create a UserNotification entity with given attributes.

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: UserNotification
        """

        new_user_notification = UserNotification(
            content=new_attrs["content"],
            viewed=new_attrs["viewed"],
        )

        db.session.add(new_user_notification)
        db.session.commit()

        return new_user_notification

    @staticmethod
    def create_user_notification(content: str):
        notification_data = {
            "content": content,
            "viewed": False,
        }

        return UserNotificationService.create(notification_data)

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass