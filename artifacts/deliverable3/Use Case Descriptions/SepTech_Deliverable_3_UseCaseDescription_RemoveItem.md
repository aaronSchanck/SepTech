

|Title |   Remove Item     |
|---------|---------|
|**ID**|    UC-CART-2      |
|**Relevant User Stories**|    FR-CART-2, FR-CART-3, FR-CART-6, FR-CART-7     |
|**Description**|     This use case describes the process for removing an item from the user cart.      |
|**System Under Design**|     Centauri/Item Cart System        |
|**Primary Actor**|     General User.        |
|**Participants**|     Admin user, Premium User, Non-Premium User        |
|**Goal**|     To successfully remove an item from the user cart.        |
|**Following Use Cases**|     Add Item, checkout.       |
|**Invariant**|     No invariant.     |
|**Precondition**|     User is signed into a user account.       |
|**Success Postcondition**|     The system updates the current state of the user's cart.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. The user clicks on the Cart icon.      |        |
| 2. The system directs the user to their cart.     |    |
| 3. The user clicks on the quantity number of an item in their cart.     |  3a. The user clicks on the 'X' icon next to the item to remove an item. (Go to step 5.)       |
| 4. The user changes the quantity of the item to zero.     |         |
| 5. The system removes the item from their cart.     |         |
| 6. The system displays the updated cart upon the user refreshing or revisiting the cart.     |         |
