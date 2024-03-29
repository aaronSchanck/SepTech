"""/web/app/syzygy/item_reports/schema.py

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


class ItemReportSchema(Schema):
    id = fields.Integer(dump_only=True)
