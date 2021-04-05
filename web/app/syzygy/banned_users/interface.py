"""/web/app/syzygy/banned_users/interface.py

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


class BannedUserInterface(TypedDict, total=False):
    id: int