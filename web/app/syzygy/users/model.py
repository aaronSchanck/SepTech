from sqlalchemy import Column, Integer, String, DateTime
from app import db
from .interface import UserInterface


class User(db.Model):

    __tablename__ = "users"

    userid = Column(Integer, primary_key=True)
    email = Column(String(127), unique=True, nullable=False)
    username = Column(String(127), nullable=False)
    password = Column(String(127), nullable=False)
    first_name = Column(String(127))
    last_name = Column(String(127))
    date_of_birth = Column(DateTime)
    created_at = Column(DateTime)
    modified_at = Column(DateTime)

    def update(self, changes: UserInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
