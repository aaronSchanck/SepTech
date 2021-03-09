from typing import List
from flask import request

from flask_restx import Namespace, Resource
from flask_accepts import accepts, responds

from .schema import UserSchema
from .service import UserService
from .model import User
from .interface import UserInterface

api = Namespace("User")


@api.route("/")
class UserResource(Resource):

    @responds(schema=UserSchema(many=True))
    def get(self):
        """Get all Users"""

        return UserService.get_all()

    @accepts(schema=UserSchema, api=api)
    @responds(schema=UserSchema)
    def post(self):
        """Create a Single User"""

        return UserService.create(request.parsed_obj)


@api.route("/<int:userid>")
@api.param("userid", "User database ID")
class UserIdResource(Resource):
    @responds(schema=UserSchema)
    def get(self, userid: int):
        """Get Single User"""

        return UserService.get_by_id(userid)

    def delete(self, userid: int):
        """Delete Single User"""
        from flask import jsonify

        id = UserService.delete_by_id(userid)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=UserSchema, api=api)
    @responds(schema=UserSchema)
    def put(self, userid: int):
        """Update Single User"""

        changes: UserInterface = request.parsed_obj
        User = UserService.get_by_id(userid)
        return UserService.update(User, changes)
