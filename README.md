# Banking Ledger Engine

![Build Status](https://github.com/moumen-bouguessa/Banking-ledger-engine/workflows/CI%20Build,%20Test,%20Security,%20and%20Push%20Docker%20Image/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=moumen-bouguessa_Banking-ledger-engine&metric=alert_status&token=1a6b64f14a0a0f77f597c85ef11d05a195300226)](https://sonarcloud.io/summary/new_code?id=moumen-bouguessa_Banking-ledger-engine)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=moumen-bouguessa_Banking-ledger-engine&metric=sqale_rating&token=1a6b64f14a0a0f77f597c85ef11d05a195300226)](https://sonarcloud.io/summary/new_code?id=moumen-bouguessa_Banking-ledger-engine)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=moumen-bouguessa_Banking-ledger-engine&metric=security_rating&token=1a6b64f14a0a0f77f597c85ef11d05a195300226)](https://sonarcloud.io/summary/new_code?id=moumen-bouguessa_Banking-ledger-engine)

A high-performance, secure core banking backend engine built with Java 17 and Spring Boot 3. Designed following rigorous software verification, extensive mutation testing, formal behavioral specification, and multi-layered DevSecOps principles.

---

## Architecture Overview

* **Core Framework:** Spring Boot 3.1.5 (Java 17)
* **Build System:** Apache Maven
* **Formal Verification:** OpenJML (Java Modeling Language contracts)
* **Testing Suite:** JUnit 5, JaCoCo, PiTest (Mutation Analysis), JMH (Microbenchmarks)
* **Security & SAST:** GitGuardian (ggshield), Snyk, SonarCloud
* **Containerization & CI/CD:** Docker, DockerHub, GitHub Actions

---

## Core Quality & Security Standards

### 1. Formal Specification (JML)
Core ledger operations are formally specified using Java Modeling Language (JML) class invariants and method contracts (@requires, @ensures) verified via OpenJML to guarantee numerical exactness and transactional state constraints.

### 2. Comprehensive Test Suite & Mutation Analysis
* **Unit & Integration Tests:** Comprehensive test coverage verified using JaCoCo.
* **Mutation Testing:** Executed via PiTest to eliminate false-positive unit tests and ensure high fault-detection coverage.
* **Microbenchmarking:** Performance-critical execution paths are benchmarked using Java Microbenchmark Harness (JMH).

### 3. CI/CD Pipeline Automation
Every push to main executes an automated GitHub Actions pipeline:
* **Secret Detection:** GitGuardian scans commit history for exposed API tokens and hardcoded credentials.
* **Dependency Vulnerability Scanning:** Snyk audits third-party Maven packages against known CVEs.
* **Static Code Analysis:** SonarCloud performs SAST checks for security vulnerabilities and code smells using JDK 21 runtime.
* **Automated Publishing:** Passes all quality gates before building and pushing a production-ready image to DockerHub (moumenbouguessa/banking-ledger-engine:latest).

---

## Local Development Setup

### Prerequisites
* Java Development Kit (JDK 17)
* Apache Maven 3.8+
* Docker Desktop

### 1. Clone & Build Locally
git clone https://github.com/moumen-bouguessa/Banking-ledger-engine.git
cd Banking-ledger-engine
mvn clean package

### 2. Run Unit Tests & Generate JaCoCo Coverage
mvn clean test jacoco:report

The HTML coverage report will be generated at target/site/jacoco/index.html.

### 3. Run PiTest Mutation Testing
mvn pitest:mutationCoverage

The mutation report will be saved under target/pit-reports/.

---

## Docker Container Deployment

### Run Container Directly from DockerHub
docker pull moumenbouguessa/banking-ledger-engine:latest
docker run -d -p 8080:8080 --name banking-ledger moumenbouguessa/banking-ledger-engine:latest

### Build Container Locally
docker build -t banking-ledger-engine:local .
docker run -d -p 8080:8080 banking-ledger-engine:local

---

## CI/CD Workflow Architecture

[ Git Push ] 
     │
     ├──► GitGuardian (Secret Leak Detection)
     ├──► Java 17 Setup & Maven Build
     ├──► Snyk (Dependency Security Audit)
     ├──► JUnit 5 Tests + JaCoCo Coverage + PiTest Mutation
     ├──► Java 21 Setup + SonarCloud (SAST Code Quality)
     └──► Build Docker Container ──► Push to DockerHub

---

## License

This project is open-source and available under the MIT License.