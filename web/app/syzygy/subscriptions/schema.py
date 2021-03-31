"""/web/app/syzygy/subscriptions/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from datetime import datetime

from marshmallow import Schema, fields

log = logging.getLogger(__name__)


class SubscriptionSchema(Schema):
    id = fields.Number(dump_only=True)