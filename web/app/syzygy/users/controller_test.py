from unittest.mock import patch
from flask.testing import FlaskClient

from app.test.fixtures import client, app  # noqa
from .service import UserService
from .schema import UserSchema
from .model import User
from . import BASE_ROUTE
from datetime import datetime, date



def make_user(
    id: int = 1, 
    username: str = "yin", 
    email: str = "yin@gmail.com", 
    password: str = "password", 
    full_name: str = "Yin", 
    dob: date = date(1994,10,10), 
    phone_number: str = "0123456789", 
    password_salt: str = "test"
) -> User:
    return User(id=id, username=username, email=email, password=password, full_name=full_name, date_of_birth=dob, phone_number=phone_number, password_salt=password_salt)


class TestUserResource:
    @patch.object(
        UserService,
        "get_all",
        lambda: [
            make_user(1, username="test1", email="test1@gmail.com"),
            make_user(2, username="test2", email="test2@gmail.com"),
            make_user(3, username="test3", email="test3@gmail.com")
        ],
    )
    def test_get(self, client: FlaskClient):  # noqa
        with client:
            results = client.get(f"/api/{BASE_ROUTE}", follow_redirects=True).get_json()
            expected = (
                UserSchema(many=True)
                .dump(
                    [
                        make_user(1, username="test1", email="test1@gmail.com"),
                        make_user(2, username="test2", email="test2@gmail.com"),
                        make_user(3, username="test3", email="test3@gmail.com")
                    ]
                )
                
            )
            
            for e in range(len(expected)):
                assert results[e]['id'] == expected[e]['id']
                assert results[e]['username'] == expected[e]['username']
                assert results[e]['email'] == expected[e]['email']
                assert results[e]['password_salt'] == expected[e]['password_salt']
                assert results[e]['full_name'] == expected[e]['full_name']
                assert results[e]['date_of_birth'] == expected[e]['date_of_birth']
                assert results[e]['admin_level'] == expected[e]['admin_level']
                assert results[e]['phone_number'] == expected[e]['phone_number']
                # modified at and created at is wack

    @patch.object(
        UserService, "create", lambda create_request: User(**create_request)
    )
    def test_post(self, client: FlaskClient):  # noqa
        with client:

            payload = dict(
                            email="testemail@gmail.com",
                            password="string",
                            password_salt="salty",
                            phone_number="",
                            full_name="name",
                            date_of_birth="2021-04-28")
            result = client.post(f"/api/{BASE_ROUTE}/", json=payload).get_json()
            print("FUCK: ", payload["date_of_birth"])
            expected = (
                UserSchema()
                .dump(User(
                    email=payload["email"], 
                    password_salt=payload["password_salt"],
                    phone_number=payload["phone_number"], 
                    full_name=payload["full_name"], 
                    date_of_birth=date(2021,4,28),
                    password=payload["password"]))
                
            )
          
            assert result['id'] == expected['id']
            assert result['username'] == expected['username']
            assert result['email'] == expected['email']
            assert result['password_salt'] == expected['password_salt']
            assert result['full_name'] == expected['full_name']
            assert result['date_of_birth'] == expected['date_of_birth']
            assert result['admin_level'] == expected['admin_level']
            assert result['phone_number'] == expected['phone_number']
            # modified at and created at is wack