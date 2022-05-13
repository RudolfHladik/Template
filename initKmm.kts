#!/bin/bash

//usr/bin/env echo '
/**** BOOTSTRAP kscript ****\'>/dev/null
command -v kscript >/dev/null 2>&1 || curl -L "https://git.io/fpF1K" | bash 1>&2
exec kscript $0 "$@"
\*** IMPORTANT: Any code including imports and annotations must come after this line ***/

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

//// APP

val (appName, packageName) = getAppNameAndPackage()
renamePackagesInAndroidApp(packageName)
renamePackagesInShared(packageName)
// rename rootProject.name in settings.gradle.kts
renameTextInPath("template-kit/settings.gradle.kts", "template-kit", appName)
renameRootFolder(appName)
println("KMM App: $appName created!")

//// END APP

// region functions

fun renameRootFolder(appName: String) {
    moveFilesToPath("template-kit", appName)
}

fun renamePackagesInAndroidApp(packageName: String) {
    // move androidApp to new package
    val packagePath = packageName.replace('.', '/')
    moveFilesToPath("template-kit/androidApp/src/main/java/com/rudolfhladik/kmm/template/android", "template-kit/androidApp/src/main/kotlin/${packagePath}/android")
    val androidAppJavaFolder = File("template-kit/androidApp/src/main/java")
    androidAppJavaFolder.deleteRecursively()

    // rename package and imports
    renameTextInFilePath("template-kit/androidApp/","com.rudolfhladik.kmm.template", packageName)
}

fun renamePackagesInShared(packageName: String) {
    val packagePath = packageName.replace('.', '/')

    val targets = listOf(
        "androidMain",
        "androidTest",
        "commonMain",
        "commonTest",
        "iosMain",
        "iosTest"
    )
    val temp = "temp"
    targets.forEach {
        val sourcePackage = getSourceSharedPackageForTarget(it)
        moveFilesToPath(sourcePackage, temp)
        val sourceFile = File("template-kit/shared/src/${it}/kotlin/com")
        sourceFile.deleteRecursively()
        val targetPackage = getNewSharedPackageForTarget(it, packagePath)
        moveFilesToPath(temp, targetPackage)
        val tempFolder = File(temp)
        tempFolder.deleteRecursively()
    }
    // move SQL entity to correct package
    val sqlSource = "template-kit/shared/src/commonMain/sqldelight/com/rudolfhladik/kmm/template"
    moveFilesToPath(sqlSource, temp)
    val sourceFile = File("template-kit/shared/src/commonMain/sqldelight/com")
    sourceFile.deleteRecursively()
    val targetPackage = "template-kit/shared/src/commonMain/sqldelight/${packagePath}"
    moveFilesToPath(temp, targetPackage)
    val tempFolder = File(temp)
    tempFolder.deleteRecursively()

//    renamePackagesInSharedClasses(packageName)
    renameTextInFilePath("template-kit/shared/", "com.rudolfhladik.kmm.template", packageName)
}

fun renameTextInFilePath(path: String, oldText: String, newText: String) {
    val f = File(path)
    f.walk().forEach {
        if (it.isDirectory.not()) {
            renameTextInPath(it.path,oldText, newText)
        }
    }
}

fun getSourceSharedPackageForTarget(target: String): String {
    return "template-kit/shared/src/${target}/kotlin/com/rudolfhladik/kmm/template"
}

fun getNewSharedPackageForTarget(target: String, packagePath: String): String {
    return "template-kit/shared/src/${target}/kotlin/${packagePath}"
}

fun renameTextInPath(path: String, oldText: String, newText: String) {
    val path: Path = Paths.get(path)
    val charset = StandardCharsets.UTF_8

    var content = String(Files.readAllBytes(path), charset)
    content = content.replace(oldText.toRegex(), newText)
    Files.write(path, content.toByteArray(charset))
}

fun moveFilesToPath(from: String, to: String) {
    val f = File(to)
    f.mkdirs()

    val sourcePath = Paths.get(from)
    val targetPath = Paths.get(to)
    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING)
}

fun getAppNameAndPackage(): Pair<String, String> {
    println("Let's create KMM app")
    print("Enter app name: ")
    val appName: String = readLine() ?: error("You need to enter name")

    println("Enter app package name")
    println("e.g. com.example.test")
    print("Package name: ")
    val packageName = readLine() ?: error("You need to enter package name")
    return appName to packageName
}
