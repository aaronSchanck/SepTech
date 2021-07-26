"""/web/app/syzygy/search_filters/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging
from typing import List

from app import db
from libs.response import ErrResponse, NormalResponse

from .model import SearchFilter

log = logging.getLogger(__name__)


class SearchFilterService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return SearchFilter.query.all()

    @staticmethod
    def get_by_id(id: int) -> SearchFilter:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        return SearchFilter.query.get(id)

    @staticmethod
    def update(search_filter: SearchFilter, updates: dict) -> SearchFilter:
        """[summary]

        :param search_filter: [description]
        :type search_filter: SearchFilter
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: SearchFilter
        """

        search_filter.update(updates)
        db.session.commit()
        return search_filter

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a search_filter from the table with the specified id

        :param id: SearchFilter's id
        :type id: int
        :return: List containing the deleted search_filter, if found, otherwise an empty
        list
        :rtype: List
        """

        search_filter = SearchFilterService.get_by_id(id)
        if not search_filter:
            return []
        db.session.delete(search_filter)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> SearchFilter:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: SearchFilter
        """

        new_search_filter = SearchFilter()

        db.session.add(new_search_filter)
        db.session.commit()

        return new_search_filter

    @staticmethod
    def create_if_not_exists(new_attrs: dict) -> SearchFilter:
        pass

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass
