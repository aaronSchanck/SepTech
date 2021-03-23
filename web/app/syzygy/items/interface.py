"""/web/app/syzygy/items/interface.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from typing import TypedDict, List

log = logging.getLogger(__name__)


class ItemInterface(TypedDict, total=False):
    itemid: int
    name: str
    # quantity: int
    # posted_at: str
    # seller: int
    # price: str
    # can_buy: bool
    # can_bid: bool
    # highest_bid: float
    # bidding_ends: str
    # quality: str
    # thumbnail: int
    # item_variants: List
    # description: str
