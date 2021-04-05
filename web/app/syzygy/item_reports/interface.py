"""/web/app/syzygy/item_reports/interface.py

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


class ItemReportInterface(TypedDict, total=False):
    id: int
