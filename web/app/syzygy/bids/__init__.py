"""/web/app/syzygy/bids/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Bid
from .schema import BidSchema

BASE_ROUTE = "bids"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as bid_api

    api.add_namespace(bid_api, path=f"/{root}/{BASE_ROUTE}")
