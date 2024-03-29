"""/web/app/syzygy/business_reports/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import BusinessReport
from .schema import BusinessReportSchema

BASE_ROUTE = "business_reports"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as business_report_api

    api.add_namespace(business_report_api, path=f"/{root}/{BASE_ROUTE}")
