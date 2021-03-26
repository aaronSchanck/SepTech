"""/web/app/syzygy/orderdetails/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import User
from .schema import UserSchema

BASE_ROUTE = "orderdetails"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as orderdetails_api

    api.add_namespace(orderdetails_api, path=f"/{root}/{BASE_ROUTE}")
