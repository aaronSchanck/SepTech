"""/web/app/syzygy/items/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Item
from ..categories.schema import CategorySchema
from marshmallow_sqlalchemy import ModelSchema

from marshmallow import Schema, fields
import marshmallow as ma
from app.fields.fields import Image

log = logging.getLogger(__name__)


class ItemSchema(Schema):
    class Meta:
        ordered = True

    itemid = fields.Integer(dump_only=True)
    name = fields.String()
    quantity = fields.Integer()
    posted_at = fields.DateTime(dump_only=True)
    seller = fields.Integer()
    price = fields.Number()
    can_buy = fields.Bool()
    can_bid = fields.Bool()
    highest_bid = fields.Number(dump_only=True)
    highest_bid_user = fields.Integer(dump_only=True)
    bidding_ends = fields.DateTime()
    quality = fields.String()
    category_id = fields.Integer(dump_only=True)
    category = fields.Nested(CategorySchema)

    thumbnail = fields.Integer()
    images = fields.List(Image())
    item_variants = fields.List(fields.Integer, dump_only=True)
    description = fields.Str()
    # attributes = fields.Dict()  # act as filters