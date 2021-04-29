from pytest import fixture

from .model import ItemReview
from .schema import ItemReviewSchema


@fixture
def schema() -> ItemReviewSchema:
    return ItemReviewSchema()


def test_ItemReviewSchema_create(schema: ItemReviewSchema):
    assert schema


def test_ItemReviewSchema_works(schema: ItemReviewSchema):

    item_review = ItemReview(        
        id=3,
        userid=1,
        itemid=1,
        rating=4.00,
        review_content="test review test")

    assert item_review.id == 3
    assert item_review.userid == 1
    assert item_review.itemid == 1
    assert item_review.rating == 4.00
    assert item_review.review_content == "test review test"