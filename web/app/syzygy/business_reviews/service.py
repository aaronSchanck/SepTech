"""/web/app/syzygy/business_reviews/service.py

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
from lib import ErrResponse, NormalResponse
from libs.auth import encrypt_pw

from .interface import BusinessReviewInterface
from .model import BusinessReview

log = logging.getLogger(__name__)


class BusinessReviewService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return BusinessReview.query.all()

    @staticmethod
    def get_by_id(id: int) -> BusinessReview:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        business_review = BusinessReview.query.get(id)

        if business_review is None:
            return ErrResponse("Requested business_review doesn't exist", 400)

        return business_review

    @staticmethod
    def update(
        business_review: BusinessReview, updates: BusinessReviewInterface
    ) -> BusinessReview:
        """[summary]

        :param business_review: The BusinessReview to update in the database
        :type business_review: BusinessReview
        :param BusinessReview_change_updates: Dictionary object containing the new changes
        to update the BusinessReview model object with
        :type BusinessReview_change_updates: BusinessReviewInterface
        :return: The updated BusinessReview model object
        :rtype: BusinessReview
        """
        business_review.update(updates)
        db.session.commit()

        return business_review

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a business_review from the table with the specified id

        :param id: BusinessReview's id
        :type id: int
        :return: List containing the deleted business_review, if found, otherwise an empty
        list
        :rtype: List
        """

        business_review = BusinessReviewService.get_by_id(id)
        if not business_review:
            return []
        db.session.delete(business_review)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: BusinessReviewInterface) -> BusinessReview:
        """Creates a business_review object from the BusinessReviewInterface TypedDict

        :param new_attrs: A dictionary with the input into a BusinessReview model
        :type new_attrs: BusinessReviewInterface
        :return: A new business_review object based on the input
        :rtype: BusinessReview
        """

        new_business_review = BusinessReview()

        db.session.add(new_business_review)
        db.session.commit()

        return new_business_review
