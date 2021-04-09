"""/web/app/syzygy/bids/interface.py

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


class BidInterface(TypedDict, total=False):
    id: int
    business_name: str
    email: str
    password: str
    owner_full_name: str
    created_at: str
    modified_at: str
    phone_number: str
    password_salt1: str
    password_reset_code: str
    password_reset_timeout: str
    last_successful_login: str
    last_unsuccessful_login: str
