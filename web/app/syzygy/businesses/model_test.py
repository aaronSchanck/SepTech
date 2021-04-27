from pytest import fixture
from flask_sqlalchemy import SQLAlchemy
from app.test.fixtures import app, db  # noqa
from .model import Business


@fixture
def business() -> Business:
    return Business(
        id=5,
        business_name="Test business",
        email="Test email",
        password=b"somepassword",
        owner_full_name="test owner name",
        phone_number="0123456789",
        description="test test test"
    )


def test_Business_create(business: Business):
    assert business


def test_Business_retrieve(business: Business, db: SQLAlchemy):  # noqa
    db.session.add(business)
    db.session.commit()
    s = Business.query.first()
    assert s.__dict__ == business.__dict__
