from pytest import fixture

from .model import Business
from .schema import BusinessSchema


@fixture
def schema() -> BusinessSchema:
    return BusinessSchema()


def test_BusinessSchema_create(schema: BusinessSchema):
    assert schema


def test_BusinessSchema_works(schema: BusinessSchema):

    business = Business(
        id=5,
        business_name="Test business",
        email="Test email",
        password=b"somepassword",
        owner_full_name="test owner name",
        phone_number="0123456789",
        description="test test test")

    assert business.id == 5
    assert business.business_name == "Test business"
    assert business.email == "Test email"
    assert business.password == b"somepassword"
    assert business.owner_full_name == "test owner name"
    assert business.phone_number == "0123456789"
    assert business.description == "test test test"