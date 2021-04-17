"""/web/app/syzygy/item_reviews/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from marshmallow import Schema, fields

from ..users.schema import UserSchema

log = logging.getLogger(__name__)


class ItemReviewSchema(Schema):
    id = fields.Number(dump_only=True)
    userid = fields.Number(required=True)
    user = fields.Nested(UserSchema())
    itemid = fields.Number(required=True)

    rating = fields.Integer(required=True)
    review_content = fields.Str()

    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)