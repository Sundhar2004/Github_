--- Functionality ---
This Android application allows users to log in using their Google account via Firebase Authentication. 
After successful login, the app fetches the authenticated user's GitHub profile and repositories using GitHub REST API. 
The fetched repository data is stored in Room Database for offline access.
If there is no internet connection, the app loads data from the local database.
Additionally, the app handles Google Sign-In, Firebase Authentication, and clean navigation between screens using MVVM architecture.

--- Components Used ---
Kotlin
MVVM Architecture
Firebase Authentication (Google Sign-In)
Retrofit (API Calling)
Room Database (Offline Storage)
LiveData & ViewModel
Coroutines (Asynchronous API calls)
Repository Pattern
Material Design UI Components
Network Utility (Check Internet Availability)

--- Architecture Used (MVVM (Model-View-ViewModel)---
It provides a clean separation of concerns.
Easy to manage UI-related data in a lifecycle-conscious way using ViewModel.

--- Steps Taken for Test ---
Business logic is separated from UI (Activity/Fragment) into ViewModel.
Used LiveData and Coroutines for better lifecycle management and async handling.

