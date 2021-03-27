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


class OrderSchema(Schema):
    id = fields.Number(dump_only=True)