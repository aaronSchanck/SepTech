"""/web/app/syzygy/search_filters/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import SearchFilter
from .schema import SearchFilterSchema

BASE_ROUTE = "search_filters"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as item_report_api

    api.add_namespace(item_report_api, path=f"/{root}/{BASE_ROUTE}")
