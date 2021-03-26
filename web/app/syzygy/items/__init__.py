"""/web/app/syzygy/items/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Item
from .schema import ItemSchema

BASE_ROUTE = "items"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as item_api

    api.add_namespace(item_api, path=f"/{root}/{BASE_ROUTE}")