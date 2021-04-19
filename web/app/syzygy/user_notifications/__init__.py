"""/web/app/syzygy/user_notifications/__init__.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from .model import UserNotification
from .schema import UserNotificationSchema

BASE_ROUTE = "user_notifications"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as user_notification_api

    api.add_namespace(user_notification_api, path=f"/{root}/{BASE_ROUTE}")
