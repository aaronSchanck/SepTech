from datetime import datetime
import pandas as pd
import numpy as np
from flask_script import Command

from app import db
from app.syzygy.users import User


def seed_things():
    classes = [User]
    for klass in classes:
        # seed_thing(klass)
        pass

# def seed_thing(cls):
#     db.session.bulk_insert_mappings(cls, things)


class SeedCommand(Command):
    """ Seed the DB."""

    def run(self):
        if (
            input(
                "Are you sure you want to drop all tables and recreate? (y/N)\n"
            ).lower()
            == "y"
        ):
            print("Dropping tables...")
            db.drop_all()
            db.create_all()
            # seed_things()
            # db.session.commit()
            # print("DB successfully seeded.")
