"""/web/app/syzygy/categories/schema.py

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


class CategorySchema(Schema):
    class Meta:
        ordered = True

    id = fields.Number(dump_only=True)
    category_1 = fields.Str()
    category_2 = fields.Str()
    category_3 = fields.Str()
    category_4 = fields.Str()
    category_5 = fields.Str()
