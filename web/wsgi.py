"""/web/wsgi.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import os
import logging

from app import create_app

from utils.logging import setup_logging
from utils.images import setup_image_store

env = os.getenv("FLASK_ENV") or "test"

setup_logging()

app = create_app(env)

host = "localhost" if env == "test" else "0.0.0.0"

if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
