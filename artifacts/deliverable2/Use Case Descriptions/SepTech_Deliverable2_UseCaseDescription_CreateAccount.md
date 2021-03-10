
 
|Title |   Create Account   |
|---------|---------|
|**Description**|     This use case describes the process of creating a user account.       |
|**System Under Design**|    Centauri/Signup System       |
|**Primary Actor**|     Unregistered User.        |
|**Participants**|     No Additional Participants.        |
|**Goal**|     The goal os to create a user account successfully.        |
|**Following Use Cases**|     login, logout, change account information        |
|**Invariant**|     No Invariant       |
|**Precondition**|     User Selects "create account" button       |
|**Success Postcondition**|     User is able to login successfully with new account.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User Selects "create account" button    | 5a. User chooses a username, password, or email that already exists for another account.        |
| 2. System displays dedicated account creation page    | 5b. System notifies user to select another username password, or email.        |
| 3. User chooses a standard account, a seller account or both     |         |
| 4. System requests the user to input a unique username, password, and email.    |         |
| 5. User Inputs a unique username, password, and email.     |         |
| 6. System sends email to the address listed by the user for confirmation.     |         |
| 7. User confirms account through email     |         |
