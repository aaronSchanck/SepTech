# /src/views/UserView

from flask import request, json, Response, Blueprint, g
from ..models.UserModel import UserModel, UserSchema
from ..utils.auth import Auth
from ..utils.util import respond

user_api = Blueprint('user_api', __name__)
user_schema = UserSchema()


@user_api.route('/', methods=['POST'])
def create():
    """
    Create User Function
    """
    req_data = request.get_json()
    data, error = user_schema.load(req_data)

    if error:
        return respond(error, 400)

    # check if user already exist in the db
    user_in_db = UserModel.get_user_by_email(data.get('email'))
    if user_in_db:
        message = {'error': 'Duplicate email on user entry'}
        return respond(message, 400)

    user = UserModel(data)
    user.save()
    ser_data = user_schema.dump(user).data
    token = Auth.generate_token(ser_data.get('id'))
    return respond({'jwt_token': token}, 201)


@user_api.route('/', methods=['GET'])
def get_all():
    """
    Get all users
    """
    users = UserModel.get_all_users()
    ser_users = user_schema.dump(users, many=True).data
    return respond(ser_users, 200)


@user_api.route('/<int:user_id>', methods=['GET'])
def get_a_user(user_id):
    """
    Get a single User
    """
    user = UserModel.get_one_user(user_id)
    if not user:
        return respond({'error': 'user not found'}, 404)

    ser_user = user_schema.dump(user).data
    return respond(ser_user, 200)


@user_api.route('/<int:user_id>', methods=['DELETE'])
def delete():
    """
    Delete a user
    """
    user = UserModel.get_one_user(g.user.get('id'))
    user.delete()
    return respond({'message': 'deleted'}, 204)


@user_api.route('/me', methods=['GET'])
def get_me():
    """
    Get current user
    """
    user = UserModel.get_one_user(g.user.get('id'))
    ser_user = user_schema.dump(user).data
    return respond(ser_user, 200)


@user_api.route('/login', methods=['POST'])
def login():
    """
    User Login Function
    """
    req_data = request.get_json()

    data, error = user_schema.load(req_data, partial=True)
    if error:
        return respond(error, 400)
    if not data.get('email') or not data.get('password'):
        return respond({'error': 'you need email and password to sign in'}, 400)
    user = UserModel.get_user_by_email(data.get('email'))
    if not user:
        return respond({'error': 'invalid credentials'}, 400)
    if not user.check_hash(data.get('password')):
        return respond({'error': 'invalid credentials'}, 400)
    ser_data = user_schema.dump(user).data
    token = Auth.generate_token(ser_data.get('id'))
    return respond({'jwt_token': token}, 200)
