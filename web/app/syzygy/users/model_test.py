from pytest import fixture
from flask_sqlalchemy import SQLAlchemy
from app.test.fixtures import app, db  # noqa
from .model import User


@fixture
def user() -> User:
    return User(
        id=1,
        full_name="Test user",
        email="Test purpose",
        password=b"somepassword",
    )


def test_User_create(user: User):
    assert user


def test_User_retrieve(user: User, db: SQLAlchemy):  # noqa
    db.session.add(user)
    db.session.commit()
    s = User.query.first()
    assert s.__dict__ == user.__dict__
