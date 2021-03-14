
|Title |   Logout      |
|---------|---------|
|**ID**|    UC-ACC-4      |
|**Relevant User Stories**|    FR-ACC-2, FR-ACC-3     |
|**Description**|     This use case describes the process of logging out of a localUser account.       |
|**System Under Design**|     Centauri/Account System        |
|**Primary Actor**|     General User        |
|**Participants**|     Administrative localUser, premium localUser, non-premium localUser, seller localUser.        |
|**Goal**|     To successfully logout of a localUser profile.        |
|**Following Use Cases**|     No following use cases.       |
|**Invariant**|     No Invariant.       |
|**Precondition**|     User is signed into a localUser account.       |
|**Success Postcondition**|     User is successfully logged out of the localUser profile.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "logout" button.     | 3a. User selects "all connected devices".        |
| 2. System prompts localUser to log out of current device or all connected devices.     | 3b. System removes localUser access to the account from all connected devices.        |
| 3. User selects "current device".    |         |
| 4. System removes localUser access to the account from the current device.     |         |
