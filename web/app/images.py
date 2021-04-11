import os
import logging
import shutil

log = logging.getLogger(__name__)


def setup_syzygy_image_store(path):
    items_dir = "items"
    businesses_dir = "businesses"

    items_path = os.path.join(path, items_dir)
    businesses_path = os.path.join(path, businesses_dir)

    try:
        os.mkdir(items_path)
        os.mkdir(businesses_path)
    except OSError:
        pass


def clear_syzygy_image_store(path):
    items_dir = "items"
    businesses_dir = "businesses"

    items_path = os.path.join(path, items_dir)
    businesses_path = os.path.join(path, businesses_dir)
    users_path = os.path.join(path, users_dir)

    try:
        shutil.rmtree(items_path)
        shutil.rmtree(businesses_path)
        shutil.rmtree(users_path)
    except Exception:
        pass