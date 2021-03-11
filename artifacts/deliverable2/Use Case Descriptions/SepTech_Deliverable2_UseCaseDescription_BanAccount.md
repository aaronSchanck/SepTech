|Title |   Ban Account      |
|---------|---------|
|**Description**|     This use case describes the steps banning an account that violates the Terms of Service.      |
|**System Under Design**|    Administration System     |
|**Primary Actor**|   Admin User   |
|**Participants**|    Offending User   |
|**Goal**| Remove user account from the system.      |
|**Following Use Cases**| Provide Reason for Ban       |
|**Invariant**| None   |
|**Precondition**|  The user must have administrative permissions.   |
|**Success Postcondition**| The account violating terms is barred access to the app.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  The admin user navigates to the profile of the offending user.     |  1a. The admin user views a report on an offending user.     |
| 2.  The system displays the page of the offending user.  |  1b. The admin clicks on the link to the offending user. (Go to step 2.)   |
| 3.  The admin user clicks on the button to ban the account. |      |
| 4.  The system directs the admin user to a page requesting reason for a ban. |      |
| 5.  The admin user enters information into the form. |      |
| 6.  The admin user clicks the button concluding the ban. |      |
| 7.  The system sends a notification to the offending user's email inbox indicating they have been banned. |      |
| 8.  The system displays a message indicating they have been banned upon an attempt to sign in by the offending user. |      |
| 9.  The system locks the account down. |      |



