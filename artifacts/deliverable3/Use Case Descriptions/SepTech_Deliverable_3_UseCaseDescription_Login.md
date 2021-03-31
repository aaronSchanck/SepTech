

|Title |   Login      |
|---------|---------|
|**ID**|    UC-ACC-3      |
|**Relevant User Stories**|    FR-ACC-1     |
|**Description**|     This use case describes the process of loging into a user account.       |
|**System Under Design**|   Centauri/Account System.       |
|**Primary Actor**|     General User        |
|**Participants**|     Administrative user, premium user, non-premium user, seller user.        |
|**Goal**|     To successfully login to a user account       |
|**Following Use Cases**|     Edit account info, upgrade account, view cart,  view item catalog, leave review, view bidding page, view purchase history, view recommendations, search function, chat function.       |
|**Invariant**|     Account being logged into exists.      |
|**Precondition**|     User selects "login" button.        |
|**Success Postcondition**|     System loads Centauri home page.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User enters username and password.     | 3a. Entered username or password do not match any reference in the account database.        |
| 2. User selects "login" button.     | 3b. System alerts user that username or password are incorrect.        |
| 3. System cross references entered username and password with account database     | 3c. System prompts user to enter a different username and password.        |
| 4. System loads Centauri home page     | 3d. User selects "forgot username or password.        |
| 5.      | 3e. System prompts user to enter connected email address.        |
| 6.      | 3f. User enters connected email address.        |
| 7.      | 3g. System sends user email to reset username or password.        |
| 8.      | 3h. User changes username or password.        |
