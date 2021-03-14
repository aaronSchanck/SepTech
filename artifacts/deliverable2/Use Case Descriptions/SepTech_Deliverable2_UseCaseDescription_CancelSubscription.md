

|Title |   Cancel Premium Subscription     |
|---------|---------|
|**ID**|    UC-PR-1      |
|**Relevant User Stories**|    FR-ACC-5, FR-PAY-1      |
|**Description**|     This use case describes the process of cancelling a premium subscription and downgrading to a regular account.       |
|**System Under Design**|     Centauri/Premium System        |
|**Primary Actor**|     Premium User.       |
|**Participants**|     None       |
|**Goal**|     To successfully downgrade a localUser account to a non-premium one.      |
|**Following Use Cases**|     None       |
|**Invariant**|     User has a valid localUser account.      |
|**Precondition**|     User is a premium member.      |
|**Success Postcondition**|     User becomes a regular localUser.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. The localUser selects "Cancel My Subscription" button.     |        |
| 2. The system directs the localUser to a new page to confirm if they are sure.     |    |
| 3. The localUser selects the option "Yes".     |  3a. The localUser selects the option "No".       |
| 4. The system downgrades the localUser to a regular account in the database.   | 3b.  The system directs the localUser back to their account settings.        |
| 5. The system notifies the localUser in app of when their membership access will officially terminate. |       |
| 6. The system sends the localUser an email notifying them that they have successfully unsubscribed. |        |