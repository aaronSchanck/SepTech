"""/web/app/syzygy/users/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import User
from .schema import UserReceiveSchema, UserSendSchema

BASE_ROUTE = "users"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as user_api

    api.add_namespace(user_api, path=f"/{root}/{BASE_ROUTE}")
