"""/web/app/syzygy/view_history/schema.py

Author: Adam Green (adam.green1@maine.edu)
        Ashley Drexler (ashley.drexler@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from marshmallow import Schema, fields

from ..view_history_items.schema import ViewHistoryItemSchema

log = logging.getLogger(__name__)


class ViewHistorySchema(Schema):
    id = fields.Number(dump_only=True)

    userid = fields.Number()

    view_history_items = fields.List(fields.Nested(ViewHistoryItemSchema), dump_only=True)

    created_at = fields.DateTime(dump_only=True)
    modified_at = fields.DateTime(dump_only=True)
