from flask_sqlalchemy import SQLAlchemy
from typing import List
from app.test.fixtures import app, db  # noqa
from .model import User
from .service import UserService  # noqa
from datetime import datetime, date


def test_get_all(db: SQLAlchemy):  # noqa
    yin: User = User(id=1, username="yin12345", email="yin12345@gmail.com", password=b"password", full_name="Yin", date_of_birth=date(1994,10,10), phone_number="0123456789", password_salt="test")
    yang: User = User(id=2, username="yang12345", email="yang12345@gmail.com", password=b"password", full_name="Yang", date_of_birth=date(1994,10,10), phone_number="0123456789", password_salt="test")
    db.session.add(yin)
    db.session.add(yang)
    db.session.commit()

    results: List[User] = UserService.get_all()
    print(results)

    assert len(results) == 2
    assert yin in results and yang in results


def test_update(db: SQLAlchemy):  # noqa
    yin: User = User(id=5, username="yin", email="yin@gmail.com", password=b"password", full_name="Yin", date_of_birth=date(1994,10,10), phone_number="0123456789", password_salt="test")

    db.session.add(yin)
    db.session.commit()
    updates = dict(full_name="Yin Yin")

    UserService.update(yin, updates)

    result: User = User.query.get(yin.id)
    assert result.full_name == "Yin Yin"


def test_delete_by_id(db: SQLAlchemy):  # noqa
    yin: User = User(id=3, username="yin123456", email="yin123456@gmail.com", password=b"password", full_name="Yin", date_of_birth=date(1994,10,10), phone_number="0123456789", password_salt="test")
    yang: User = User(id=4, username="yang123456", email="yang123456@gmail.com", password=b"password", full_name="Yang", date_of_birth=date(1994,10,10), phone_number="0123456789", password_salt="test")
    db.session.add(yin)
    db.session.add(yang)
    db.session.commit()

    UserService.delete_by_id(3)
    UserService.delete_by_id(5)
    db.session.commit()

    results: List[User] = User.query.all()
    print(results)

    assert len(results) == 3
    assert yin not in results and yang in results


def test_create(db: SQLAlchemy):  # noqa
    yin = dict(id=1, username="yin12345", email="yin12345@gmail.com", password=b"password", full_name="Yin", date_of_birth=date(1994,10,10), phone_number="0123456789", password_salt="test")
    UserService.create(yin)
    results: List[User] = User.query.all()
    print(results)

    assert len(results) == 3

    for k in yin.keys():
        assert getattr(results[0], k) == yin[k]