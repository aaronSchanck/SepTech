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


---

|Title |   Ban Account      |
|---------|---------|
|**ID**|    UC-ADM-1      |
|**Relevant User Stories**|    FR-ADM-1      |
|**Description**|     This use case describes the steps banning an account that violates the Terms of Service.      |
|**System Under Design**|    Administration System     |
|**Primary Actor**|   Admin User   |
|**Participants**|    Offending User   |
|**Goal**| Remove localUser account from the system.      |
|**Following Use Cases**| Provide Reason for Ban       |
|**Invariant**| None   |
|**Precondition**|  The localUser must have administrative permissions.   |
|**Success Postcondition**| The account violating terms is barred access to the app.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  The admin localUser navigates to the profile of the offending localUser.     |  1a. The admin localUser views a report on an offending localUser.     |
| 2.  The system displays the page of the offending localUser.  |  1b. The admin clicks on the link to the offending localUser. (Go to step 2.)   |
| 3.  The admin localUser clicks on the button to ban the account. |      |
| 4.  The system directs the admin localUser to a page requesting reason for a ban. |      |
| 5.  The admin localUser enters information into the form. |      |
| 6.  The admin localUser clicks the button concluding the ban. |      |
| 7.  The system sends a notification to the offending localUser's email inbox indicating they have been banned. |      |
| 8.  The system displays a message indicating they have been banned upon an attempt to sign in by the offending localUser. |      |
| 9.  The system locks the account down. |      |

---

|Title |   Cancel Premium Subscription     |
|---------|---------|
|**ID**|    UC-PR-1      |
|**Relevant User Stories**|    FR-ACC-5, FR-PAY-1      |
|**Description**|     This use case describes the process of cancelling a premium subscription and downgrading to a regular account.       |
|**System Under Design**|     Centauri/Premium System        |
|**Primary Actor**|     Premium User.       |
|**Participants**|     None       |
|**Goal**|     To successfully downgrade a localUser account to a non-premium one.      |
|**Following Use Cases**|     None       |
|**Invariant**|     User has a valid localUser account.      |
|**Precondition**|     User is a premium member.      |
|**Success Postcondition**|     User becomes a regular localUser.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. The localUser selects "Cancel My Subscription" button.     |        |
| 2. The system directs the localUser to a new page to confirm if they are sure.     |    |
| 3. The localUser selects the option "Yes".     |  3a. The localUser selects the option "No".       |
| 4. The system downgrades the localUser to a regular account in the database.   | 3b.  The system directs the localUser back to their account settings.        |
| 5. The system notifies the localUser in app of when their membership access will officially terminate. |       |
| 6. The system sends the localUser an email notifying them that they have successfully unsubscribed. |        |

---

|Title |   Create Account   |
|---------|---------|
|**ID**|    UC-ACC-1      |
|**Relevant User Stories**|    FR-ACC-4     |
|**Description**|     This use case describes the process of creating a localUser account.       |
|**System Under Design**|    Centauri/Signup System       |
|**Primary Actor**|     Unregistered User.        |
|**Participants**|     No Additional Participants.        |
|**Goal**|     The goal is to create a localUser account successfully.        |
|**Following Use Cases**|     login        |
|**Invariant**|     No Invariant       |
|**Precondition**|     User Selects "create account" button       |
|**Success Postcondition**|     User is able to login successfully with new account.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User Selects "create account" button    | 5a. User chooses a username, password, or email that already exists for another account.        |
| 2. System displays dedicated account creation page    | 5b. System notifies localUser to select another username password, or email.        |
| 3. User chooses a standard account, a seller account, or both     |         |
| 4. System requests the localUser to input a unique username, password, and email.    |         |
| 5. User Inputs a unique username, password, and email.     |         |
| 6. System sends email to the address listed by the localUser for confirmation.     |         |
| 7. User confirms account through email     |         |

---


