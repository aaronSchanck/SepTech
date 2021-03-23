"""/web/app/syzygy/items/categories/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from marshmallow import Schema, fields
from marshmallow_sqlalchemy import ModelSchema
from .model import Category

log = logging.getLogger(__name__)


class CategorySchema(Schema):
    class Meta:
        model = Category

    category_id = fields.Number(dump_only=True)
    category = fields.Str(required=True)
    sub_category1 = fields.Str()
    sub_category2 = fields.Str()
    sub_category3 = fields.Str()
    sub_category4 = fields.Str()
