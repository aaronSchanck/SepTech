"""/web/app/syzygy/banned_users/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import BannedUser
from .schema import BannedUserSchema, BannedUserSchema

BASE_ROUTE = "banned_user"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as banned_user_api

    api.add_namespace(banned_user_api, path=f"/{root}/{BASE_ROUTE}")
