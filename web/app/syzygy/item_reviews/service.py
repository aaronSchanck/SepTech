"""/web/app/syzygy/item_reviews/service.py

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
from libs.auth import encrypt_pw

from .model import ItemReview

log = logging.getLogger(__name__)


class ItemReviewService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return ItemReview.query.all()

    @staticmethod
    def get_by_id(id: int) -> ItemReview:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        item_review = ItemReview.query.get(id)

        if item_review is None:
            return ErrResponse("Requested item_review doesn't exist", 400)

        return item_review

    @staticmethod
    def update(item_review: ItemReview, updates: dict) -> ItemReview:
        """[summary]

        :param item_review: [description]
        :type item_review: ItemReview
        :param updates: [description]
        :type updates: dict
        :return: [description]
        :rtype: ItemReview
        """

        item_review.update(updates)
        db.session.commit()

        return item_review

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a item_review from the table with the specified id

        :param id: ItemReview's id
        :type id: int
        :return: List containing the deleted item_review, if found, otherwise an empty
        list
        :rtype: List
        """

        item_review = ItemReviewService.get_by_id(id)
        if not item_review:
            return []
        db.session.delete(item_review)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: dict) -> ItemReview:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: dict
        :return: [description]
        :rtype: ItemReview
        """

        new_item_review = ItemReview()

        db.session.add(new_item_review)
        db.session.commit()

        return new_item_review

    @staticmethod
    def transform(attrs: dict) -> dict:
        """Transforms the dict input for the object. Puts the information in a form that the model can use.

        :param attrs: [description]
        :type attrs: dict
        :return: [description]
        :rtype: dict
        """

        pass
