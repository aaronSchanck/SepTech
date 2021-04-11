"""/web/app/syzygy/business_reviews/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import BusinessReview
from .schema import BusinessReviewSchema

BASE_ROUTE = "business_reviews"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as business_review_api

    api.add_namespace(business_review_api, path=f"/{root}/{BASE_ROUTE}")
