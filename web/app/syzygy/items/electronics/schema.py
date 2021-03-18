"""/web/app/syzygy/electronics/schema.py

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


class ElectronicSchema(ItemSchema):
    itemid = fields.Number(dump_only=True)
    name = fields.Str(required=True)
