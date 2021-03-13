import logging

from mypy_extensions import TypedDict

log = logging.getLogger(__name__)


class UserInterface(TypedDict, total=False):
    userid: int
    username: str
    email: str
    password: str
    first_name: str
    last_name: str
    date_of_birth: str
    created_at: str
    modified_at: str
