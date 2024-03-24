plugins {
  id("java")
  id("java-library")
  id("maven-publish")
}

group = "org.example"
version = "unspecified"

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
      artifactId = "flex-batch"
      version = "$pama1234PublishVersion"
      from(components["java"])

      pom {
        name.set("pama1234-flex-batch")
        description.set("flex batch")
        url.set("https://github.com/pama1234/just-some-other-libgdx-game")
        scm {
          url.set("https://github.com/pama1234/just-some-other-libgdx-game")
        }
      }
    }
  }
}