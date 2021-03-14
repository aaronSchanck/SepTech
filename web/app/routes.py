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
    from app.syzygy.localUsers import register_routes as attach_users

    # Add routes
    attach_users(api, app)
