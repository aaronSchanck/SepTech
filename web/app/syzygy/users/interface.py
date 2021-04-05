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


class UserInterface(TypedDict, total=False):
    email: str
    password: str
    full_name: str
    date_of_birth: str
    created_at: str
    modified_at: str
    phone_number: str
    password_salt: str
    password_reset_code: str
    password_reset_timeout: str
    last_successful_login: str
    last_unsuccessful_login: str