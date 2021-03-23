"""/web/app/syzygy/images/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Image
from .schema import ImageSchema

BASE_ROUTE = "images"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as item_image_api

    api.add_namespace(item_image_api, path=f"/{root}/{BASE_ROUTE}")
