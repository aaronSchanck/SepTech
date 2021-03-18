"""/web/app/syzygy/items/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Furniture
from .schema import FurnitureSchema

from .. import BASE_ROUTE as BASE_ROUTE_ITEMS

BASE_ROUTE = "furniture"

log = logging.getLogger(__name__)

def register_routes(api, app, root="api"):
    from .controller import api as item_furniture_api

    api.add_namespace(item_furniture_api, path=f"/{root}/{BASE_ROUTE_ITEMS}/{BASE_ROUTE}")
