"""/web/app/syzygy/user_reports/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from .interface import UserReportInterface

log = logging.getLogger(__name__)


class UserReport(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "user_reports"

    id = db.Column(db.Integer, primary_key=True)
    reporter_user_id = db.Column(db.Integer)
    reported_user_id = db.Column(db.Integer)

    report_reason = db.Column(db.String)
    report_comment = db.Column(db.String)

    created_at = db.Column(db.DateTime)

    # admin stuff
    reviewed_by_id = db.Column(db.Integer, nullable=True)
    reviewed_by_name = db.Column(db.String, nullable=True)
    reviewed_at = db.Column(db.DateTime, nullable=True)

    def update(self, changes: UserReportInterface):
        for key, val in changes.users():
            setattr(self, key, val)

        return self
