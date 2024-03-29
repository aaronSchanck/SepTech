"""/web/app/syzygy/businesses/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

import datetime as dt

from marshmallow import Schema, fields

log = logging.getLogger(__name__)


class BusinessSchema(Schema):
    id = fields.Number(dump_only=True)

    business_name = fields.Str(required=True)
    owner_full_name = fields.Str(required=True)

    email = fields.Email(required=True)
    password = fields.Str(required=True, load_only=True)

    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)

    phone_number = fields.String()

    password_salt = fields.String(required=True)

    password_reset_code = fields.String(dump_only=True)
    password_reset_timeout = fields.DateTime(dump_only=True)

    description = fields.Str()