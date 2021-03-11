
|Title |   Logout      |
|---------|---------|
|**Description**|     This use case describes the process of logging out of a user account.       |
|**System Under Design**|     Centauri/Account System        |
|**Primary Actor**|     General User        |
|**Participants**|     Administrative user, premium user, non-premium user, seller user.        |
|**Goal**|     To successfully logout of a user profile.        |
|**Following Use Cases**|     No following use cases.       |
|**Invariant**|     No Invariant.       |
|**Precondition**|     User is signed into a user account.       |
|**Success Postcondition**|     User is successfully logged out of the user profile.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "logout" button.     | 3a. User selects "all connected devices".        |
| 2. System prompts user to log out of current device or all connected devices.     | 3b. System removes user access to the account from all connected devices.        |
| 3. User selects "current device".    |         |
| 4. System removes user access to the account from the current device.     |         |
