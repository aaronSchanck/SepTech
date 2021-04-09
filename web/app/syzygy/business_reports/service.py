"""/web/app/syzygy/business_reports/service.py

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
from lib import ErrResponse, NormalResponse

from .interface import BusinessReportInterface
from .model import BusinessReport

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
