"""/web/app/syzygy/item_reports/service.py

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

from .model import ItemReport

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
    def update(item_report: ItemReport, updates: dict) -> ItemReport:
        """[summary]

        :param item_report: [description]
        :type item_report: ItemReport
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: ItemReport
        """

        item_report.update(updates)
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
    def create(new_attrs: dict) -> ItemReport:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: ItemReport
        """

        new_item_report = ItemReport()

        db.session.add(new_item_report)
        db.session.commit()

        return new_item_report

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass
