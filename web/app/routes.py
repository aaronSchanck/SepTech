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

    from app.syzygy.items import register_routes as attach_items
    from app.syzygy.items.categories import register_routes as attach_item_categories

    # Add routes
    attach_users(api, app)

    attach_items(api, app)
    attach_item_categories(api, app)