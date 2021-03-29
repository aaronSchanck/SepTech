"""/web/app/syzygy/users/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

import datetime as dt

from sqlalchemy.dialects import postgresql

from marshmallow import Schema, fields

log = logging.getLogger(__name__)


class OrderItemSchema(Schema):
    userid = fields.Number(dump_only=True)
    email = fields.Email(required=True)
    password = fields.Str(required=True, load_only=True)
    full_name = fields.Str(required=True)
    date_of_birth = fields.Date()
    created_at = fields.DateTime()
    modified_at = fields.DateTime()
    phone_number = fields.String()
    password_salt1 = fields.String(required=True)
