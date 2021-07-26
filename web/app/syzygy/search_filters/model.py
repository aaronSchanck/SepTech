"""/web/app/syzygy/search_filters/model.py

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


class SearchFilter(db.Model):
    """A SearchFilter model in the database"""

    __tablename__ = "search_filters"

    id = db.Column(db.Integer, primary_key=True)

    created_at = db.Column(db.DateTime)
    modified_at = db.Column(db.DateTime)

    def __init__(self, **kwargs):
        super(SearchFilter, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
