from pytest import fixture
from flask_sqlalchemy import SQLAlchemy
from app.test.fixtures import app, db  # noqa
from .model import OrderItem


@fixture
def order_item() -> OrderItem:
    return OrderItem(
        id=10,
        quantity=5,
        price=45,
        itemid=1,
        orderid=1
    )

def test_OrderItem_create(order_item: OrderItem):
    assert order_item


def test_OrderItem_retrieve(order_item: OrderItem, db: SQLAlchemy):  # noqa
    db.session.add(order_item)
    db.session.commit()
    s = OrderItem.query.first()
    assert s.__dict__ == order_item.__dict__
