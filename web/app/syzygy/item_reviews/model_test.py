from pytest import fixture
from flask_sqlalchemy import SQLAlchemy
from app.test.fixtures import app, db  # noqa
from .model import ItemReview


@fixture
def item_review() -> ItemReview:
    return ItemReview(
        id=3,
        userid=1,
        itemid=1,
        rating=4.00,
        review_content="test review test"
    )


def test_ItemReview_create(item_review: ItemReview):
    assert item_review


def test_ItemReview_retrieve(item_review: ItemReview, db: SQLAlchemy):  # noqa
    db.session.add(item_review)
    db.session.commit()
    s = ItemReview.query.first()
    assert s.__dict__ == item_review.__dict__
