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

log = logging.getLogger(__name__)


class WishlistSchema(Schema):
    id = fields.Number(dump_only=True)

    itemid = fields.Number()

    wishlistid = fields.Number(dump_only=True)