|Title |   Edit Account Info      |
|---------|---------|
|**ID**|    UC-ACC-2      |
|**Relevant User Stories**|    FR-ACC-6, FR-ACC-7, FR-ACC-8, FR-ACC-9      |
|**Description**|     This use case describes the process of editing the information associated with a localUser account.       |
|**System Under Design**|     Centauri/Account System.        |
|**Primary Actor**|     General User        |
|**Participants**|     Administrative localUser, premium localUser, non-premium localUser, seller localUser.      |
|**Goal**|     To successfully edit the information associated with a localUser account.        |
|**Following Use Cases**|     Change Username, change password, change email        |
|**Invariant**|     No Invariant.      |
|**Precondition**|     User is signed into a valid account.       |
|**Success Postcondition**|     No postcondition.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "Edit account information" page.     | 3a. The inputed information matches an existing account.        |
| 2. System prompts localUser to enter a new username, password, or email.      | 3b. The system prompts the localUser to input a different username password or email.        |
| 3. User enters a new username, password, or email.     | 5a. The inputed information does not match the initial input.        |
| 4. System prompts localUser to re-enter the new username, password, or email.     | 5b. The system informs the localUser that the inputs do not match.        |
| 5. User re-enters new username, password, or email.     |         |
| 6. User selects the "confirm" button.     |         |
| 7. System saves new information to account database.     |         |

---


|Title |   Enter Item Page     |
|---------|---------|
|**ID**|    UC-CAT-1      |
|**Relevant User Stories**|    FR-CAT-1, FR-CAT-2, FR-CAT-3      |
|**Description**|     This use case describes the process opening navigating to the item information page.     |
|**System Under Design**|     Centauri/Item Catalog System       |
|**Primary Actor**|     General User        |
|**Participants**|     Admin User, Premium User, Non-Premium User, Seller.       |
|**Goal**|     To successfully display the contents of the item information page.       |
|**Following Use Cases**|     Add to cart, Enter Seller Page.       |
|**Invariant**|     None       |
|**Precondition**|     User must view item in the item catalog.     |
|**Success Postcondition**|     System will display the contents of the item information page.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User views item in the item catalog.     |         |
| 2. System highlights the item, allowing localUser to click on it.     |         |
| 3. User clicks on item.     |         |
| 4. System brings localUser to the item page.     |         |

---



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


---

|Title |   Filter Search      |
|---------|---------|
|**ID**|    UC-SER-1      |
|**Relevant User Stories**|    FR-SER-6    |
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


---



|Title |   Leave Review      |
|---------|---------|
|**ID**|    UC-REV-1      |
|**Relevant User Stories**|    FR-REV-1, FR-REV-2      |
|**Description**|     This use case describes the process for leaving a review on an item or seller.       |
|**System Under Design**|     Centauri/Review System        |
|**Primary Actor**|     General User.       |
|**Participants**|     Admin localUser, Premium User, Non-Premium User, Seller account.       |
|**Goal**|     To successfully leave a review on an item or a seller.        |
|**Following Use Cases**|     None.      |
|**Invariant**|     User is signed into a valid localUser account.       |
|**Precondition**|     User has interacted with the item or seller in question previously.     |
|**Success Postcondition**|     User successfully leaves a review on a item or seller.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "leave review" button.     | 3a. User chooses to leave review of a seller.        |
| 2. System prompts localUser to pick either an item review or a seller review.      | 3b. System displays the previous sellers that the localUser has bought from.        |
| 3. User chooses to leave a review of an item.     | 3c. User selects seller to review.        |
| 4. System displays the previous items that the localUser has bought.     | 3d. System prompts localUser to write a review title, seller rating, and the written review itself.        |
| 5. User selects item to review.     | 3e. User provides this information.         |
| 6. System prompts localUser to write a review title, product rating, and the written review itself.     | 3f. User selects the "confirm review" button.        |
| 7. User provides this information.        | 3g. System attaches the review to the selected seller.            |
| 8. User selects the "confirm review" button. | 7a. User does not fill out all 3 sections.        |
| 9. System attaches the review to the selected item.  | 7b. System prompts localUser to fill in remaining sections.           |


