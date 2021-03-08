from app import db
from .model import User
from .interface import UserInterface


class UserService:
    @staticmethod
    def get_all():
        return User.query.all()

    @staticmethod
    def get_by_id(userid: int):
        return User.query.get(userid)

    @staticmethod
    def update(user: User, User_change_updates: UserInterface):
        user.update(User_change_updates)
        db.session.commit()
        return user

    @staticmethod
    def delete_by_id(userid: int):
        user = User.query.filter(User.userid == userid).first()
        if not user:
            return []
        db.session.delete(user)
        db.session.commit()
        return [userid]

    @staticmethod
    def create(new_attrs: UserInterface):
        new_user = User(name=new_attrs["name"], purpose=new_attrs["purpose"])

        db.session.add(new_user)
        db.session.commit()

        return new_user
