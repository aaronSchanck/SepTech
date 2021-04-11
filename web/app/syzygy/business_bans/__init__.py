"""/web/app/syzygy/business_bans/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import BusinessBan
from .schema import BusinessBanSchema, BusinessBanSchema

BASE_ROUTE = "business_ban"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as business_ban_api

    api.add_namespace(business_ban_api, path=f"/{root}/{BASE_ROUTE}")
