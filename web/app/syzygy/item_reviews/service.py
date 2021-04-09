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

from .interface import ItemReviewInterface
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
    def update(item_review: ItemReview, updates: ItemReviewInterface) -> ItemReview:
        """[summary]

        :param item_review: The ItemReview to update in the database
        :type item_review: ItemReview
        :param ItemReview_change_updates: Dictionary object containing the new changes
        to update the ItemReview model object with
        :type ItemReview_change_updates: ItemReviewInterface
        :return: The updated ItemReview model object
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
    def create(new_attrs: ItemReviewInterface) -> ItemReview:
        """Creates a item_review object from the ItemReviewInterface TypedDict

        :param new_attrs: A dictionary with the input into a ItemReview model
        :type new_attrs: ItemReviewInterface
        :return: A new item_review object based on the input
        :rtype: ItemReview
        """

        new_item_review = ItemReview()

        db.session.add(new_item_review)
        db.session.commit()

        return new_item_review
