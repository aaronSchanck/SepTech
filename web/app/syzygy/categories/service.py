"""/web/app/syzygy/categories/service.py

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

from .model import Category

log = logging.getLogger(__name__)


class CategoryService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Category.query.all()

    @staticmethod
    def get_by_id(id: int) -> Category:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        return Category.query.get(id)

    @staticmethod
    def update(category: Category, updates: dict) -> Category:
        """[summary]

        :param category: [description]
        :type category: Category
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: Category
        """

        category.update(updates)

        db.session.commit()
        return category

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a category from the table with the specified id

        :param id: Category's id
        :type id: int
        :return: List containing the deleted category, if found, otherwise an empty
        list
        :rtype: List
        """

        category = CategoryService.get_by_id(id)
        if not category:
            return []
        db.session.delete(category)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> Category:
        """Creates a category object from the CategoryInterface TypedDict

        :param new_attrs: A dictionary with the input into a Category model
        :type new_attrs: CategoryInterface
        :return: A new category object based on the input
        :rtype: Category
        """

        new_category = Category(
            category_1=new_attrs["category_1"],
            category_2=new_attrs["category_2"],
            category_3=new_attrs["category_3"],
            category_4=new_attrs["category_4"],
            category_5=new_attrs["category_5"],
        )

        db.session.add(new_category)
        db.session.commit()

        return new_category

    @staticmethod
    def create_if_not_exists(new_attrs: dict) -> Category:
        """Creates a category object from the CategoryInterface TypedDict

        :param new_attrs: A dictionary with the input into a Category model
        :type new_attrs: CategoryInterface
        :return: A new category object based on the input
        :rtype: Category
        """

        category = (
            Category.query.filter(Category.category_1 == new_attrs["category_1"])
            .filter(Category.category_2 == new_attrs["category_2"])
            .filter(Category.category_3 == new_attrs["category_3"])
            .filter(Category.category_4 == new_attrs["category_4"])
            .filter(Category.category_5 == new_attrs["category_5"])
            .first()
        )

        if category is None:
            category = CategoryService.create(new_attrs=new_attrs)

        return category
