

|Title |   Add Item     |
|---------|---------|
|**ID**|    UC-IC-1      |
|**Relevant User Stories**|    FR-CART-1, FR-CART-8      |
|**Description**|     This use case describes the process for adding an item to the user cart.      |
|**System Under Design**|     Centauri/Item Cart System        |
|**Primary Actor**|     General User.        |
|**Participants**|     Admin user, Premium User, Non-Premium User        |
|**Goal**|     To successfully add an item to the user cart.        |
|**Following Use Cases**|     Cart Update Save (Add Item part of Update Cart generalization).     |
|**Invariant**|     User is in cart view.     |
|**Precondition**|     User is signed into a user account.       |
|**Success Postcondition**|     The system saves the item to the users cart.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "add to cart" button on a purchasable item.      | 3a. Item is no longer in stock.        |
| 2. System directs user to their cart.     | 3b. System displays message to user that the item is no longer in stock.        |
| 3. System saves item in the user's cart.     |         |
