<h1 align="center">Creview Movie Album</h1></br>
<p align="center">  
A demo Movie app using compose and Hilt based on modern Android tech-stacks and MVVM architecture. Fetching data from the network and integrating persisted data in the database via repository pattern.
</p>
</br>
<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p>

## Screenshots
<p align="center">
<img src="https://i.postimg.cc/vMvSQ9rT/app-mockup-ios-screenshot-1-default-6-5-inch-1.png" width="270"/>
<img src="https://i.postimg.cc/mTNCy4gx/app-mockup-ios-screenshot-1-default-6-5-inch-3.png" width="270"/>
</br>
<img src="https://i.postimg.cc/D06ct9LF/app-mockup-android-screenshot-1-default-1080x1920-5.png" width="270"/>
<img src="https://i.postimg.cc/bv11XXfM/app-mockup-android-screenshot-1-default-1080x1920-3.png" width="270"/>


</p>

## Tech stack & Open-source libraries
- Minimum SDK level 21
- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- Hilt for dependency injection.
- JetPack
  - Compose - A modern toolkit for building native Android UI.
  - Lifecycle - dispose observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct database.
  - App Startup - Provides a straightforward, performant way to initialize components at application startup.
- Architecture
  - MVVM Architecture (Declarative View - ViewModel - Model)
  - Repository pattern
- Material Design & Animations
