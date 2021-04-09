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

from .interface import SubscriptionInterface
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

        # if subscription is None:
        #     return ErrResponse("Requested subscription doesn't exist", 400)

        return subscription

    @staticmethod
    def update(
        subscription: Subscription, Subscription_change_updates: SubscriptionInterface
    ) -> Subscription:
        """[summary]

        :param subscription: The Subscription to update in the database
        :type subscription: Subscription
        :param Subscription_change_updates: Dictionary object containing the new changes
        to update the Subscription model object with
        :type Subscription_change_updates: SubscriptionInterface
        :return: The updated Subscription model object
        :rtype: Subscription
        """
        subscription.update(Subscription_change_updates)
        subscription.modified_at = datetime.now()

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
    def create(new_attrs: SubscriptionInterface) -> Subscription:
        """Creates a subscription object from the SubscriptionInterface TypedDict

        :param new_attrs: A dictionary with the input into a Subscription model
        :type new_attrs: SubscriptionInterface
        :return: A new subscription object based on the input
        :rtype: Subscription
        """

        new_subscription = Subscription()

        db.session.add(new_subscription)
        db.session.commit()

        return new_subscription
