"""/web/app/syzygy/item_reports/model.py

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


class ItemReport(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "item_reports"

    id = db.Column(db.Integer, primary_key=True)

    # reported (item) and reporter (user) ids
    reporter_user_id = db.Column(db.Integer)
    reported_item_id = db.Column(db.Integer)

    # additional information regarding the report, entered by the reporter
    report_reason = db.Column(db.String)
    report_comment = db.Column(db.String)

    created_at = db.Column(db.DateTime)

    # admin stuff
    reviewed_by_id = db.Column(db.Integer, nullable=True)
    reviewed_by_name = db.Column(db.String, nullable=True)
    reviewed_at = db.Column(db.DateTime, nullable=True)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