---



|Title |   Login      |
|---------|---------|
|**ID**|    UC-ACC-3      |
|**Relevant User Stories**|    FR-ACC-1     |
|**Description**|     This use case describes the process of loging into a localUser account.       |
|**System Under Design**|   Centauri/Account System.       |
|**Primary Actor**|     General User        |
|**Participants**|     Administrative localUser, premium localUser, non-premium localUser, seller localUser.        |
|**Goal**|     To successfully login to a localUser account       |
|**Following Use Cases**|     Edit account info, upgrade account, view cart,  view item catalog, leave review, view bidding page, view purchase history, view recommendations, search function, chat function.       |
|**Invariant**|     Account being logged into exists.      |
|**Precondition**|     User selects "login" button.        |
|**Success Postcondition**|     System loads Centauri home page.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User enters username and password.     | 3a. Entered username or password do not match any reference in the account database.        |
| 2. User selects "login" button.     | 3b. System alerts localUser that username or password are incorrect.        |
| 3. System cross references entered username and password with account database     | 3c. System prompts localUser to enter a different username and password.        |
| 4. System loads Centauri home page     | 3d. User selects "forgot username or password.        |
| 5.      | 3e. System prompts localUser to enter connected email address.        |
| 6.      | 3f. User enters connected email address.        |
| 7.      | 3g. System sends localUser email to reset username or password.        |
| 8.      | 3h. User changes username or password.        |


---


|Title |   Logout      |
|---------|---------|
|**ID**|    UC-ACC-4      |
|**Relevant User Stories**|    FR-ACC-2, FR-ACC-3     |
|**Description**|     This use case describes the process of logging out of a localUser account.       |
|**System Under Design**|     Centauri/Account System        |
|**Primary Actor**|     General User        |
|**Participants**|     Administrative localUser, premium localUser, non-premium localUser, seller localUser.        |
|**Goal**|     To successfully logout of a localUser profile.        |
|**Following Use Cases**|     No following use cases.       |
|**Invariant**|     No Invariant.       |
|**Precondition**|     User is signed into a localUser account.       |
|**Success Postcondition**|     User is successfully logged out of the localUser profile.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "logout" button.     | 3a. User selects "all connected devices".        |
| 2. System prompts localUser to log out of current device or all connected devices.     | 3b. System removes localUser access to the account from all connected devices.        |
| 3. User selects "current device".    |         |
| 4. System removes localUser access to the account from the current device.     |         |

---



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
|**Invariant**|     User is signed into a valid localUser account.      |
|**Precondition**|     User views the item in the bidding menu.       |
|**Success Postcondition**|     User successfully places a bid on an item.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects the item to bid on.     | 5a. Another localUser places a higher bid         |
| 2. System displays the current highest bid, time left to bid, and the number of bidders.     | 5b. User has the option to place another bid.        |
| 3. User places bid at a minimal interval set by the seller.     | 5c. System resets the timer back to one minute if a bid is made within one minute from t he time limit.        |
| 4. User wins at the end of the time limit with the highest bid.     |         |

---


|Title |   Remove Item     |
|---------|---------|
|**ID**|    UC-CART-2      |
|**Relevant User Stories**|    FR-CART-2, FR-CART-3, FR-CART-6, FR-CART-7     |
|**Description**|     This use case describes the process for removing an item from the localUser cart.      |
|**System Under Design**|     Centauri/Item Cart System        |
|**Primary Actor**|     General User.        |
|**Participants**|     Admin localUser, Premium User, Non-Premium User        |
|**Goal**|     To successfully remove an item from the localUser cart.        |
|**Following Use Cases**|     Add Item, checkout.       |
|**Invariant**|     No invariant.     |
|**Precondition**|     User is signed into a localUser account.       |
|**Success Postcondition**|     The system updates the current state of the localUser's cart.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. The localUser clicks on the Cart icon.      |        |
| 2. The system directs the localUser to their cart.     |    |
| 3. The localUser clicks on the quantity number of an item in their cart.     |  3a. The localUser clicks on the 'X' icon next to the item to remove an item. (Go to step 5.)       |
| 4. The localUser changes the quantity of the item to zero.     |         |
| 5. The system removes the item from their cart.     |         |
| 6. The system displays the updated cart upon the localUser refreshing or revisiting the cart.     |         |

