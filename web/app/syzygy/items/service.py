"""/web/app/syzygy/items/service.py

Author: Adam Green (adam.green1@maine.edu)

[Description]

Classes:

    [ClassesList]

Functions:

    [FunctionsList]

"""

import json
import logging
import os
import re
from collections import OrderedDict
from datetime import datetime
from typing import List

import werkzeug
from app import db
from libs.response import ErrResponse, NormalResponse

from ..categories.model import Category
from ..categories.service import CategoryService
from .model import Item
from .schema import ImageSchema

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
    def get_by_id(id: int) -> Item:
        """[summary]

        :param id: [description]
        :type id: int
        :return: [description]
        :rtype: [type]
        """
        return Item.query.get(id)

    @staticmethod
    def update(item: Item, updates: OrderedDict) -> Item:
        """[summary]

        :param item: [description]
        :type item: Item
        :param updates: [description]
        :type updates: OrderedDict
        :return: [description]
        :rtype: Item
        """

        ItemService.transform(updates)

        item.update(updates)
        item.modified_at(datetime.now())

        db.session.commit()
        return item

    @staticmethod
    def delete_by_id(id: int) -> List:
        """Deletes a item from the table with the specified id

        :param id: Item's id
        :type id: int
        :return: List containing the deleted item, if found, otherwise an empty
        list
        :rtype: List
        """

        item = ItemService.get_by_id(id)
        if not item:
            return []
        db.session.delete(item)
        db.session.commit()
        return [id]

    @staticmethod
    def create(new_attrs: OrderedDict) -> Item:
        """[summary]

        :param new_attrs: [description]
        :type new_attrs: OrderedDict
        :return: [description]
        :rtype: Item
        """

        print(type(new_attrs))

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

    @staticmethod
    def search(search_str: str):
        return Item.query.filter(Item.name.ilike("%search_str%")).all()

    @staticmethod
    def parse_images(base_path, request_files):
        image_schema = ImageSchema()
        file_ids = list(request_files)

        image_paths = []

        for file_id in file_ids:
            imagefile = request_files[file_id]
            filename = werkzeug.utils.secure_filename(imagefile.filename)

            path = os.path.join(base_path, filename)

            image_paths.append(path)

            imagefile.save(path)

        return image_paths

    @staticmethod
    def transform(attrs: dict) -> dict:
        return attrs
