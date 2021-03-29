"""/web/app/syzygy/routes.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from app.syzygy.users import register_routes as attach_users
    from app.syzygy.businesses import register_routes as attach_businesses
    from app.syzygy.addresses import register_routes as attach_addresses
    from app.syzygy.banned import register_routes as attach_banned

    from app.syzygy.items import register_routes as attach_items
    from app.syzygy.removed_items import register_routes as attach_removed_items
    from app.syzygy.categories import register_routes as attach_item_categories

    from app.syzygy.order_items import register_routes as attach_order_items
    from app.syzygy.orders import register_routes as attach_orders

    # Add routes
    attach_users(api, app)
    attach_businesses(api, app)

    attach_addresses(api, app)
    attach_banned(api, app)

    attach_items(api, app)
    attach_item_categories(api, app)
    attach_removed_items(api, app)

    attach_order_items(api, app)
    attach_orders(api, app)