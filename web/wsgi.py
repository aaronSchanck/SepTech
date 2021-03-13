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

log = logging.getLogger(__name__)

app = create_app(os.getenv("FLASK_ENV") or "test")

host = "localhost" if app == "test" else "0.0.0.0"

if __name__ == "__main__":
    with setup_logging():
        app.run(host="localhost", debug=True)
