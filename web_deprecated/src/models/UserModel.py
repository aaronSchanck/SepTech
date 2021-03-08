from marshmallow import fields, Schema
from . import db

class UserModel(db.Model):
    """
    User Model
    """

    # table name
    __tablename__ = 'users'

    uuid = db.Column(db.Integer, primary_key=True)
    email = db.Column(db.String(128), unique=True, nullable=False)
    username = db.Column(db.String(128), nullable=False)
    password = db.Column(db.String(128), nullable=False)
    first_name = db.Column(db.String(64), nullable=False)
    last_name = db.Column(db.String(64), nullable=False)
    date_of_birth = db.Column(db.DateTime)
    # created_at = db.Column(db.DateTime)
    # modified_at = db.Column(db.DateTime)
    
    def __init__(self, data):
        self.email = data.get('email')
        self.username = data.get('username')
        self.password = data.get('password')
        self.first_name = data.get('first_name')
        self.last_name = data.get('last_name')
        self.date_of_birth = data.get('date_of_birth')

    def save(self):
        db.session.add(self)
        db.session.commit()

    def update(self, data):
        pass

    @staticmethod
    def get_all_users():
        return UserModel.query.all()

    @staticmethod
    def get_one_user(id):
        return UserModel.query.get(id)

    @staticmethod
    def get_user_by_email(value):
        return UserModel.query.filter_by(email=value).first()

    def __repr__(self):
        return '<id {}>'.format(self.id)


class UserSchema(Schema):
    id = fields.Int(dump_only=True)
    email = fields.Str(required=True)
    username = fields.Email(required=True)
    password = fields.Str(required=True, load_only=True)
    first_name = fields.Str(required=True)
    last_name = fields.Str(required=True)
    dob = fields.DateTime()
