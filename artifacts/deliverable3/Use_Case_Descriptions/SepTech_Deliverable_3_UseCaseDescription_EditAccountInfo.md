

|Title |   Edit Account Info      |
|---------|---------|
|**ID**|    UC-ACC-2      |
|**Relevant User Stories**|    FR-ACC-6, FR-ACC-7, FR-ACC-8, FR-ACC-9      |
|**Description**|     This use case describes the process of editing the information associated with a user account.       |
|**System Under Design**|     Centauri/Account System.        |
|**Primary Actor**|     General User        |
|**Participants**|     Administrative user, premium user, non-premium user, seller user.      |
|**Goal**|     To successfully edit the information associated with a user account.        |
|**Following Use Cases**|     Change Username, change password, change email        |
|**Invariant**|     No Invariant.      |
|**Precondition**|     User is signed into a valid account.       |
|**Success Postcondition**|     No postcondition.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "Edit account information" page.     | 3a. The inputed information matches an existing account.        |
| 2. System prompts user to enter a new username, password, or email.      | 3b. The system prompts the user to input a different username password or email.        |
| 3. User enters a new username, password, or email.     | 5a. The inputed information does not match the initial input.        |
| 4. System prompts user to re-enter the new username, password, or email.     | 5b. The system informs the user that the inputs do not match.        |
| 5. User re-enters new username, password, or email.     |         |
| 6. User selects the "confirm" button.     |         |
| 7. System saves new information to account database.     |         |
