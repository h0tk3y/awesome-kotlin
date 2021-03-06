import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Project
import org.gradle.api.plugins.ApplicationPluginConvention
import org.gradle.api.tasks.JavaExec
import org.gradle.script.lang.kotlin.compile
import org.gradle.script.lang.kotlin.configure
import org.gradle.script.lang.kotlin.dependencies
import org.gradle.script.lang.kotlin.repositories
import org.gradle.script.lang.kotlin.testCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:1.2.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.3")
    }
}

apply {
    plugin("kotlin")
    plugin("application")
    plugin("com.github.johnrengelman.shadow")
}

configure<ApplicationPluginConvention> {
    mainClassName = "link.kotlin.scripts.Application"
}

configure<JavaExec>("run") {
    args(System.getProperty("travis", "false"))
}

configure<ShadowJar>("shadowJar") {
    mergeServiceFiles()
}

repositories {
    jcenter()
    maven { setUrl("https://dl.bintray.com/heapy/heap") }
}

configure<KotlinProjectExtension> {
    experimental.coroutines = Coroutines.ENABLE
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.3")
    compile("org.jetbrains.kotlin:kotlin-reflect:1.1.3")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:0.16")

    compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.8.8")
    compile("org.slf4j:slf4j-simple:1.7.25")

    compile("com.rometools:rome:1.7.0")
    compile("com.github.dfabulich:sitemapgen4j:1.0.6")
    compile("org.jsoup:jsoup:1.10.2")
    compile("by.heap.remark:remark-kotlin:1.2.0")

    compile("org.jetbrains.kotlin:kotlin-script-util:1.1.3")
    compile("com.atlassian.commonmark:commonmark:0.9.0")
    compile("com.atlassian.commonmark:commonmark-ext-gfm-tables:0.9.0")

    compile("com.squareup.okhttp3:okhttp:3.5.0")

    testCompile("junit:junit:4.12")
}

inline fun <reified C> Project.configure(name: String, configuration: C.() -> Unit) {
    (this.tasks.getByName(name) as C).configuration()
}
