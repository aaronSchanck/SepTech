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
    category: str
    sub_category1: str
    sub_category2: str
    sub_category3: str
    sub_category4: str
