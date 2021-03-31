"""/web/app/syzygy/items/categories/interface.py

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


class CategoryInterface(TypedDict, total=False):
    category_id: int
    category_1: str
    category_2: str
    category_3: str
    category_4: str
    category_5: str
