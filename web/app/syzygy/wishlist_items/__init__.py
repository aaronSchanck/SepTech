"""/web/app/syzygy/orders/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Order
from .schema import OrderSchema

BASE_ROUTE = "orders"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as order_api

    api.add_namespace(order_api, path=f"/{root}/{BASE_ROUTE}")
