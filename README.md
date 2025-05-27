# Spacenews - Android App

[App Logo](app/src/main/res/mipmap-anydpi/ic_launcher.xml)

**Spacenews** is an Android application designed to deliver the latest news and articles related to
space exploration, astronomy, and aerospace advancements. Built with modern Android development
technologies, it offers a clean, user-friendly interface for browsing and reading space-related
content.

## Features

* **Browse Articles:** View a list of the latest space news articles, updated regularly.
* **Infinite Scrolling:** Smoothly scroll through a virtually endless list of articles thanks to
  efficient data paging.
* **Article Details:** Tap on an article to view its full content, including images and summaries.
* **Enhanced Detail View (V2):** A richer detail screen experience designed by AI.
* **Open in Browser:** Option to open the full article in an external web browser.
* **Share Articles:** Easily share interesting articles with friends and family via other apps.
* **Offline Caching:** Access previously loaded articles even when offline.
* **Pull to Refresh:** Update the article list with the latest content.
* **Settings:** For switching between the basic and enhanced experience.

## Technologies Used

This project leverages a modern Android tech stack, including:

* **Kotlin:** Primary programming language.
* **Jetpack Compose:** For building the entire UI declaratively.
* **Navigation Compose:** For implementing in-app navigation between different Composable screens in
  a type-safe and idiomatic Compose way.
* **Coroutines & Flow:** For asynchronous programming and reactive data streams.
* **ViewModel:** To manage UI-related data in a lifecycle-conscious way.
* **Paging 3:** For efficiently loading and displaying large lists of data.
* **Retrofit:** For type-safe HTTP calls to fetch news data from
  the https://api.spaceflightnewsapi.net/v4/docs/.
* **Kotlinx Serialization:** For parsing JSON data from the API.
* **OkHttp3 Logging Interceptor:** For inspecting network requests and responses during development.
* **Room:** For offline support.
* **DataStore (Preferences):** For storing simple key-value settings
* **Koin:** For dependency injection.
* **Coil:** For image loading and caching.
* **Timber:** For flexible logging.
* **Material Design 3:** For UI components and theming.
* **Android Jetpack Libraries:** Navigation Compose, Lifecycle, etc.
* **Gradle with Version Catalogs (`libs.versions.toml`):** For managing dependencies.

## Architecture

This application's architecture is **highly inspired by Clean Architecture principles**, aiming for
a separation of concerns, improved testability, and a degree of independence from external
frameworks and UI. While striving for this separation, it's acknowledged that certain foundational
Android frameworks or libraries, crucial to the app's core functionality, may have a presence across
multiple conceptual layers.

The architecture is generally structured around the following layers:

* **Domain Layer:** This is the heart of the application, containing the core business logic and
  business entities.
    * **Entities:** Plain Kotlin data classes representing the fundamental concepts of your
      application (e.g., `Article`).
    * **Use Cases (Interactors):** These classes encapsulate specific application business rules.
      They
      orchestrate the flow of data, including paginated data structures like
      `Flow<PagingData<Type>>`,
      by interacting with repository interfaces.
    * **Repository Interfaces:** Abstract definitions for data operations, dictating how data (
      including
      paginated data) should be fetched or stored.

* **Data Layer:** Responsible for providing the concrete implementation for the repository
  interfaces defined in the Domain layer. It handles data retrieval from various sources.
    * **Repositories:** Implementations of the repository interfaces from the Domain layer. They
      configure and return data streams, such as `Flow<PagingData<Type>>` using Jetpack Paging
      components like `Pager` and `PagingSource`.
    * **Data Sources:** Concrete classes that fetch data for pagination
    * **`ArticleRemoteMediator`**:
        * A Paging 3 component that orchestrates loading data from the network when local data is
          exhausted
          or a refresh is needed.
    * Fetches data via `ArticleApiService`, stores it in Room, and updates pagination keys.
    * **Models & Mappers**:
        * **API Models**: Represent network response structures.
        * **Database Entities**: Represent Room database tables.
        * **Mappers**: Convert data between API models and database entities.

* **Presentation Layer (MVVM - ViewModels):** This layer acts as an intermediary between the UI
  Layer and the Domain Layer. It contains the presentation logic.
    * **ViewModels:** Manage and prepare data for the UI layer. They fetch data from the Domain
      layer (via Use Cases), apply any necessary UI-specific transformations, and expose it as
      observable UI state
    * **UI State Management:** Defines and holds the state that the UI layer needs to render itself.
      This includes data to be displayed, loading indicators, error messages, etc.
    * **User Action Processing:** Receives user actions from the UI layer and delegates the
      corresponding business logic to Use Cases in the Domain layer.
    * *ViewModels in this layer are typically Android lifecycle-aware but should avoid direct
      dependencies on Android UI framework classes.*

* **UI Layer (Jetpack Compose):** This is the outermost layer, responsible for everything the user
  sees and interacts with.
    * **Composables (Screens & UI Elements):** Jetpack Compose functions (`@Composable`) that define
      the visual structure and appearance of the application. They observe UI state exposed by the
      Presentation layer (ViewModels) and render the interface accordingly.
    * **User Input Handling:** Captures user interactions (taps, scrolls, text input) and forwards
      these events to the Presentation layer (ViewModels) for processing.
    * **State Observation:** Subscribes to UI state changes from ViewModels and re-renders
      efficiently when data changes.
    * *This layer is entirely focused on UI concerns and should contain minimal to no business or
      presentation logic.*

## Project Structure

The project is organized by feature, with core components shared across features, aligning with MVVM
and Clean Architecture principles.

* **`:app` Application Module**:
    * The main entry point (`MainActivity`, `SpacenewsApp`).
    * Integrates all feature modules.
    * Handles global dependency injection setup and top-level navigation.
    * Depends on feature and core modules.

* **`:feature`Feature Modules**:
    * Encapsulate specific app functionalities (e.g., article browsing, settings).
    * Typically structured internally into:
        * `:presentation`: ViewModels, Composable Screens, feature-specific navigation.
        * `:domain`: Use Cases, Repository Interfaces, Domain Models (pure Kotlin).
        * `:data`: Repository Implementations, Remote (Retrofit API services, DTOs) and Local (Room
          DAOs, Entities) Data Sources, Mappers.
    * Depend on core modules; aim for independence from other feature modules.

* **`:sdk` "SDK-like" Modules**:
    * Contain reusable code across features.
    * Examples:
        * `:theme`: Common Composables, App Theme.
        * `:network`: Base network setup (Retrofit), shared database configuration (Room).
        * `:logger`: Logging setup.
    * Reduce duplication and enforce consistency. Feature modules and `:app` depend on these.

## Getting Started
### Prerequisites

* Android Studio (latest stable version recommended)
* Kotlin Plugin
* Android SDK

### Building and Running

1. **Clone the repository:**
2. **Open in Android Studio:**
    * Open Android Studio.
    * Click on "Open an Existing Project".
    * Navigate to the cloned directory and select it.
3. **Sync Gradle:**
    * Android Studio should automatically sync the Gradle project. If not, click on "Sync Project
      with Gradle Files" (elephant icon).