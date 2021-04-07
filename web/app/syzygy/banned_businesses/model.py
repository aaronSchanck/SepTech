"""/web/app/syzygy/banned_businesses/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from .interface import BannedBusinessInterface

log = logging.getLogger(__name__)


class BannedBusiness(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "banned_businesses"

    id = db.Column(db.Integer, primary_key=True)

    def update(self, changes: BannedBusinessInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self
