"""/web/app/syzygy/users/controller.py

Author: Adam Green (adam.green1@maine.edu)

This file acts as the main router for the API. The main GET/POST/PUT/DELETE
requests are written here. This also draws the swagger UI on the API for
rudimentary testing on the browser.

Classes:

    UserResource:
        Extends Resource from flask-restx. Adding a function with name
        "get"/"post"/"delete"/"put" will add the respective route to the API.

    UserIdResource:
        Extends Resource from flask-restx. Follows same functionality from
        aforementioned class. Must be routed to with {baseurl}/{id}.

    UserLoginResource:
        Extends Resource from flask-restx. Acts as a helper class for logging
        in users.

"""

import logging
from typing import List

from flask import request
from flask_accepts import accepts, responds
from flask_restx import Namespace, Resource
from marshmallow import fields
from webargs.flaskparser import use_args, use_kwargs

from ..orders.schema import OrderSchema
from ..wishlist.schema import WishlistSchema
from .model import User
from .schema import UserSchema
from .service import UserService

api = Namespace("User")
log = logging.getLogger(__name__)


# declare schemas
user_schema = UserSchema()
user_schema_many = UserSchema(many=True)

order_schema = OrderSchema()

wishlist_schema = WishlistSchema()


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

        print(request.parsed_obj)

        return UserService.create(request.parsed_obj)


@api.route("/<int:id>")
@api.param("id", "User database ID")
class UserIdResource(Resource):
    @responds(schema=UserSchema)
    def get(self, id: int):
        """Get Single User"""

        return UserService.get_by_id(id)

    def delete(self, id: int):
        """Delete Single User"""
        from flask import jsonify

        id = UserService.delete_by_id(id)
        return jsonify(dict(status="Success", id=id))

    @accepts(schema=UserSchema, api=api)
    @responds(schema=UserSchema)
    def put(self, id: int):
        """Update Single User"""

        changes = request.parsed_obj
        print("Changes:")
        print(changes)

        user = UserService.get_by_id(id)
        return UserService.update(user, changes)


@api.route("/<email>")
@api.param("email", "User database email")
class UserEmailResource(Resource):
    @responds(schema=UserSchema)
    def get(self, email: str):
        return UserService.get_by_email(email)

    @accepts(
        dict(name="code", type=str, help="User input password reset code"),
        api=api,
    )
    def post(self, email: str):
        """User input verify password reset code"""
        code = request.parsed_args["code"]

        return UserService.verify_code(code, email)

    def put(self, email: str):
        """Forgot password API Endpoint"""
        result = UserService.reset_password(email)
        return jsonify(result)


@api.route("/<email>/check_exists")
@api.param("email", "User database email")
class UserEmailCheckResource(Resource):
    @responds(schema=UserSchema)
    def get(self, email: str):
        """Used to check whether a user exists at the given email, without actually sending the user object."""
        return UserService.check_exists(email)


@api.route("/login")
class UserLoginResource(Resource):
    @accepts(
        dict(name="email", type=str, help="A user's email"),
        dict(name="password", type=str, help="A user's password"),
        api=api,
    )
    @responds(schema=UserSchema)
    def post(self):
        """Login with user email address and password. If the user exists, then
        return the user object to the caller.
        """

        email = request.parsed_args["email"]
        password = request.parsed_args["password"]

        return UserService.login(email, password)


@api.route("/<int:id>/cart")
@api.param("id", "User ID in database")
class UserCartResource(Resource):
    args = {"itemid": fields.Int(required=True), "quantity": fields.Int(required=True)}

    def get(self, id: int):
        order, response = UserService.get_user_cart(id)

        return order_schema.dump(order) if response.status_code == 200 else response

    @use_args(args, location="form")
    def post(self, args, id: int):
        order_schema = OrderSchema()

        itemid = args["itemid"]
        quantity = args["quantity"]

        order, response = UserService.add_to_cart(id, itemid, quantity)

        return order_schema.dump(order) if response.status_code == 200 else response


@api.route("/<int:id>/wishlist")
@api.param("id", "User ID in database")
class UserWishlistResource(Resource):
    wishlist_args = {"itemid": fields.Int(required=True)}

    def get(self, id: int):
        wishlist, response = UserService.get_user_wishlist(id)

        print(wishlist_schema.dump(wishlist))

        return (
            wishlist_schema.dump(wishlist) if response.status_code == 200 else response
        )

    @use_args(wishlist_args, location="form")
    def post(self, args, id: int):
        itemid = args["itemid"]

        print(itemid)

        wishlist, response = UserService.add_to_wishlist(id, itemid)

        return (
            wishlist_schema.dump(wishlist) if response.status_code == 200 else response
        )
