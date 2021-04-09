"""/web/app/syzygy/sessions/schema.py

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


class SessionSchema(Schema):
    id = fields.Number(dump_only=True)
    userid = fields.Number()

    unique_session_id = fields.String()

    session_expires_at = fields.Datetime()