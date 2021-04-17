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

from ..items.schema import ItemSchema

log = logging.getLogger(__name__)


class OrderItemSchema(Schema):
    id = fields.Number(dump_only=True)

    itemid = fields.Int()
    orderid = fields.Int()

    quantity = fields.Number()
    price = fields.Int()

    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)

    item = fields.Nested(ItemSchema(), dump_only=True)