"""/web/app/syzygy/wishlist/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Wishlist
from .schema import WishlistSchema

BASE_ROUTE = "wishlist"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as wishlist_api

    api.add_namespace(wishlist_api, path=f"/{root}/{BASE_ROUTE}")
