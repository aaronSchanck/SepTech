"""/web/app/syzygy/removed_items/model.py

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


class RemovedItem(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "removed_items"

    id = db.Column(db.Integer, primary_key=True)

    remove_date = db.Column(db.DateTime())

    remove_reason = db.Column(db.String)

    def __init__(self, **kwargs):
        super(RemovedItem, self).__init__(**kwargs)

    def update(self, changes: dict):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
