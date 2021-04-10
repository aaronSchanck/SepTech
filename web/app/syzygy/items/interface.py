"""/web/app/syzygy/items/interface.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging
from typing import List, TypedDict

log = logging.getLogger(__name__)


class ItemInterface(TypedDict, total=False):
    pass
    # id: int
    # name: str
    # quantity: int
    # created_at: str
    # modified_at: str
    # sellerid: int
    # price: str
    # can_buy: bool
    # can_bid: bool
    # highest_bid: float
    # bidding_ends: str
    # quality: str
    # thumbnail: int
    # description: str
