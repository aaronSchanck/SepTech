from pytest import fixture

from .model import Order
from .schema import OrderSchema

@fixture
def schema() -> OrderSchema:
    return OrderSchema()


def test_OrderSchema_create(schema: OrderSchema):
    assert schema


def test_OrderSchema_works(schema: OrderSchema):

    order = Order(
        id=1,
        ordered=True,
        total_price=50,)

    assert order.id == 1
    assert order.ordered == True
    assert order.total_price == 50