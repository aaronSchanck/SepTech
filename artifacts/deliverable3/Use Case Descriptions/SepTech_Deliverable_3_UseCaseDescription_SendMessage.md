|Title |   Send Message      |
|---------|---------|
|**ID**|    UC-CHAT-1      |
|**Relevant User Stories**|    FR-CHAT-1, FR-CHAT-2, FR-CHAT-3, FR-CHAT-4     |
|**Description**|     This use case describes the steps for sending a message to another user on the platform.       |
|**System Under Design**|     Centauri/Chat System        |
|**Primary Actor**|      General User       |
|**Participants**|     Admin user, Premium User, Non-Premium User        |
|**Goal**|     Successfully deliver a message to another Centauri user.        |
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
| 7. System delivers message to recipient.     | 6b. System notifies the user that they have entered an empty message.       |
| 8. Recipient user receives a message notification.     |         |