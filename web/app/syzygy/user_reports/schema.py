"""/web/app/syzygy/user_reports/schema.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

import datetime as dt

from sqlalchemy.dialects import postgresql

from marshmallow import Schema, fields

log = logging.getLogger(__name__)


class UserReportSchema(Schema):
    id = fields.Integer(dump_only=True)
    reporter_id = fields.Integer(required=True)
    reported_id = fields.Integer(required=True)

    report_reason = fields.Str(required=True)
    report_comment = fields.Str(required=False)

    created_at = fields.DateTime(dump_only=True)

    # admin stuff
    reviewed_by_id = fields.Integer(dump_only=True)
    reviewed_by_name = fields.String(dump_only=True)
    reviewed_at = fields.DateTime(dump_only=True)