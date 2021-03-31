"""/web/app/syzygy/items/categories/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Category
from .interface import CategoryInterface
from flask import Response
import json
import logging

import re

from typing import List

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
    def get_by_id(itemid: int) -> Category:
        """[summary]

        :param itemid: [description]
        :type itemid: int
        :return: [description]
        :rtype: [type]
        """
        return Category.query.get(itemid)

    @staticmethod
    def update(item: Category, Category_change_updates: CategoryInterface) -> Category:
        """[summary]

        :param item: The Category to update in the database
        :type item: Category
        :param Category_change_updates: Dictionary object containing the new changes
        to update the Category model object with
        :type Category_change_updates: CategoryInterface
        :return: The updated Category model object
        :rtype: Category
        """
        item.update(Category_change_updates)
        db.session.commit()
        return item

    @staticmethod
    def delete_by_id(category_id: int) -> List:
        """Deletes a item from the table with the specified itemid

        :param itemid: Category's itemid
        :type itemid: int
        :return: List containing the deleted item, if found, otherwise an empty
        list
        :rtype: List
        """

        category = CategoryService.get_by_id(category_id)
        if not item:
            return []
        db.session.delete(category)
        db.session.commit()
        return [itemid]

    @staticmethod
    def create(new_attrs: CategoryInterface) -> Category:
        """Creates a item object from the CategoryInterface TypedDict

        :param new_attrs: A dictionary with the input into a Category model
        :type new_attrs: CategoryInterface
        :return: A new item object based on the input
        :rtype: Category
        """

        new_item = Category(
            category_1=new_attrs["category_1"],
            category_2=new_attrs["category_2"],
            category_3=new_attrs["category_3"],
            category_4=new_attrs["category_4"],
            category_5=new_attrs["category_5"],
        )

        db.session.add(new_item)
        db.session.commit()

        return new_item

    @staticmethod
    def create_if_not_exists(new_attrs: CategoryInterface) -> Category:
        """Creates a item object from the CategoryInterface TypedDict

        :param new_attrs: A dictionary with the input into a Category model
        :type new_attrs: CategoryInterface
        :return: A new item object based on the input
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
