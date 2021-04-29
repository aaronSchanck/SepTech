"""/web/app/syzygy/view_history/__init__.py

Author: Adam Green (adam.green1@maine.edu),
        Ashley Drexler (ashley.drexler@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import ViewHistory
from .schema import ViewHistorySchema

BASE_ROUTE = "view_history"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as view_history_api

    api.add_namespace(view_history_api, path=f"/{root}/{BASE_ROUTE}")
