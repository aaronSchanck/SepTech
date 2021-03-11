|Title |   View Purchase History      |
|---------|---------|
|**Description**|     This use case describes the steps for viewing an account's purchase history.       |
|**System Under Design**|     Centauri/Purchase History System        |
|**Primary Actor**|      General User       |
|**Participants**|     Admin user, Premium User, Non-Premium User        |
|**Goal**|     Successfully display a list of all purchases made on the account.       |
|**Following Use Cases**|     Search History, Filter History, View Item       |
|**Invariant**|   The user must be logged in.         |
|**Precondition**|     The user must have an account in the system.       |
|**Success Postcondition**|     The system displays the list of all purchases made on the account, if any.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User clicks on the button to view their purchase history.      |        |
| 2. System directs them to the page with their purchases.     |         |
| 3. System displays list of purchases in order of most recent.    | 3a. System displays a message saying there has been no purchases made yet.        |
