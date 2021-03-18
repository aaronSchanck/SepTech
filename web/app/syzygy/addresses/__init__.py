"""/web/app/syzygy/addresses/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Address
from .schema import AddressSchema

BASE_ROUTE = "addresses"

log = logging.getLogger(__name__)

def register_routes(api, app, root="api"):
    from .controller import api as address_api

    api.add_namespace(address_api, path=f"/{root}/{BASE_ROUTE}")
