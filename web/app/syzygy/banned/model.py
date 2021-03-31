"""/web/app/syzygy/banned/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from .interface import BannedInterface

log = logging.getLogger(__name__)


class Banned(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "banned"

    id = db.Column(db.Integer, primary_key=True)

    def update(self, changes: BannedInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
