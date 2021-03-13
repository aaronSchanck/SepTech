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

env = os.getenv("FLASK_ENV") or "test"

app = create_app(env)

setup_logging()

host = "localhost" if env == "test" else "0.0.0.0"

if __name__ == "__main__":
    app.run(host="0.0.0.0", debug=True)
