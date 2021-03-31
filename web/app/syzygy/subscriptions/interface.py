"""/web/app/syzygy/subscriptions/interface.py

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


class SubscriptionInterface(TypedDict, total=False):
    id: int