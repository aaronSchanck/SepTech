"""/web/app/syzygy/banned/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Banned
from .schema import BannedSchema, BannedSchema

BASE_ROUTE = "banned"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as banned_api

    api.add_namespace(banned_api, path=f"/{root}/{BASE_ROUTE}")
