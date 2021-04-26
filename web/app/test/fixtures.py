import pytest

from app import create_app


@pytest.fixture
def app():
    return create_app("test")


@pytest.fixture
def db(app):
    from app import db

    print("hello")

    with app.app_context():
        db.drop_all()
        db.create_all()
        yield db
        db.drop_all()
        db.session.commit()


@pytest.fixture
def client(app, db):
    return app.test_client()