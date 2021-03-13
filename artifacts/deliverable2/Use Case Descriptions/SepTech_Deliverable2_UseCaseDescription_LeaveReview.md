

|Title |   Leave Review      |
|---------|---------|
|**ID**|    UC-REV-1      |
|**Relevant User Stories**|    FR-REV-1, FR-REV-2      |
|**Description**|     This use case describes the process for leaving a review on an item or seller.       |
|**System Under Design**|     Centauri/Review System        |
|**Primary Actor**|     General User.       |
|**Participants**|     Admin user, Premium User, Non-Premium User, Seller account.       |
|**Goal**|     To successfully leave a review on an item or a seller.        |
|**Following Use Cases**|     None.      |
|**Invariant**|     User is signed into a valid user account.       |
|**Precondition**|     User has interacted with the item or seller in question previously.     |
|**Success Postcondition**|     User successfully leaves a review on a item or seller.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "leave review" button.     | 3a. User chooses to leave review of a seller.        |
| 2. System prompts user to pick either an item review or a seller review.      | 3b. System displays the previous sellers that the user has bought from.        |
| 3. User chooses to leave a review of an item.     | 3c. User selects seller to review.        |
| 4. System displays the previous items that the user has bought.     | 3d. System prompts user to write a review title, seller rating, and the written review itself.        |
| 5. User selects item to review.     | 3e. User provides this information.         |
| 6. System prompts user to write a review title, product rating, and the written review itself.     | 3f. User selects the "confirm review" button.        |
| 7. User provides this information.        | 3g. System attaches the review to the selected seller.            |
| 8. User selects the "confirm review" button. | 7a. User does not fill out all 3 sections.        |
| 9. System attaches the review to the selected item.  | 7b. System prompts user to fill in remaining sections.           |

