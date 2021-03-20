"""/web/app/syzygy/address/interface.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import logging

from typing import TypedDict

log = logging.getLogger(__name__)


class AddressInterface(TypedDict, total=False):
    unit: str
    building: str
    street_name: str
    street_type: str
    city: str
    region: str
    country: str
    address_code: str
    postal_code: str
