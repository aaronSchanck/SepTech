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
