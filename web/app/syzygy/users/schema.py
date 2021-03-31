"""/web/app/syzygy/users/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from datetime import datetime

from sqlalchemy.dialects.postgresql import UUID

from marshmallow import Schema, fields

log = logging.getLogger(__name__)


class UserSchema(Schema):
    userid = fields.Number(dump_only=True)
    email = fields.Email(required=True)
    password = fields.Str(required=True, load_only=True)
    full_name = fields.Str(required=True)
    date_of_birth = fields.Date()
    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)
    phone_number = fields.String()
    password_salt1 = fields.String(required=True)
    password_reset_code = fields.String(dump_only=True)
    password_reset_timeout = fields.DateTime(dump_only=True)