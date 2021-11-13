# Petnet
## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
This app is dedicated to showcase your pets, connect you to pets up for adoption, and also for petsitters who are setting their pet up for a dedicated pet sitting job or taking care of your pet.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Social Networking and Lifestyle?
- **Mobile:** This is essential to be mobile because this app would be dedicated to showing off your pets. Basically seeing anyone in the area interested in pets, it would be showcased here. 
- **Story:** Creates a more mobile access to showcasing pets in specific, compared to bigger social networks that focus on general picture socializing.
- **Market:** Can be used by different organizations, whether it would be a Zoo, an Animal Shelter, Individuals with multiple pets or animals, animal lovers, etc.
- **Habit:** Can be used daily, or weekly to see different pet postings, new events, new showcases, gatherings, etc. Would also be used to check up on different animals by having indirect contact to them.
- **Scope:** Connects people to pets and to other pet owners. We can first start by adding people

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

[ ] User can register for a new account
[ ] User can login
[ ] User can upload pictures of their pets
[ ] User can connect with other pet owners

**Optional Nice-to-have Stories**

[ ] User can find a pet for adoption at a nearby animal shelter
[ ] User can find a petsitter easily


### 2. Screen Archetypes

* Login
    * User can login when the app opens
* Register - User signs up or logs into their account
   * User is prompted to register or log into an existing account upon first opening the application
* Pet Feed
   * User is shown content of pets based on their preferences
   * User is able to filter what kinds of content they want/don't want to see on their feed
* Petsitter
    * User is able to find a petsitter and communicate with them
* Pet Adoption
   * User is able to view adoption centers at animal shelters nearby
* User Profile
   * User is able to edit preferences such as what pets they are interested in and if they are an owner or not
   * User is able to change profile picture as well as name
   * Pet Profile - a sub profile for a User Profile that shows the users' pet(s)

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Pet Feed
* Pet Adoption
* User Profile
    * Pet Profiles

**Flow Navigation** (Screen to Screen)
* Login
    * Pet Feed
* Register
    * Pet Feed
* Pet Feed
    * User Profiles
* Petsitter
    * petsitter profile // tbdfigma
* Pet Adoption
    * Shelters Page
* User Profile
    * Pet Feed 

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/Petnet-CodePath/Petnet/blob/main/Wireframe.png" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
### Models
[Add table of models]
   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | author        | Pointer to User| image author |
   | image         | File     | image that user posts |
   | caption       | String   | image caption by author |
   | likesCount    | Number   | number of likes for the post |

### Networking
- Home Feed Screen
   - (Read/GET) Query all posts where user is author
   - (Create/POST) Create a new like on a post
   - (Delete) Delete existing like
   - (Create/POST) Create a new comment on a post
   - (Delete) Delete existing comment
- Create Post Screen
   - (Create/POST) Create a new post object
- Profile Screen
   - (Read/GET) Query logged in user object
   - (Update/PUT) Update user profile image



- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
