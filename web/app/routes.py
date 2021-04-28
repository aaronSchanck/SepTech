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
    from app.syzygy.addresses import register_routes as attach_addresses
    from app.syzygy.bids import register_routes as attach_bids
    from app.syzygy.business_bans import register_routes as attach_business_bans
    from app.syzygy.business_reports import register_routes as attach_business_reports
    from app.syzygy.businesses import register_routes as attach_businesses
    from app.syzygy.categories import register_routes as attach_categories
    from app.syzygy.item_reports import register_routes as attach_item_reports
    from app.syzygy.item_reviews import register_routes as attach_item_reviews
    from app.syzygy.items import register_routes as attach_items
    from app.syzygy.order_items import register_routes as attach_order_items
    from app.syzygy.orders import register_routes as attach_orders
    from app.syzygy.removed_items import register_routes as attach_removed_items
    from app.syzygy.sessions import register_routes as attach_sessions
    from app.syzygy.user_bans import register_routes as attach_user_bans
    from app.syzygy.user_reports import register_routes as attach_user_reports
    from app.syzygy.users import register_routes as attach_users
    from app.syzygy.wishlist import register_routes as attach_wishlist
    from app.syzygy.wishlist_items import register_routes as attach_wishlist_items

    # Add routes
    attach_addresses(api, app)
    attach_bids(api, app)
    attach_business_bans(api, app)
    attach_business_reports(api, app)
    attach_businesses(api, app)
    attach_categories(api, app)
    attach_item_reports(api, app)
    attach_item_reviews(api, app)
    attach_items(api, app)
    attach_order_items(api, app)
    attach_orders(api, app)
    attach_removed_items(api, app)
    attach_sessions(api, app)
    attach_user_bans(api, app)
    attach_user_reports(api, app)
    attach_users(api, app)
    attach_wishlist(api, app)
    attach_wishlist_items(api, app)