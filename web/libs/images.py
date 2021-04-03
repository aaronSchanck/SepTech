import os

import logging

from flask_uploads import UploadSet, IMAGES

log = logging.getLogger(__name__)

IMAGE_SET = UploadSet("images", IMAGES)


def setup_image_store():
    directory = "images"
    path = os.path.join(os.getcwd(), directory)

    try:
        os.mkdir(path)
    except OSError:
        pass

    return path