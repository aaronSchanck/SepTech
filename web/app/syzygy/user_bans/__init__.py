"""/web/app/syzygy/user_bans/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import UserBan
from .schema import UserBanSchema, UserBanSchema

BASE_ROUTE = "user_bans"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as user_bans_api

    api.add_namespace(banned_user_api, path=f"/{root}/{BASE_ROUTE}")
