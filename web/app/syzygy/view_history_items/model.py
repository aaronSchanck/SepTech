"""/web/app/syzygy/view_history_items/model.py

Author: Adam Green (adam.green1@maine.edu)
        Ashley Drexler (ashley.drexler@maine.edu)


[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from datetime import datetime

log = logging.getLogger(__name__)


class ViewHistoryItem(db.Model):
    """View History entity in database

    Args:
        db ([type]): [description]

    Returns:
        [type]: [description]
    """

    __tablename__ = "view_history_items"

    id = db.Column(db.Integer, primary_key=True)

    itemid = db.Column(db.Integer, db.ForeignKey("items.id"))
    item = db.relationship("Item")

    viewhistoryid = db.Column(db.Integer, db.ForeignKey("view_history.id"))
    viewhistory = db.relationship("ViewHistory", back_populates="view_history_items")

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    def __init__(self, **kwargs):
        super(ViewHistoryItem, self).__init__(**kwargs)
        self.created_at = datetime.now()
        self.modified_at = datetime.now()

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        self.modified_at = datetime.now()

        return self
