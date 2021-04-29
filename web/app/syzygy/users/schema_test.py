from pytest import fixture

from .model import User
from .schema import UserSchema


@fixture
def schema() -> UserSchema:
    return UserSchema()


def test_UserSchema_create(schema: UserSchema):
    assert schema


def test_UserSchema_works(schema: UserSchema):

    user = User(id=1,
        full_name="Test user",
        email="Test purpose",
        password=b"somepassword")

    assert user.id == 1
    assert user.full_name == "Test user"
    assert user.email == "Test purpose"
    assert user.password == b"somepassword"