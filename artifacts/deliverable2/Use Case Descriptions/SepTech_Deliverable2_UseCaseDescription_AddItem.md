

|Title |   Add Item     |
|---------|---------|
|**ID**|    UC-IC-1      |
|**Relevant User Stories**|    FR-CART-1, FR-CART-8      |
|**Description**|     This use case describes the process for adding an item to the localUser cart.      |
|**System Under Design**|     Centauri/Item Cart System        |
|**Primary Actor**|     General User.        |
|**Participants**|     Admin localUser, Premium User, Non-Premium User        |
|**Goal**|     To successfully add an item to the localUser cart.        |
|**Following Use Cases**|     None.       |
|**Invariant**|     No invariant.     |
|**Precondition**|     User is signed into a localUser account.       |
|**Success Postcondition**|     The system saves the item to the localUsers cart.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "add to cart" button on a purchasable item.      | 3a. Item is no longer in stock.        |
| 2. System directs localUser to their cart.     | 3b. System displays message to localUser that the item is no longer in stock.        |
| 3. System saves item in the localUser's cart.     |         |
