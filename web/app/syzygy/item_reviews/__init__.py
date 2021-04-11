"""/web/app/syzygy/item_reviews/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import ItemReview
from .schema import ItemReviewSchema

BASE_ROUTE = "item_reviews"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as item_review_api

    api.add_namespace(item_review_api, path=f"/{root}/{BASE_ROUTE}")
