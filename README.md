# KMM Template

## What is KMM Template
KMM Template is sample Kotlin Multiplatform app with basic features set up and aligned to MVVM architecture.
With the initKmm.kts script you can setup your own App with all the features from this Template. It will rename packages and application id accordingly.
  
  Basic features includes:
   - database with **SQL Delight**
   - networking with **KTOR**
   - serialization with **kotlinx.serialization**
   - **coroutines** with multithreaded support
   - usecases with **Arkitekt km-usecases**
   - **cocoapods** integration
   - iOS app ui with **SwiftUI**
   - Android app ui with **Jetpack Compose**
   
## Installation
 1. clone this repo
 2. switch to Template directory ( `cd Template`)
 3. execute initKmm.kts script with bash or kscript ( `bash initKmm.kts` / `kscript initKmm.kts` )
      executing with bash will download kscript first and then execute the script with kscript
      - if you install kscript for the first time it may throw an error that the `kscript command not found` try restarting terminal
      and execute `kscript initKmm.kts` again
 4. follow the steps in terminal
 5. open newly created project with Android Studio with Kotlin multiplatform plugin installed
 6. run Android / iOS apps and enjoy

## Versions
 check actual versions in gradle versions [catalog](Template/gradle/libs.versions.toml)
 
## Requirements
  - Java JDK 11
  - Android Studio Arctic Fox 2020.3.1 or higher
  - Xcode 12.5.1 or higher
  - [kscript](https://github.com/holgerbrandl/kscript)

## Contributors

Current maintainer and main contributor is [Rudolf Hladík](https://github.com/RudolfHladik).

## License

KMM Template is available under the MIT license. See the [LICENSE](LICENSE) for more information.
