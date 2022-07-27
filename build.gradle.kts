import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.7.10"
	kotlin("jvm").version(kotlinVersion)
	kotlin("plugin.serialization").version(kotlinVersion)

	id("net.mamoe.mirai-console").version("2.12.0")
}

group = "my.example"
version = "1.0.0"
description = "Example Bot"

repositories {
	mavenLocal()
	maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
	mavenCentral()
}

dependencies {
	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-stdlib:${getKotlinPluginVersion()}")
	implementation("org.jetbrains.kotlin:kotlin-reflect:${getKotlinPluginVersion()}")
	compileOnly("org.jetbrains:annotations:23.0.0")
	// ??
	api("net.mamoe:mirai-console-compiler-annotations-jvm:2.11.1")
}

mirai {
	jvmTarget = JavaVersion.VERSION_17
}

tasks.withType<AbstractCompile> {
	sourceCompatibility = JavaVersion.VERSION_17.toString()
	targetCompatibility = JavaVersion.VERSION_17.toString()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		jvmTarget = JavaVersion.VERSION_17.toString()
		freeCompilerArgs = listOf(
			"-Xjsr305=strict",
			"-opt-in=kotlin.RequiresOptIn",
			// "-Xcontext-receivers",
		)
	}
}

tasks.create("build2Jar") {
	group = "mirai"
	dependsOn += "buildPlugin"
	doLast {
		val pluginPath = "${rootDir}/plugins/"
		(File(pluginPath).listFiles() ?: emptyArray()).filter f@{
			val name = it.name
			if (it.isFile && name.startsWith(rootProject.name)) {
				if (name.endsWith(".bak")) {
					println("Delete backup File: $name")
					if (!it.delete()) error("Cannot Delete File: $pluginPath$name")
					return@f false
				}
				return@f true
			}
			return@f false
		}.forEach {
			val name = it.name
			println("Backup File: $name to $name.bak")
			it.renameTo(File("$pluginPath$name.bak"))
		}
		(File("${buildDir}/mirai/").listFiles() ?: emptyArray()).forEach {
			val name = it.name
			println("Copy File: $name to plugins/$name ")
			it.copyTo(File(pluginPath + name), true)
		}
	}
}
