plugins {
  id("java")
  id("java-library")
  id("maven-publish")
}

repositories {
  mavenCentral()
}

dependencies {
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")

  val gdxVersion = rootProject.ext["gdxVersion"]
  api("com.badlogicgames.gdx:gdx:$gdxVersion")
}

tasks.test {
  useJUnitPlatform()
}


publishing {
  publications {
    create<MavenPublication>("maven") {
      val pama1234PublishVersion: String by project
      groupId = "pama1234"
      artifactId = "p6-drawer"
      version = "$pama1234PublishVersion"
      from(components["java"])

      pom {
        name.set("pama1234-shape-p6")
        description.set("shape drawer")
        url.set("https://github.com/pama1234/just-some-other-libgdx-game")
        scm {
          url.set("https://github.com/pama1234/just-some-other-libgdx-game")
        }
      }
    }
  }
}

//java {
//  sourceCompatibility = JavaVersion.VERSION_17
//  targetCompatibility = JavaVersion.VERSION_17
//}