"""/web/app/syzygy/business_reports/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

log = logging.getLogger(__name__)


class BusinessReport(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "business_reports"

    id = db.Column(db.Integer, primary_key=True)

    # reported (item) and reporter (user) ids
    reporter_user_id = db.Column(db.Integer)
    reported_business_id = db.Column(db.Integer)

    # additional information regarding the report, entered by the reporter
    report_reason = db.Column(db.String)
    report_comment = db.Column(db.String)

    # admin stuff
    reviewed_by_id = db.Column(db.Integer, nullable=True)
    reviewed_by_name = db.Column(db.String, nullable=True)
    reviewed_at = db.Column(db.DateTime, nullable=True)

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    def __init__(self, **kwargs):
        super(BusinessReport, self).__init__(**kwargs)

        self.created_at = datetime.now()
        self.modified_at = datetime.now()

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        self.modified_at = datetime.now()

        return self
