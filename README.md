# LinguaJournal

LinguaJournal is an Android application built in Kotlin that helps users build and organise vocabulary through a personal language learning journal.

The app allows users to write entries, extract vocabulary, translate text using an external API, and store everything locally using a structured database. It was developed as part of a Mobile Software Development module and follows modern Android architecture principles.



## Features

- Create and manage journal entries
- Extract and store vocabulary from written text
- Translate text using the MyMemory API (via Retrofit)
- Persist data locally using Room Database
- Sort and organise vocabulary entries
- Clean multi-fragment UI using the Navigation Component


## Architecture & Design

LinguaJournal follows the MVVM (Model–View–ViewModel) architecture pattern to ensure separation of concerns and maintainability.

The project includes:

- UI layer (Fragments)
- ViewModels
- Repository layer
- Room Database (DAO + Entities)
- Retrofit API service
- Kotlin Coroutines for asynchronous operations

Coroutines are used to handle API calls and database operations off the main thread in a structured and lifecycle-aware way.



## Tech Stack

- Kotlin
- Android SDK
- MVVM Architecture
- Room Database
- Retrofit
- Kotlin Coroutines
- Navigation Component


## Project Structure

- `data/` – Database, DAO, Entities, Repository
- `api/` – Retrofit service definitions
- `ui/` – Fragments and UI logic
- `viewmodel/` – ViewModels
- `utils/` – Helper and utility classes


## How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle
4. Run on an emulator or physical device


## What I Learned

Through this project, I gained practical experience with:

- Implementing MVVM architecture in Android
- Managing asynchronous tasks with Kotlin Coroutines
- Integrating REST APIs using Retrofit
- Designing a Room database with DAOs and entities
- Structuring a scalable Android application

## Challenges & Solutions

One of the main challenges was structuring the app using MVVM while keeping responsibilities clearly separated between UI, ViewModel, and Repository layers. 

Managing asynchronous API calls and database operations safely using Kotlin Coroutines also required careful handling to avoid blocking the main thread.

These challenges helped strengthen my understanding of Android architecture and lifecycle-aware components.

