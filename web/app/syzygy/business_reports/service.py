"""/web/app/syzygy/business_reports/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import BusinessReport
from .interface import BusinessReportInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)


class BusinessReportService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return BusinessReport.query.all()

    @staticmethod
    def get_by_id(id: int) -> BusinessReport:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        return BusinessReport.query.get(id)

    @staticmethod
    def update(
        business_report: BusinessReport,
        business_report_changes: BusinessReportInterface,
    ) -> BusinessReport:
        """[summary]

        :param business_report: The BusinessReport to update in the database
        :type business_report: BusinessReport
        :param BusinessReport_change_updates: Dictionary object containing the new changes
        to update the BusinessReport model object with
        :type BusinessReport_change_updates: BusinessReportInterface
        :return: The updated BusinessReport model object
        :rtype: BusinessReport
        """
        business_report.update(business_report_changes)
        db.session.commit()
        return business_report

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a business_report from the table with the specified id

        :param id: BusinessReport's id
        :type id: int
        :return: List containing the deleted business_report, if found, otherwise an empty
        list
        :rtype: List
        """

        business_report = BusinessReportService.get_by_id(id)
        if not business_report:
            return []
        db.session.delete(business_report)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: BusinessReportInterface) -> BusinessReport:
        """Creates a business_report object from the BusinessReportInterface TypedDict

        :param new_attrs: A dictionary with the input into a BusinessReport model
        :type new_attrs: BusinessReportInterface
        :return: A new business_report object based on the input
        :rtype: BusinessReport
        """

        new_business_report = BusinessReport()

        db.session.add(new_business_report)
        db.session.commit()

        return new_business_report


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
