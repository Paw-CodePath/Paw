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

- [ ] User can register for a new account
- [ ] User can login
- [ ] User can upload pictures of their pets
- [ ] User can connect with other pet owners

**Optional Nice-to-have Stories**

- [ ] User can find a pet for adoption at a nearby animal shelter
- [ ] User can find a petsitter easily


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
<img src="https://github.com/Petnet-CodePath/Petnet/blob/main/Wireframe.png" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
### Models
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

## User stories
- [ ]  Main Login Screen
   - [X]  Connect to Back4App
   - [X]  Create Test Profiles
   - [ ]  Login Button -> Main Screen
   - [ ]  Register Button -> Requried to Input Username and Password -> First Time Profile Set up Screen
- [ ]  Main Screen
   - [ ]  Main Profile Screen Shows up
   - [ ]  Functionality of Check and X buttons
   - [ ]  Being able to swipe left for no, right for yes.
   - [ ]  Tapping the Profile will be able to preview the whole profile layout
   - [ ]  *Apply this as the last step* No more Matches left screen
- [ ]  Matches/Messaging Screen
   - [ ]  Functionality of Filters Buttons
   - [ ]  Previewing new matches
   - [ ]  Recycler View of People you have been messaging with
- [ ]  Profile Options Menu Screen
   - [ ]  User Profile Settings Button -> User Profile Screen
   - [ ]  Pet Profile Settings Button -> Pet Profile Screen
   - [ ]  Previewing Profile picture, as well as pet picture
   - [ ]  Toggle Button Functionality for Fitlering Search Criteria
   - [ ]  *Stretch* Being able to manually search for organizations and people for adopting pets
- [ ]  First time Profile Set up screen
   - [ ]  Requiring For at least for:
      - [ ]  User: Must have name, 1 picture, looking for, and you are tag.
      - [ ]  *Pet is optional but to create a pet profile must have: * 1 picture, looking for, and you are tag.
   - [ ]   Button being able to add more pet profiles
- [ ]  User Profile
   - [ ]  Button to add a picture, and replace a picture. Max 4 pictures
   - [ ]  Description box. Max: 280 chars
   - [ ]  Looking for tag box. tags: Petsitter/Dogwalker, Adopting pets, Other Petowners
   - [ ]  Who are you tag box. tags: Human, Organization/Adoption Center, Other... 
- [ ]  Pet Profile 
   - [ ]  Button to add a picture, and replace a picture. Max 4 pictures
   - [ ]  Description box. Max: 280 chars
   - [ ]  Looking for tag box. tags: Petsitter/Dogwalker, Adoption, Other pets
   - [ ]  Who are you tag box. tags: Dog, Cat, Bird, Other...
- [X]  ToolBar and Fragments
   - [X]  Base UI of 3 Fragment Screens (Main, Matches, Profile)
   - [X]  Bottom toolbar to switch between fragments

## Build Progress
11/13-11/19 Progress

*Input GIF here*

*Input Brief Description of what was added*


11/20-11/26

*Input GIF here*

*Input Brief Description of what was added*

