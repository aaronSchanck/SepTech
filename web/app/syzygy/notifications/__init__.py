"""/web/app/syzygy/notifications/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import Notification
from .schema import NotificationSchema

BASE_ROUTE = "notifications"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as notification_api

    api.add_namespace(notification_api, path=f"/{root}/{BASE_ROUTE}")
