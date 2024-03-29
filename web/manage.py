"""/web/manage.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import os

from app import create_app, db
from flask_script import Manager
from commands.seed import SeedCommand
from app.images import clear_syzygy_image_store

env = os.getenv("FLASK_ENV") or "dev"
print(f"Active environment: * {env} *")
app = create_app(env)

manager = Manager(app)
app.app_context().push()
manager.add_command("seed_db", SeedCommand)


@manager.command
def init_db():
    print("Creating all resources.")
    db.create_all()


@manager.command
def drop_all():
    if input("Are you sure you want to drop all tables? (y/N)\n").lower() == "y":
        print("Dropping tables...")
        db.drop_all()

        clear_syzygy_image_store(os.path.join(os.getcwd(), "images"))


@manager.command
def run():
    app.run()


if __name__ == "__main__":
    manager.run()
