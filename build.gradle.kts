plugins {
	java
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.simcheong"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.2.4")
	implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.2.4")
	implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")
	compileOnly("org.projectlombok:lombok:1.18.32")
	runtimeOnly("com.h2database:h2:2.2.224")
	annotationProcessor("org.projectlombok:lombok:1.18.32")
	testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
	testImplementation("org.springframework.security:spring-security-test:4.1.4.RELEASE")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}