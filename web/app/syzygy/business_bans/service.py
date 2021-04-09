"""/web/app/syzygy/business_bans/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
from datetime import datetime
from typing import List

import bcrypt
from app import db
from app.globals import *
from libs.response import ErrResponse, NormalResponse

from .interface import BusinessBanInterface
from .model import BusinessBan

log = logging.getLogger(__name__)


class BusinessBanService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return BusinessBan.query.all()

    @staticmethod
    def get_by_id(id: int) -> BusinessBan:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        business_ban = BusinessBan.query.get(id)

        return business_ban

    @staticmethod
    def update(
        business_ban: BusinessBan,
        updates: BusinessBanInterface,
    ) -> BusinessBan:
        """[summary]

        :param business_ban: The BusinessBan to update in the database
        :type business_ban: BusinessBan
        :param BusinessBan_change_updates: Dictionary object containing the new changes
        to update the BusinessBan model object with
        :type BusinessBan_change_updates: BusinessBanInterface
        :return: The updated BusinessBan model object
        :rtype: BusinessBan
        """
        business_ban.update(BusinessBan_change_updates)
        business_ban.modified_at = datetime.now()

        db.session.commit()
        return business_ban

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a business_ban from the table with the specified id

        :param id: BusinessBan's id
        :type id: int
        :return: List containing the deleted business_ban, if found, otherwise an empty
        list
        :rtype: List
        """

        business_ban = BusinessBanService.get_by_id(id)
        if not business_ban:
            return []
        db.session.delete(business_ban)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: BusinessBanInterface) -> BusinessBan:
        """Creates a business_ban object from the BusinessBanInterface TypedDict

        :param new_attrs: A dictionary with the input into a BusinessBan model
        :type new_attrs: BusinessBanInterface
        :return: A new business_ban object based on the input
        :rtype: BusinessBan
        """

        new_business_ban = BusinessBan()

        db.session.add(new_business_ban)
        db.session.commit()

        return new_business_ban
