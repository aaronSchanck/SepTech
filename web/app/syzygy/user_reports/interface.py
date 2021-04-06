"""/web/app/syzygy/user_reports/interface.py

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


class UserReportInterface(TypedDict, total=False):
    id: int
    reporter_user_id: int
    reported_user_id: int

    report_reason: str
    report_comment: str

    created_at: str

    # admin stuff
    reviewed_by_id: int
    reviewed_by_name: str
    reviewed_at: str