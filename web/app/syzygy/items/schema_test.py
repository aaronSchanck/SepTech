from pytest import fixture

from .model import Item
from .schema import ItemLightSchema
from .schema import ItemFullSchema


@fixture
def schema() -> ItemLightSchema:
    return ItemLightSchema()


def test_ItemLightSchema_create(schema: ItemLightSchema):
    assert schema


def test_ItemLightSchema_works(schema: ItemLightSchema):

    item = Item(id=1,
        name="test item",
        quantity=1,
        sellerid=1,
        can_buy=True,
        price=10,
        can_bid=False,
        quality="New",
        description="test test test")

    assert item.id == 1
    assert item.name == "test item"
    assert item.quantity == 1
    assert item.sellerid == 1
    assert item.can_buy == True
    assert item.price == 10
    assert item.quality == "New"
    assert item.description == "test test test"

@fixture
def schema() -> ItemFullSchema:
    return ItemFullSchema()


def test_ItemFullSchemaa_create(schema: ItemFullSchema):
    assert schema


def test_ItemFullSchema_works(schema: ItemFullSchema):

    item = Item(id=1,
        name="test item",
        quantity=1,
        sellerid=1,
        can_buy=True,
        price=10,
        can_bid=False,
        quality="New",
        description="test test test")

    assert item.id == 1
    assert item.name == "test item"
    assert item.quantity == 1
    assert item.sellerid == 1
    assert item.can_buy == True
    assert item.price == 10
    assert item.quality == "New"
    assert item.description == "test test test"