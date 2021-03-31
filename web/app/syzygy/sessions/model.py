"""/web/app/syzygy/session/model.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from app import db

from .interface import SessionInterface

log = logging.getLogger(__name__)


class Session(db.Model):
    """[summary]

    :param db: [description]
    :type db: [type]
    :return: [description]
    :rtype: [type]
    """

    __tablename__ = "session"

    id = db.Column(db.Integer, primary_key=True)

    def update(self, changes: SessionInterface):
        for key, val in changes.items():
            setattr(self, key, val)

        return self