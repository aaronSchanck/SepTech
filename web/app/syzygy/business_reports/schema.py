"""/web/app/syzygy/business_reports/schema.py

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


class BusinessReportSchema(Schema):
    id = fields.Integer(dump_only=True)
    reporter_user_id = fields.Integer()
    reported_business_id = fields.Integer()

    # additional information regarding the report, entered by the reporter
    report_reason = db.Column(db.String)
    report_comment = db.Column(db.String)

    created_at = db.Column(db.DateTime, dump_only=True)

    # admin stuff
    reviewed_by_id = fields.Int(dump_only=True)
    reviewed_by_name = fields.Str(dump_only=True)
    reviewed_at = fields.DateTime(dump_only=True)
