"""/web/app/syzygy/business_notifications/service.py

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

from .model import BusinessNotification

log = logging.getLogger(__name__)


class BusinessNotificationService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return BusinessNotification.query.all()

    @staticmethod
    def get_by_id(id: int) -> BusinessNotification:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        return BusinessNotification.query.get(id)

    @staticmethod
    def update(
        business_report: BusinessNotification,
        updates: dict,
    ) -> BusinessNotification:
        """[summary]

        :param business_report: [description]
        :type business_report: BusinessNotification
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: BusinessNotification
        """

        business_report.update(updates)
        db.session.commit()
        return business_report

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a business_report from the table with the specified id

        :param id: BusinessNotification's id
        :type id: int
        :return: List containing the deleted business_report, if found, otherwise an empty
        list
        :rtype: List
        """

        business_report = BusinessNotificationService.get_by_id(id)
        if not business_report:
            return []
        db.session.delete(business_report)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> BusinessNotification:
        """Creates a business_report object from the dict TypedDict

        :param new_attrs: A dictionary with the input into a BusinessNotification model
        :type new_attrs: dict
        :return: A new business_report object based on the input
        :rtype: BusinessNotification
        """

        new_business_report = BusinessNotification()

        db.session.add(new_business_report)
        db.session.commit()

        return new_business_report

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass
