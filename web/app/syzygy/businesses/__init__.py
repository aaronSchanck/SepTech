"""/web/app/syzygy/businesses/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Business
from .schema import BusinessSchema

BASE_ROUTE = "businesses"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as business_api

    api.add_namespace(business_api, path=f"/{root}/{BASE_ROUTE}")
