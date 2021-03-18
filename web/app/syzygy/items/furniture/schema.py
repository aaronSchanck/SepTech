"""/web/app/syzygy/items/furniture/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from marshmallow import Schema, fields

from ..schema import ItemSchema

log = logging.getLogger(__name__)


class FurnitureSchema(ItemSchema):
    discriminator = fields.Str(default="furniture", load_only=True)
    subcategory_1 = fields.Str()
    subcategory_2 = fields.Str()
    subcategory_3 = fields.Str()
    subcategory_4 = fields.Str()
    subcategory_5 = fields.Str()
