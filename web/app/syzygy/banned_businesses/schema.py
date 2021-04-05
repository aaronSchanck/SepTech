"""/web/app/syzygy/banned_businesses/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from datetime import datetime

from sqlalchemy.dialects.postgresql import UUID

from marshmallow import Schema, fields

log = logging.getLogger(__name__)


class BannedBusinessSchema(Schema):
    id = fields.Number(dump_only=True)