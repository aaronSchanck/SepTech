"""/web/app/syzygy/address/schema.py

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


class AddressSchema(Schema):
    unit = fields.String()
    building = fields.String()
    street_name = fields.String()
    street_type = fields.String()
    city = fields.String()
    region = fields.String()
    country = fields.String()
    address_code = fields.String()
    postal_code = fields.String()
