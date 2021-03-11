|Title |   Filter Search      |
|---------|---------|
|**Description**|     This use case describes the steps for filtering through the results of a search.      |
|**System Under Design**|    Search System     |
|**Primary Actor**|   General User   |
|**Participants**|    Admin User, Premium User, Non-Premium User    |
|**Goal**| Display list of items relevant to the search after filtering.      |
|**Following Use Cases**| Filter by Rating, Filter by Price       |
|**Invariant**| None   |
|**Precondition**|  User must first initiate a search.        |
|**Success Postcondition**|  System will return a list of items that matches the search after filtering, or if nothing is left, system will return a message saying no items matched their search.          |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User enters keyword(s) into the search bar.      |  1a. User filters an existing search. (Go to Step 4)      |
| 2. The system fetches listings that contain the keyword(s) in the title, description, or tags.    | 2a. System is unable to fetch listings that match the search.         |
| 3. The system displays a list of items that matches the keyword(s).    | 2b. System returns a message stating No Items Found.        |
| 4. User selects desired filters for narrow down search.    |  |
| 5. User clicks the button to initiate a filtered search.    |  |
| 6. The system filters out listings that do not match the search.    | 6a. System filters out every listing.         |
| 7. The system displays a list of items that matches the search.    | 6b. System returns a message stating No Items Found.        |