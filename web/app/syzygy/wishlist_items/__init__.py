"""/web/app/syzygy/wishlist_items/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import WishlistItem
from .schema import WishlistItemSchema

BASE_ROUTE = "wishlist_items"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as wishlist_item_api

    api.add_namespace(wishlist_item_api, path=f"/{root}/{BASE_ROUTE}")
