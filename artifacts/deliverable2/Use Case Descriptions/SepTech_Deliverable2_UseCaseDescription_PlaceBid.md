

|Title |   Place Bid      |
|---------|---------|
|**ID**|    UC-BID-1      |
|**Relevant User Stories**|    FR-BID-1     |
|**Description**|     This use case describes the process of placing a bid on a biddable item.      |
|**System Under Design**|     Centauri/Bidding System        |
|**Primary Actor**|     General User       |
|**Participants**|     Admin User, Premium User, Non-Premium User, Seller.        |
|**Goal**|     To successfully place a bid on a biddable item.       |
|**Following Use Cases**|     None       |
|**Invariant**|     User is signed into a valid user account.      |
|**Precondition**|     User views the item in the bidding menu.       |
|**Success Postcondition**|     User successfully places a bid on an item.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. System displays the available items to bid on.     | 5a. Another user places a higher bid         |
| 2. User selects the item to bid on.     | 5b. User has the option to place another bid.        |
| 3. System displays the current highest bid, time left to bid, and the number of bidders.     | 5c. System resets the timer back to one minute if a bid is made within one minute from t he time limit.        |
| 4. User places bid at a minimal interval set by the seller.     |         |
| 5. User wins at the end of the time limit with the highest bid.  |       |
