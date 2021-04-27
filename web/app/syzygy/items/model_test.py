from pytest import fixture
from flask_sqlalchemy import SQLAlchemy
from app.test.fixtures import app, db  # noqa
from .model import Item

@fixture
def item() -> Item:
    return Item(
        id=1,
        name="test item",
        quantity=1,
        sellerid=1,
        can_buy=True,
        price=10,
        can_bid=False,
        quality="New",
        description="test test test"
    )

__tablename__ = "items"


def test_Item_create(item: Item):
    assert item


def test_Item_retrieve(item: Item, db: SQLAlchemy):  # noqa
    db.session.add(item)
    db.session.commit()
    s = Item.query.first()
    assert s.__dict__ == item.__dict__
