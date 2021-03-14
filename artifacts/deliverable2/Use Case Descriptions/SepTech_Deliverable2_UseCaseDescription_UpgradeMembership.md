

|Title |   Upgrade Membership     |
|---------|---------|
|**ID**|    UC-PR-2      |
|**Relevant User Stories**|    FR-PAY-1, FR-ACC-5    |
|**Description**|     This use case describes the process of upgrading a localUser account to a premium account.       |
|**System Under Design**|     Centauri/Premium System        |
|**Primary Actor**|     Non-Premium User.       |
|**Participants**|     None       |
|**Goal**|     To successfully upgrade a localUser account to a premium one.      |
|**Following Use Cases**|     More accurate search functions, higher customer service priority, faster shipping options, cancel subscription.       |
|**Invariant**|     User has a valid localUser account.      |
|**Precondition**|     User is not already a premium member.      |
|**Success Postcondition**|     User becomes a premium localUser.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "upgrade account" button.     | 4a. User has not saved a payment method.        |
| 2. System loads upgrade account page.     | 4b. System prompts localUser to enter payment information.        |
| 3. System prompts localUser to select payment method.     |         |
| 4. User selects saved payment method.   |          |
| 5. System now designates account as premium account. |       |
| 6. System sends localUser a billing statement to their email. |        |