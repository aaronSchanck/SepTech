import os

from app import create_app, db
from flask_script import Manager

env = os.getenv("FLASK_ENV") or "test"
print(f"Active environment: * {env} *")
app = create_app(env)

manager = Manager(app)
app.app_context().push()
# manager.add_command("seed_db", SeedCommand)


@manager.command
def run():
    app.run()

if __name__ == "__main__":
    manager.run()