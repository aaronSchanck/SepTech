"""/web/app/syzygy/categories/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Category
from .schema import CategorySchema

BASE_ROUTE = "categories"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as category_api

    api.add_namespace(category_api, path=f"/{root}/{BASE_ROUTE}")
