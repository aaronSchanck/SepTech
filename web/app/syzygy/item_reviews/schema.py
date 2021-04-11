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

log = logging.getLogger(__name__)


class ItemReviewSchema(Schema):
    id = fields.Number(dump_only=True)