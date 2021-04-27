from pytest import fixture
from flask_sqlalchemy import SQLAlchemy
from app.test.fixtures import app, db  # noqa
from .model import Order

@fixture
def order() -> Order:
    return Order(
        id=1,
        ordered=True,
        total_price=50,
    )

__tablename__ = "orders"


def test_Order_create(order: Order):
    assert order


def test_Order_retrieve(order: Order, db: SQLAlchemy):  # noqa
    db.session.add(order)
    db.session.commit()
    s = Order.query.first()
    assert s.__dict__ == order.__dict__
