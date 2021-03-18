"""/web/app/syzygy/items/interface.py

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


class ElectronicInterface(TypedDict, total=False):
    itemid: int
    name: str
    discriminator: str
    subcategory_1: str
    subcategory_2: str
    subcategory_3: str
    subcategory_4: str
    subcategory_5: str
