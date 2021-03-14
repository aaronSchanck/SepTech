|Title |   Ban Account      |
|---------|---------|
|**ID**|    UC-ADM-1      |
|**Relevant User Stories**|    FR-ADM-1      |
|**Description**|     This use case describes the steps banning an account that violates the Terms of Service.      |
|**System Under Design**|    Administration System     |
|**Primary Actor**|   Admin User   |
|**Participants**|    Offending User   |
|**Goal**| Remove localUser account from the system.      |
|**Following Use Cases**| Provide Reason for Ban       |
|**Invariant**| None   |
|**Precondition**|  The localUser must have administrative permissions.   |
|**Success Postcondition**| The account violating terms is barred access to the app.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  The admin localUser navigates to the profile of the offending localUser.     |  1a. The admin localUser views a report on an offending localUser.     |
| 2.  The system displays the page of the offending localUser.  |  1b. The admin clicks on the link to the offending localUser. (Go to step 2.)   |
| 3.  The admin localUser clicks on the button to ban the account. |      |
| 4.  The system directs the admin localUser to a page requesting reason for a ban. |      |
| 5.  The admin localUser enters information into the form. |      |
| 6.  The admin localUser clicks the button concluding the ban. |      |
| 7.  The system sends a notification to the offending localUser's email inbox indicating they have been banned. |      |
| 8.  The system displays a message indicating they have been banned upon an attempt to sign in by the offending localUser. |      |
| 9.  The system locks the account down. |      |



