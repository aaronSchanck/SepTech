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

from .interface import UserBanInterface
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
    def update(user_ban: UserBan, UserBan_change_updates: UserBanInterface) -> UserBan:
        """[summary]

        :param user_ban: The UserBan to update in the database
        :type user_ban: UserBan
        :param UserBan_change_updates: Dictionary object containing the new changes
        to update the UserBan model object with
        :type UserBan_change_updates: UserBanInterface
        :return: The updated UserBan model object
        :rtype: UserBan
        """
        user_ban.update(UserBan_change_updates)
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
    def create(new_attrs: UserBanInterface) -> UserBan:
        """Creates a user_ban object from the UserBanInterface TypedDict

        :param new_attrs: A dictionary with the input into a UserBan model
        :type new_attrs: UserBanInterface
        :return: A new user_ban object based on the input
        :rtype: UserBan
        """

        new_user_ban = UserBan()

        db.session.add(new_user_ban)
        db.session.commit()

        return new_user_ban
