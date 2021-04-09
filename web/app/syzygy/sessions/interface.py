"""/web/app/syzygy/sessions/interface.py

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


class SessionInterface(TypedDict, total=False):
    id: int
    userid: int
    unique_session_id: str
    session_expires_at: str