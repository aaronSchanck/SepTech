import os

import logging

log = logging.getLogger(__name__)


def setup_image_store():
    directory = "images"
    path = os.path.join(os.getcwd(), directory)

    try:
        os.mkdir(path)
    except OSError:
        pass

    return path