"""/web/app/syzygy/order_items/interface.py

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


class OrderItemInterface(TypedDict, total=False):
    id: int
