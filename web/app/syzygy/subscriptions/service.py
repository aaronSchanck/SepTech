"""/web/app/syzygy/subscriptions/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
import re
from typing import List

from app import db
from libs.response import ErrResponse, NormalResponse
from utils.auth import encrypt_pw

from .model import Subscription

log = logging.getLogger(__name__)


class SubscriptionService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Subscription.query.all()

    @staticmethod
    def get_by_id(id: int) -> Subscription:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        subscription = Subscription.query.get(id)

        return subscription

    @staticmethod
    def update(subscription: Subscription, updates: dict) -> Subscription:
        """[summary]

        :param subscription: [description]
        :type subscription: Subscription
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: Subscription
        """
        subscription.update(updates)

        db.session.commit()
        return subscription

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a subscription from the table with the specified id

        :param id: Subscription's id
        :type id: int
        :return: List containing the deleted subscription, if found, otherwise an empty
        list
        :rtype: List
        """

        subscription = SubscriptionService.get_by_id(id)
        if not subscription:
            return []
        db.session.delete(subscription)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> Subscription:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: Subscription
        """

        new_subscription = Subscription()

        db.session.add(new_subscription)
        db.session.commit()

        return new_subscription
