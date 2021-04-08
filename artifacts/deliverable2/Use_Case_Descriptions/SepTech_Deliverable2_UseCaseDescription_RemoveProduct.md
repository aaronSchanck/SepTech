|Title |   Remove Product      |
|---------|---------|
|**ID**|    UC-ADM-2      |
|**Relevant User Stories**|    FR-ADM-2     |
|**Description**|     This use case describes the steps to removing listings from the app.      |
|**System Under Design**|    Administration System     |
|**Primary Actor**|   Admin User   |
|**Participants**|  N/A     |
|**Goal**| Remove listing from the system.      |
|**Following Use Cases**| Provide Reason for Removal       |
|**Invariant**| None   |
|**Precondition**|  The localUser must have administrative permissions.   |
|**Success Postcondition**| The system no longer lists the item listing on the app.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  The admin localUser navigates to the profile of the seller.     |       |
| 2.  The system displays the seller profile.  |     |
| 3.  The admin localUser clicks on a button to remove products. |      |
| 4.  The system displays a list of current listings with the ability to check/uncheck listings. |      |
| 5.  The admin localUser checks on the items that they wish to remove. |      |
| 6.  The admin localUser clicks on a button confirming removal of the items. |      |
| 7.  The system directs the admin localUser to a form requesting information on reason for removal. |      |
| 8.  The admin localUser fills out the form. |      |
| 9.  The admin localUser clicks on a button submitting the form. |      |
| 10.  The system sends a notification to the seller's email inbox and app messages indicating listings have been removed. |      |
| 11.  The system deletes the listings from the database. |      |



