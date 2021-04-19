"""/web/app/syzygy/business_notifications/__init__.py

Author: Adam Green (adam.green1@maine.edu)

"""

import logging

from .model import BusinessNotification
from .schema import BusinessNotificationSchema

BASE_ROUTE = "business_notifications"

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from .controller import api as business_notification_api

    api.add_namespace(business_notification_api, path=f"/{root}/{BASE_ROUTE}")
