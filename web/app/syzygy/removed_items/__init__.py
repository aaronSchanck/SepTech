"""/web/app/syzygy/removed_items/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import RemovedItem
from .schema import RemovedItemSchema

BASE_ROUTE = "removed_items"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as removed_item_api

    api.add_namespace(removed_item_api, path=f"/{root}/{BASE_ROUTE}")
