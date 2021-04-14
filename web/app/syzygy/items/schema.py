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

from app.ma_fields import FileStorageField

log = logging.getLogger(__name__)


class ItemSchema(Schema):
    class Meta:
        ordered = True

    id = fields.Integer(dump_only=True)

    name = fields.String()
    quality = fields.String()
    quantity = fields.Integer()

    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)

    sellerid = fields.Integer()

    can_buy = fields.Bool()
    price = fields.Str(allow_none=True)

    can_bid = fields.Bool()
    starting_bid = fields.Str(allow_none=True)
    min_bid_increment = fields.Str(allow_none=True)

    highest_bid = fields.Number(dump_only=True)
    highest_bid_user = fields.Integer(dump_only=True)
    bidding_ends = fields.DateTime(allow_none=True)

    category_id = fields.Integer(dump_only=True)
    category = fields.Nested(CategorySchema, required=True)

    thumbnail = fields.Integer()
    image = fields.List(fields.String, dump_only=True)
    item_variants = fields.List(fields.Integer, dump_only=True)
    description = fields.Str()
    # attributes = fields.Dict()  # act as filters


class ImageSchema(Schema):
    image = FileStorageField(required=True, load_only=True)


class SearchSchema(Schema):
    page = fields.Int()
