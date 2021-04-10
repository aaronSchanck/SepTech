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
        ordered = True

    id = fields.Number(dump_only=True)
    category_1 = fields.Str(required=True)
    category_2 = fields.Str()
    category_3 = fields.Str()
    category_4 = fields.Str()
    category_5 = fields.Str()
