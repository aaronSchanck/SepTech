"""/web/app/syzygy/user_reports/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import UserReport
from .interface import UserReportInterface
from flask import Response
import json
import logging

import re

from typing import List

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
    def update(
        user_report: UserReport, user_report_changes: UserReportInterface
    ) -> UserReport:
        """[summary]

        :param user_report: The UserReport to update in the database
        :type user_report: UserReport
        :param UserReport_change_updates: Dictionary object containing the new changes
        to update the UserReport model object with
        :type UserReport_change_updates: UserReportInterface
        :return: The updated UserReport model object
        :rtype: UserReport
        """
        user_report.update(user_report_changes)
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
    def create(new_attrs: UserReportInterface) -> UserReport:
        """Creates a user_report object from the UserReportInterface TypedDict

        :param new_attrs: A dictionary with the input into a UserReport model
        :type new_attrs: UserReportInterface
        :return: A new user_report object based on the input
        :rtype: UserReport
        """

        new_user_report = UserReport()

        db.session.add(new_user_report)
        db.session.commit()

        return new_user_report


def NormalResponse(response: dict, status: int) -> Response:
    """Function to return a normal response (200-299)

    :param response: Dictionary object with the content to be sent in the response
    :type response: dict
    :param status: Status code along with the response
    :type status: int
    :return: Response object with related response and status code
    :rtype: Response
    """

    return Response(
        mimetype="application/json", response=json.dumps(response), status=status
    )


def ErrResponse(response: str, status: int) -> Response:
    """Helper function to create an error response (400-499)

    :param response: String specifying the error message to send
    :type response: str
    :param status: Status code along with the response
    :type status: int
    :return: Response object with related response and status code
    :rtype: Response
    """

    return Response(
        mimetype="application/json",
        response=json.dumps({"error": response}),
        status=status,
    )
