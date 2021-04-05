"""/web/app/syzygy/banned_businesses/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import BannedBusiness
from .schema import BannedBusinessSchema, BannedBusinessSchema

BASE_ROUTE = "banned_businesses"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as banned_business_api

    api.add_namespace(banned_business_api, path=f"/{root}/{BASE_ROUTE}")
