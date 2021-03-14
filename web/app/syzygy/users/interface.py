"""/web/app/syzygy/localUsers/interface.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from typing import TypedDict

log = logging.getLogger(__name__)


class UserInterface(TypedDict, total=False):
    userid: int
    username: str
    email: str
    password: str
    first_name: str
    last_name: str
    date_of_birth: str
    created_at: str
    modified_at: str
