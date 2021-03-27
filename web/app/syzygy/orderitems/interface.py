"""/web/app/syzygy/users/interface.py

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


class OrderItemsInterface(TypedDict, total=False):
    userid: int
    email: str
    password: str
    full_name: str
    date_of_birth: str
    created_at: str
    modified_at: str
    phone_number: str
    password_salt1: str
    password_salt2: str
