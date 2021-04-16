"""/web/app/syzygy/order_items/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from marshmallow import Schema, fields

log = logging.getLogger(__name__)


class OrderItemSchema(Schema):
    id = fields.Number(dump_only=True)
    quantity = fields.Number(dump_only=True)
    price = fields.Str()

    userid = fields.Int()

    itemid = fields.Int()

    orderid = fields.Int(allow_none=True)

    added_at = fields.DateTime(dump_only=True)