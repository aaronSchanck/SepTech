

|Title |   Cancel Premium Subscription     |
|---------|---------|
|**ID**|    UC-PR-1      |
|**Relevant User Stories**|    FR-ACC-5, FR-PAY-1      |
|**Description**|     This use case describes the process of cancelling a premium subscription and downgrading to a regular account.       |
|**System Under Design**|     Centauri/Premium System        |
|**Primary Actor**|     Premium User.       |
|**Participants**|     None       |
|**Goal**|     To successfully downgrade a user account to a non-premium one.      |
|**Following Use Cases**|     None       |
|**Invariant**|     User has a valid user account.      |
|**Precondition**|     User is a premium member.      |
|**Success Postcondition**|     User becomes a regular user.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. The user selects "Cancel My Subscription" button.     |        |
| 2. The system directs the user to a new page to confirm if they are sure.     |    |
| 3. The user selects the option "Yes".     |  3a. The user selects the option "No".       |
| 4. The system downgrades the user to a regular account in the database.   | 3b.  The system directs the user back to their account settings.        |
| 5. The system notifies the user in app of when their membership access will officially terminate. |       |
| 6. The system sends the user an email notifying them that they have successfully unsubscribed. |        |