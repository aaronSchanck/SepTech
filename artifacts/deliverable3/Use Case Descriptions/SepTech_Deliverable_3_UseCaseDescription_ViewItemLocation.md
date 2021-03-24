|Title |   View Item Location      |
|---------|---------|
|**ID**|    UC-TRK-1      |
|**Relevant User Stories**|    FR-TRK-1, FR-TRK-2, FR-TRK-3     |
|**Description**|     This use case describes the steps for viewing the current location of an order.      |
|**System Under Design**|    Tracking System     |
|**Primary Actor**|   General User   |
|**Participants**|    Admin User, Premium User, Non-Premium User    |
|**Goal**| Display the current location of an order.      |
|**Following Use Cases**|    None    |
|**Invariant**| None   |
|**Precondition**|  There must be an order that has been placed.         |
|**Success Postcondition**|    The system will display the current status and/or location of an order.        |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  The localUser clicks on the button to view their order.    |  1a. The localUser clicks on a notification of a tracking update. (Go to step 4)     |
| 2.  The system redirects the localUser to the page of an order.   |      |
| 3.   The localUser clicks on the button to view the tracking of an order.  |       |
| 4.   The system fetches the current and previous statuses of an order.  |     |
| 5.   The system returns a page displaying the current location, current status, and previous statuses of an order.  | 5a. The system displays that the current status of the item is not yet available.      |
