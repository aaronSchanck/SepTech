"""/web/app/syzygy/items/schema.py

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


class ItemSchema(Schema):
    itemid = fields.Number(dump_only=True)
    name = fields.Str(required=True)
    discriminator = fields.Str(required=True)
