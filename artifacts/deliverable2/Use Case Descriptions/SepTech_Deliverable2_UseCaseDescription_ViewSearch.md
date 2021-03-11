|Title |   View Search      |
|---------|---------|
|**Description**|     This use case describes the steps for viewing the results of a search.      |
|**System Under Design**|    Search System     |
|**Primary Actor**|   General User   |
|**Participants**|    Admin User, Premium User, Non-Premium User    |
|**Goal**| Display list of items relevant to the search.      |
|**Following Use Cases**| List by Popularity, List by Rating, List by Price, Filter Search       |
|**Invariant**| None   |
|**Precondition**|  At least one keyword must be entered into the search box.         |
|**Success Postcondition**|  System will return a list of items that matches the search, or if nothing matches, system will return a message saying no items matched their search.          |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User enters keyword(s) into the search bar.      |        |
| 2. The system fetches listings that contain the keyword(s) in the title, description, or tags.    | 2a. System is unable to fetch listings that match the search.         |
| 3. The system displays a list of items that matches the keyword(s).    | 2b. System returns a message stating No Items Found.        |