---

|Title |   Remove Product      |
|---------|---------|
|**ID**|    UC-ADM-2      |
|**Relevant User Stories**|    FR-ADM-2     |
|**Description**|     This use case describes the steps to removing listings from the app.      |
|**System Under Design**|    Administration System     |
|**Primary Actor**|   Admin User   |
|**Participants**|  N/A     |
|**Goal**| Remove listing from the system.      |
|**Following Use Cases**| Provide Reason for Removal       |
|**Invariant**| None   |
|**Precondition**|  The localUser must have administrative permissions.   |
|**Success Postcondition**| The system no longer lists the item listing on the app.     |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  The admin localUser navigates to the profile of the seller.     |       |
| 2.  The system displays the seller profile.  |     |
| 3.  The admin localUser clicks on a button to remove products. |      |
| 4.  The system displays a list of current listings with the ability to check/uncheck listings. |      |
| 5.  The admin localUser checks on the items that they wish to remove. |      |
| 6.  The admin localUser clicks on a button confirming removal of the items. |      |
| 7.  The system directs the admin localUser to a form requesting information on reason for removal. |      |
| 8.  The admin localUser fills out the form. |      |
| 9.  The admin localUser clicks on a button submitting the form. |      |
| 10.  The system sends a notification to the seller's email inbox and app messages indicating listings have been removed. |      |
| 11.  The system deletes the listings from the database. |      |

---
|Title |   Select Payment Method     |
|---------|---------|
|**ID**|    UC-PAY-2      |
|**Relevant User Stories**|    FR-PAY-2     |
|**Description**|     This use case describes the process of selecting a payment method      |
|**System Under Design**|     Centauri/Payment Methods System.        |
|**Primary Actor**|     	General User.       |
|**Participants**|     	Administrative localUser, premium localUser, non-premium localUser, seller localUser, guest localUser.       |
|**Goal**|     To successfully select a payment method for an order.      |
|**Following Use Cases**|     Display Order Review, Payment Confirmation        |
|**Invariant**|     None       |
|**Precondition**|     The localUser must be checking out.       |
|**Success Postcondition**|     The localUser is able to pay with the payment method.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. The system displays the possible payment methods for the localUser.     |         |
| 2.  The localUser clicks on a saved payment method.    | 2a. The localUser inputs a new payment method.        |
| 3.  The localUser checks out with the selected payment method.    | 2b. The localUser clicks on a button to confirm the new payment method is to be used.        |
|     | 2c. The system temporarily saves the payment method for the current order. (Go to step 3.)        |

---

|Title |   Send Message      |
|---------|---------|
|**ID**|    UC-CHAT-1      |
|**Relevant User Stories**|    FR-CHAT-1, FR-CHAT-2, FR-CHAT-3, FR-CHAT-4     |
|**Description**|     This use case describes the steps for sending a message to another localUser on the platform.       |
|**System Under Design**|     Centauri/Chat System        |
|**Primary Actor**|      General User       |
|**Participants**|     Admin localUser, Premium User, Non-Premium User        |
|**Goal**|     Successfully deliver a message to another Centauri localUser.        |
|**Following Use Cases**|     None        |
|**Invariant**|   None         |
|**Precondition**|     User must be registered. The message must not be an empty message.       |
|**Success Postcondition**|     Message is delivered and shows up in the chat of both parties.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User clicks on the button to chat.      |  1a. User navigates to the page of seller/buyer/customer service.       |
| 2. System opens up a chat box.      |  1b. User clicks on the button on their page to chat. (Go to step 4.)      |
| 3. User selects the recipient within the chat box.    |         |
| 4. User types in a message.     |         |
| 5. User clicks on the button to send message.     |         |
| 6. App checks if message input is valid.     | 6a. System recognizes the message is invalid.        |
| 7. System delivers message to recipient.     | 6b. System notifies the localUser that they have entered an empty message.       |
| 8. Recipient localUser receives a message notification.     |         |

