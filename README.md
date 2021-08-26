# KMM Template

## What is KMM Template
KMM Template is sample Kotlin Multiplatform app with basic features set up and aligned to MVVM architecture.
  
  Basic features includes:
   - database with SQL Delight
   - networking with KTOR
   - serialization with kotlinx.serialization
   - coroutines with multithreaded support
   - usecases with Arkitekt km-usecases
   - cocoapods integration
   - iOS app ui with SwiftUI
   - Android app ui with Jetpack Compose
   
## Installation
 1. clone this repo
 2. switch to Template directory ( `cd Template`)
 3. execute initKmm.kts script with bash or kscript ( `bash initKmm.kts` / `kscript initKmm.kts` )
      executing with bash will download kscript first and then execute the script with kscript
 4. follow the steps in terminal
 5. open newly created project with Android Studio with Kotlin multiplatform plugin installed
 6. run Android / iOS apps and enjoy
 
## Requirements
  - Java JDK 11
  - Android Studio Arctic Fox 2020.3.1 or higher
  - Xcode 12.5.1 or higher
  - [kscript](https://github.com/holgerbrandl/kscript)
