"""/web/app/syzygy/wishlist_items/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from marshmallow import Schema, fields

from ..items.schema import ItemLightSchema

log = logging.getLogger(__name__)


class WishlistItemSchema(Schema):
    id = fields.Number(dump_only=True)

    itemid = fields.Number()

    wishlistid = fields.Number()

    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)

    item = fields.Nested(ItemLightSchema(), dump_only=True)