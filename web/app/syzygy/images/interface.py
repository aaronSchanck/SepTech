"""/web/app/syzygy/images/interface.py

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


class ItemImageImageInterface(TypedDict, total=False):
    itemid: int
    name: str
    discriminator: str
