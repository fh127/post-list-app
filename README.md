# post-list-app
mobile test repository 



# Preconditions
* Git
* Android Studio last stable release
* JDK 8
* Android Devices >=  5.1 OS version (80%)    
  https://drive.google.com/open?id=1hw0CSXRnk4JpGe0iBJZ4FXgAZsxXJMSy


# Intructions
* Download the repository(git clone https://github.com/fh127/post-list-app.git).
* Open Android Studio.
* Install the necessary dependencies.
* Clean and Build.
* Run App.


# Architecture
* This demo app was done based patterns like:
   -  Repository pattern 
   -  Observer Pattern
   -  MVP (Model View Presenter)
   -  Inheritance and interfaces
   
   In general this design was based on SOLID to try getting a clean, mantanable and scalable app.
   
 * Module structure:
   - *app*: contains the configurations for app life cycle.
   - *business*: contains the business logic related with requirements.
   - *di*: contains th configurations related with Dependency injection.
   - *model*: contains the Data source operations handling (cache and services)
   - *utils*: contains utilities and functions to reuse in the app modules.
   - *presentation*: contains logic to handle view components and iteraction logic with business layer and data layer.
   
# Libraries
  * *Rooms(persistence library provides an abstraction layer over SQLite)*: Local storage and cache handling.
     https://developer.android.com/jetpack/androidx/releases/room
  * *Retrofit(type-safe HTTP client)*: service api handling.
     https://square.github.io/retrofit/
  * *RxJava/RxAndroid(library for composing asynchronous and event-based programs by using observable sequences)*: background task and       reactive events.
     https://github.com/ReactiveX/RxJava
  * *Dagger(dependency injection framework):* Dependency injection. 
     https://google.github.io/dagger/
  * *Android x(open-source project that the Android team uses to develop, test, package, version and release libraries within Jetpack)*
    https://developer.android.com/jetpack/androidx
    
# Improvements:
  * Implement Unit test cases for different modules/layers
  * Implement UI using fragments
  * Improve the adapter logic to reuse it.
    
# Demo
* https://drive.google.com/open?id=12_i0_b8ogfsR8oZWQMVXifj6v0H4vJ2H
* https://drive.google.com/open?id=1RrJ3lnOF9RO4F0PckMDUhaOSt1xz-tD3



