"""/web/app/syzygy/wishlist/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from marshmallow import Schema, fields

from ..wishlist_items.schema import WishlistItemSchema

log = logging.getLogger(__name__)


class WishlistSchema(Schema):
    id = fields.Number(dump_only=True)

    userid = fields.Number()

    wishlist_items = fields.List(fields.Nested(WishlistItemSchema), dump_only=True)

    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)
