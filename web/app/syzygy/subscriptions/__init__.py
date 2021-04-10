"""/web/app/syzygy/subscriptions/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Subscription
from .schema import SubscriptionSchema

BASE_ROUTE = "subscriptions"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as subscription_api

    api.add_namespace(subscription_api, path=f"/{root}/{BASE_ROUTE}")
