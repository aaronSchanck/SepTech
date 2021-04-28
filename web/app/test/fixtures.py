import pytest

from app import create_app


@pytest.fixture(scope="session")
def app():
    return create_app("test")


@pytest.fixture(scope="session")
def db(app):
    from app import db

    print("hello")

    with app.app_context():
        db.drop_all()
        db.create_all()
        yield db
        db.drop_all()
        db.session.commit()


@pytest.fixture(scope="session")
def client(app):
    return app.test_client()