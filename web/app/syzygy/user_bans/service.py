"""/web/app/syzygy/user_bans/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
from typing import List

from app import db
from libs.response import ErrResponse, NormalResponse

from .model import UserBan

log = logging.getLogger(__name__)


class UserBanService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return UserBan.query.all()

    @staticmethod
    def get_by_id(id: int) -> UserBan:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        user_ban = UserBan.query.get(id)

        return user_ban

    @staticmethod
    def update(user_ban: UserBan, updates: dict) -> UserBan:
        """Update a specified user_ban entity with the updates dict

        :param user_ban: [description]
        :type user_ban: UserBan
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: UserBan
        """
        user_ban.update(updates)
        user_ban.modified_at = datetime.now()

        db.session.commit()
        return user_ban

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a user_ban from the table with the specified id

        :param id: UserBan's id
        :type id: int
        :return: List containing the deleted user_ban, if found, otherwise an empty
        list
        :rtype: List
        """

        user_ban = UserBanService.get_by_id(id)
        if not user_ban:
            return []
        db.session.delete(user_ban)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> UserBan:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: UserBan
        """

        new_user_ban = UserBan()

        db.session.add(new_user_ban)
        db.session.commit()

        return new_user_ban

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass