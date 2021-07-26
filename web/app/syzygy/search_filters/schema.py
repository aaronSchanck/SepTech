"""/web/app/syzygy/search_filters/schema.py

Author: Adam Green (adam.green1@maine.edu)

Schemas for the SearchFilter model.
"""

from marshmallow import Schema, fields


class SearchFilterSchema(Schema):
    id = fields.Integer(dump_only=True)
