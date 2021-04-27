from pytest import fixture

from .model import OrderItem
from .schema import OrderItemSchema


@fixture
def schema() -> OrderItemSchema:
    return OrderItemSchema()


def test_OrderItemSchema_create(schema: OrderItemSchema):
    assert schema


def test_OrderItemSchema_works(schema: OrderItemSchema):

    order_item = OrderItem(
        id=10,
        quantity=5,
        price=45,
        itemid=1,
        orderid=1)

    assert order_item.id == 10
    assert order_item.quantity == 5
    assert order_item.price == 45
    assert order_item.itemid == 1
    assert order_item.orderid == 1