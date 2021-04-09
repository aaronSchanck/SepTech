"""/web/app/syzygy/addresses/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
import re
from typing import List

from app import db
from lib import ErrResponse, NormalResponse

from .interface import AddressInterface
from .model import Address

log = logging.getLogger(__name__)


class AddressService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Address.query.all()

    @staticmethod
    def get_by_id(addressid: int) -> Address:
        """[summary]

        :param addressid: [description]
        :type addressid: int
        :return: [description]
        :rtype: [type]
        """
        return Address.query.get(addressid)

    @staticmethod
    def update(address: Address, address_changes: AddressInterface) -> Address:
        """[summary]

        :param address: The Address to update in the database
        :type address: Address
        :param Address_change_updates: Dictionary object containing the new changes
        to update the Address model object with
        :type Address_change_updates: AddressInterface
        :return: The updated Address model object
        :rtype: Address
        """
        address.update(address_changes)
        db.session.commit()
        return address

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a address from the table with the specified addressid

        :param addressid: Address's addressid
        :type addressid: int
        :return: List containing the deleted address, if found, otherwise an empty
        list
        :rtype: List
        """

        address = AddressService.get_by_id(id)
        if not address:
            return []
        db.session.delete(address)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: AddressInterface) -> Address:
        """Creates a address object from the AddressInterface TypedDict

        :param new_attrs: A dictionary with the input into a Address model
        :type new_attrs: AddressInterface
        :return: A new address object based on the input
        :rtype: Address
        """

        new_address = Address()

        db.session.add(new_address)
        db.session.commit()

        return new_address
