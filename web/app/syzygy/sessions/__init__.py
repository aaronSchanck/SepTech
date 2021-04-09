"""/web/app/syzygy/sessions/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Session
from .schema import SessionSchema, SessionSchema

BASE_ROUTE = "sessions"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as session_api

    api.add_namespace(session_api, path=f"/{root}/{BASE_ROUTE}")
