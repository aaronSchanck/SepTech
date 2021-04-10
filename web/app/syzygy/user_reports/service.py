"""/web/app/syzygy/user_reports/service.py

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

from .model import UserReport

log = logging.getLogger(__name__)


class UserReportService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return UserReport.query.all()

    @staticmethod
    def get_by_id(id: int) -> UserReport:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        return UserReport.query.get(id)

    @staticmethod
    def update(user_report: UserReport, updates: dict) -> UserReport:
        """Update a UserReport entity with new attributes.

        :param user_report: [description]
        :type user_report: UserReport
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: UserReport
        """

        user_report.update(updates)

        db.session.commit()
        return user_report

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a user_report from the table with the specified id

        :param id: UserReport's id
        :type id: int
        :return: List containing the deleted user_report, if found, otherwise an empty
        list
        :rtype: List
        """

        user_report = UserReportService.get_by_id(id)
        if not user_report:
            return []
        db.session.delete(user_report)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> UserReport:
        """Creates a user_report object from the validated marshmallow schema.

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: UserReport
        """

        new_user_report = UserReport()

        db.session.add(new_user_report)
        db.session.commit()

        return new_user_report