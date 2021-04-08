

|Title |   Enter New Payment Method     |
|---------|---------|
|**ID**|    UC-PAY-1      |
|**Relevant User Stories**|    FR-PAY-3      |
|**Description**|     This use case describes the process of entering a new payment method.      |
|**System Under Design**|     Centauri/Payment Methods System.        |
|**Primary Actor**|     	General User.       |
|**Participants**|     	Administrative localUser, premium localUser, non-premium localUser, seller localUser.       |
|**Goal**|     To successfully enter a new payment method into the system.       |
|**Following Use Cases**|     Save Payment Method, Saved Payment Notification        |
|**Invariant**|     None       |
|**Precondition**|     User must be checking out or localUser must have a registered account.       |
|**Success Postcondition**|     Payment method is saved temporarily for the current order, or if entering the new payment in settings using a registered account, payment is saved to the account.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  User selects the button to enter a new payment method.    |         |
| 2.  The system opens an input box for localUser to input their new payment method.    |         |
| 3.  The localUser types in their credit card information.    |         |
| 4.  The localUser clicks on a button to confirm their payment details.   |         |
| 5.  The system confirms that the payment method is valid.   | 5a. The system can not confirm that the payment method is valid.        |
| 6.  The system temporarily saves the payment method for the current order.   | 5b. The system prompts the localUser to enter the correct payment details.        |
| | 6a. The system saves the payment method to the registered account, if the payment method is entered in the account payment settings.        |

