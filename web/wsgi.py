import os

from app import create_app

app = create_app(os.getenv("FLASK_ENV") or "test")

host = "localhost" if app=="test" else "0.0.0.0"

if __name__ == "__main__":
    app.run(host = "localhost", debug=True)