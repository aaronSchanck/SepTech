"""/web/app/syzygy/items/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

import marshmallow as ma
from app.ma_fields import FileStorageField
from marshmallow import Schema, fields
from marshmallow_sqlalchemy import ModelSchema

from ..categories.schema import CategorySchema
from ..item_reports.schema import ItemReportSchema
from ..item_reviews.schema import ItemReviewSchema
from .model import Item

log = logging.getLogger(__name__)


class ItemLightSchema(Schema):
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
    price = fields.Integer(allow_none=True)

    can_bid = fields.Bool()
    starting_bid = fields.Integer(allow_none=True)
    min_bid_increment = fields.Integer(allow_none=True)

    highest_bid = fields.Integer(dump_only=True)
    highest_bid_user = fields.Integer(dump_only=True)
    bidding_ends = fields.DateTime(allow_none=True)

    category_id = fields.Integer(dump_only=True)
    category = fields.Nested(CategorySchema)

    item_reviews = fields.List(fields.Nested(ItemReviewSchema()), dump_only=True)

    thumbnail = fields.Integer()
    item_variants = fields.List(fields.Integer, dump_only=True)
    description = fields.Str()
    # attributes = fields.Dict()  # act as filters


class ImageSchema(Schema):
    image = FileStorageField(required=True, load_only=True)


class SearchSchema(Schema):
    page = fields.Int()


class ItemFullSchema(Schema):
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
    price = fields.Integer(allow_none=True)

    can_bid = fields.Bool()
    starting_bid = fields.Integer(allow_none=True)
    min_bid_increment = fields.Integer(allow_none=True)

    highest_bid = fields.Integer(dump_only=True)
    highest_bid_user = fields.Integer(dump_only=True)
    bidding_ends = fields.DateTime(allow_none=True)

    category_id = fields.Integer(dump_only=True)
    category = fields.Nested(CategorySchema)

    item_reviews = fields.List(fields.Nested(ItemReviewSchema()), dump_only=True)

    reports = fields.List(fields.Nested(ItemReportSchema()), dump_only=True)

    item_variants = fields.List(fields.Integer, dump_only=True)
    description = fields.Str()
    # attributes = fields.Dict()  # act as filters
