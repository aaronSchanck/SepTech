"""/web/app/syzygy/items/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

from app import db
from .model import Item
from ..categories.model import Category
from ..categories.service import CategoryService
from .interface import ItemInterface
from libs.response import ErrResponse, NormalResponse
import json
import logging
from datetime import datetime

import re

from typing import List

log = logging.getLogger(__name__)


class ItemService:
    @staticmethod
    def get_all():
        """[summary]

        :return: [description]
        :rtype: [type]
        """
        return Item.query.all()

    @staticmethod
    def get_by_id(itemid: int) -> Item:
        """[summary]

        :param itemid: [description]
        :type itemid: int
        :return: [description]
        :rtype: [type]
        """
        return Item.query.get(itemid)

    @staticmethod
    def get_by_name(item_name: str) -> Item:
        """[summary]

        :param item_name: [description]
        :type item_name: str
        :return: [description]
        :rtype: [type]
        """
        return Item.query.filter(Item.name == item_name).first()

    @staticmethod
    def update(item: Item, Item_change_updates: ItemInterface) -> Item:
        """[summary]

        :param item: The Item to update in the database
        :type item: Item
        :param Item_change_updates: Dictionary object containing the new changes
        to update the Item model object with
        :type Item_change_updates: ItemInterface
        :return: The updated Item model object
        :rtype: Item
        """
        item.update(Item_change_updates)
        db.session.commit()
        return item

    @staticmethod
    def delete_by_id(itemid: int) -> List:
        """Deletes a item from the table with the specified itemid

        :param itemid: Item's itemid
        :type itemid: int
        :return: List containing the deleted item, if found, otherwise an empty
        list
        :rtype: List
        """

        item = ItemService.get_by_id(itemid)
        if not item:
            return []
        db.session.delete(item)
        db.session.commit()
        return [itemid]

    @staticmethod
    def create(new_attrs: ItemInterface) -> Item:
        """Creates a item object from the ItemInterface TypedDict

        :param new_attrs: A dictionary with the input into a Item model
        :type new_attrs: ItemInterface
        :return: A new item object based on the input
        :rtype: Item
        """

        categories = new_attrs["category"]

        category = CategoryService.create_if_not_exists(categories)

        new_item = Item(
            name=new_attrs["name"],
            quantity=new_attrs["quantity"],
            created_at=datetime.now(),
            modified_at=datetime.now(),
            sellerid=new_attrs["sellerid"],
            price=new_attrs["price"],
            can_buy=new_attrs["can_buy"],
            can_bid=new_attrs["can_bid"],
            bidding_ends=new_attrs["bidding_ends"],
            quality=new_attrs["quality"],
            category_id=category.id,
            description=new_attrs["description"],
        )

        print(new_item)

        db.session.add(new_item)
        db.session.commit()

        return new_item

    def search(search_str: str):
        return Item.query.filter(Item.name.ilike("%search_str%")).all()

    def parse_images(request_files):
        image_data = image_schema.load(request_files.to_dict())