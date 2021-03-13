import logging

log = logging.getLogger(__name__)


def register_routes(api, app, root="api"):
    from app.syzygy.users import register_routes as attach_users

    # Add routes
    attach_users(api, app)
