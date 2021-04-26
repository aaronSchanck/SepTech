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

from libs.logging import setup_logging
from libs.images import setup_image_store

env = os.getenv("FLASK_ENV") or "dev"

setup_logging()

app = create_app(env)

host = "localhost" if env == "dev" else "0.0.0.0"

if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
