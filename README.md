# Words-Android-App

![Min API](https://img.shields.io/badge/API-21%2B-orange.svg?style=flat)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

An Android application that helps to learn new words in different languages. 

Built partially using the Jetpack Compose.

The aim of is to show my abilities and use new for me, at the time of writing this text, libraries.

**Project may be unstable and contain bugs as it is under development

## Contents

- [Demonstration](#demonstration)
    - [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Development Setup](#development-setup)
- [Questions](#questions)
- [License](#license)

## Demonstration

### Screenshots

<p>
<img src="/media/screen1.png" width="100%"/>
</p>
<p>
<img src="/media/screen2.png" width="100%"/>
</p>
<p>
<img src="/media/screen3.png" width="100%"/>
</p>

## Tech Stack

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) - Official Kotlin's tooling for performing asynchronous work.
- [MVVM/MVI Architecture](https://developer.android.com/jetpack/guide) - Official recommended architecture for building robust, production-quality apps.
- [Android Jetpack](https://developer.android.com/jetpack) - Jetpack is a suite of libraries to help developers build state-of-the-art applications.

    - [Navigation](https://developer.android.com/guide/navigation) - Navigation
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The ViewModel is designed to store and manage UI-related data in a lifecycle conscious way.
    - [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow) - StateFlow is a state-holder observable flow that emits the current and new state updates to its collectors.
    - [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room library provides an abstraction layer over SQLite to allow for more robust database access.
    - [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt is a dependency injection library for Android.

- [Retrofit](https://github.com/square/retrofit) - A library for building REST API clients.

## Architecture

![architecture](/media/arch.png)

## Development Setup

This application uses an API to translate words, but there is no need to generate any keys, since this API allows limited use for anonymous users

To see more [MyMemory](https://mymemory.translated.net/doc/spec.php)
## Questions

If you have any questions regarding the codebase, hit me up - vadymdrahanov@gmail.com.

## License

Words is licensed under the [Apache 2.0 License](LICENSE).
