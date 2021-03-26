import os
import logging

log = logging.getLogger(__name__)


def setup_syzygy_image_store(path):
    items_dir = "items"
    businesses_dir = "businesses"
    users_dir = "users"

    items_path = os.path.join(path, items_dir)
    businesses_path = os.path.join(path, businesses_dir)
    users_path = os.path.join(path, users_dir)

    try:
        os.mkdir(items_path)
        os.mkdir(businesses_path)
        os.mkdir(users_path)
    except OSError:
        pass