---



|Title |   Upgrade Membership     |
|---------|---------|
|**ID**|    UC-PR-2      |
|**Relevant User Stories**|    FR-PAY-1, FR-ACC-5    |
|**Description**|     This use case describes the process of upgrading a localUser account to a premium account.       |
|**System Under Design**|     Centauri/Premium System        |
|**Primary Actor**|     Non-Premium User.       |
|**Participants**|     None       |
|**Goal**|     To successfully upgrade a localUser account to a premium one.      |
|**Following Use Cases**|     More accurate search functions, higher customer service priority, faster shipping options, cancel subscription.       |
|**Invariant**|     User has a valid localUser account.      |
|**Precondition**|     User is not already a premium member.      |
|**Success Postcondition**|     User becomes a premium localUser.      |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User selects "upgrade account" button.     | 4a. User has not saved a payment method.        |
| 2. System loads upgrade account page.     | 4b. System prompts localUser to enter payment information.        |
| 3. System prompts localUser to select payment method.     |         |
| 4. User selects saved payment method.   |          |
| 5. System now designates account as premium account. |       |
| 6. System sends localUser a billing statement to their email. |        |

---

|Title |   View Item Location      |
|---------|---------|
|**ID**|    UC-TRK-1      |
|**Relevant User Stories**|    FR-TRK-1, FR-TRK-2, FR-TRK-3     |
|**Description**|     This use case describes the steps for viewing the current location of an order.      |
|**System Under Design**|    Tracking System     |
|**Primary Actor**|   General User   |
|**Participants**|    Admin User, Premium User, Non-Premium User    |
|**Goal**| Display the current location of an order.      |
|**Following Use Cases**|    None    |
|**Invariant**| None   |
|**Precondition**|  There must be an order that has been placed.         |
|**Success Postcondition**|    The system will display the current status and/or location of an order.        |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1.  The localUser clicks on the button to view their order.    |  1a. The localUser clicks on a notification of a tracking update. (Go to step 4)     |
| 2.  The system redirects the localUser to the page of an order.   |      |
| 3.   The localUser clicks on the button to view the tracking of an order.  |       |
| 4.   The system fetches the current and previous statuses of an order.  |     |
| 5.   The system returns a page displaying the current location, current status, and previous statuses of an order.  | 5a. The system displays that the current status of the item is not yet available.      |


---

|Title |   View Purchase History      |
|---------|---------|
|**ID**|    UC-PHI-1      |
|**Relevant User Stories**|    FR-PHI-1, FR-PHI-2, FR-PHI-3, FR-PHI-6     |
|**Description**|     This use case describes the steps for viewing an account's purchase history.       |
|**System Under Design**|     Centauri/Purchase History System        |
|**Primary Actor**|      General User       |
|**Participants**|     Admin localUser, Premium User, Non-Premium User        |
|**Goal**|     Successfully display a list of all purchases made on the account.       |
|**Following Use Cases**|     Search History, Filter History, View Item       |
|**Invariant**|   The localUser must be logged in.         |
|**Precondition**|     The localUser must have an account in the system.       |
|**Success Postcondition**|     The system displays the list of all purchases made on the account, if any.       |


|**STEPS**|**ALTERNATIVE**|
|---------|---------|
| 1. User clicks on the button to view their purchase history.      |        |
| 2. System directs them to the page with their purchases.     |         |
| 3. System displays list of purchases in order of most recent.    | 3a. System displays a message saying there has been no purchases made yet.        |


---

|Title |   View Search      |
|---------|---------|
|**ID**|    UC-SER-2      |
|**Relevant User Stories**|    FR-SER-1, FR-SER-2, FR-SER-3, FR-SER-4, FR-SER-5     |
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


---
