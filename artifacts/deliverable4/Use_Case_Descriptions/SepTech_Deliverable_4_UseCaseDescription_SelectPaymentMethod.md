|Title |   Select Payment Method     |
|---------|---------|
|**ID**|    UC-PAY-2      |
|**Relevant User Stories**|    FR-PAY-2     |
|**Description**|     This use case describes the process of selecting a payment method      |
|**System Under Design**|     Centauri/Payment Methods System.        |
|**Primary Actor**|     	General User.       |
|**Participants**|     	Administrative user, premium user, non-premium user, seller user, guest user.       |
|**Goal**|     To successfully select a payment method for an order.      |
|**Following Use Cases**|     Display Order Review, Payment Confirmation        |
|**Invariant**|     None       |
|**Precondition**|     The user must be checking out.       |
|**Success Postcondition**|     The user is able to pay with the payment method.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. The system displays the possible payment methods for the user.     |         |
| 2.  The user clicks on a saved payment method.    | 2a. The user inputs a new payment method.        |
| 3.  The user checks out with the selected payment method.    | 2b. The user clicks on a button to confirm the new payment method is to be used.        |
|     | 2c. The system temporarily saves the payment method for the current order. (Go to step 3.)        |
