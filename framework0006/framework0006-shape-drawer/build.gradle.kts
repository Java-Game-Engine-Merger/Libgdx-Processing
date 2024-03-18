plugins {
  id("java")
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