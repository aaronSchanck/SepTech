"""/web/app/syzygy/business_bans/interface.py

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


class BusinessBanInterface(TypedDict, total=False):
    id: int
    businessid: int