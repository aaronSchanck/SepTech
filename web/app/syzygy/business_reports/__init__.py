"""/web/app/syzygy/item_reports/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import ItemReport
from .schema import ItemReportSchema

BASE_ROUTE = "item_reports"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as item_report_api

    api.add_namespace(item_report_api, path=f"/{root}/{BASE_ROUTE}")
