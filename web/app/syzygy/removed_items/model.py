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

from .interface import RemovedItemInterface

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

    def update(self, changes: RemovedItemInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
