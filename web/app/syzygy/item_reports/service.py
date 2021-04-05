"""/web/app/syzygy/item_reports/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import ItemReport
from .interface import ItemReportInterface
from flask import Response
import json
import logging

import re

from typing import List

log = logging.getLogger(__name__)


class ItemReportService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return ItemReport.query.all()

    @staticmethod
    def get_by_id(id: int) -> ItemReport:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        return ItemReport.query.get(id)

    @staticmethod
    def update(
        item_report: ItemReport, item_report_changes: ItemReportInterface
    ) -> ItemReport:
        """[summary]

        :param item_report: The ItemReport to update in the database
        :type item_report: ItemReport
        :param ItemReport_change_updates: Dictionary object containing the new changes
        to update the ItemReport model object with
        :type ItemReport_change_updates: ItemReportInterface
        :return: The updated ItemReport model object
        :rtype: ItemReport
        """
        item_report.update(item_report_changes)
        db.session.commit()
        return item_report

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a item_report from the table with the specified id

        :param id: ItemReport's id
        :type id: int
        :return: List containing the deleted item_report, if found, otherwise an empty
        list
        :rtype: List
        """

        item_report = ItemReportService.get_by_id(id)
        if not item_report:
            return []
        db.session.delete(item_report)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: ItemReportInterface) -> ItemReport:
        """Creates a item_report object from the ItemReportInterface TypedDict

        :param new_attrs: A dictionary with the input into a ItemReport model
        :type new_attrs: ItemReportInterface
        :return: A new item_report object based on the input
        :rtype: ItemReport
        """

        new_item_report = ItemReport()

        db.session.add(new_item_report)
        db.session.commit()

        return new_item_report


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
