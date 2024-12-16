# GamerPlay App - Technical Test

This repository contains the code for the GamerPlay app, developed as part of a technical test. The app fetches and displays a list of games from a remote API.

## Architecture

The app follows a Clean Architecture approach, aiming for separation of concerns, testability, and maintainability. The project is structured into the following modules:
app: The main application module, responsible for UI setup, navigation, UI features and dependency injection.
domain: Contains the core business logic, including entities (data models) and use cases (interactors). This module is independent of any specific implementation details.
data: Handles data access, including fetching data from the remote API and implementing the repository interfaces defined in the `domain` module.


## Technologies Used
*   Kotlin: The primary programming language.
*   Jetpack Compose: Modern toolkit for building native Android UIs.
*   Hilt: Dependency injection library for Android.
*   Retrofit: HTTP client for making network requests.
*   Coil: Image loading library.
*   Kotlin Coroutines: For asynchronous programming.
*   JUnit and Mockito: For unit testing.
*   Navigation Compose: For in-app navigation.
*   Material Design 3: Modern UI design system.

## Key Features
*   Displays a list of games fetched from a remote API.
*   Uses pull-to-refresh functionality to update the game list.
*   Implements a shimmer effect to provide a smooth loading experience.
*   Includes unit tests for the app, data and domain layers.

## Testing
Unit tests have been implemented for the 'app', 'data' and 'domain' layers using JUnit and Mockito. These tests cover various scenarios, including successful API calls, network errors, and HTTP errors.

## Conclusion
This project demonstrates a solid understanding of Android development best practices, including Clean Architecture, dependency injection, and unit testing.
