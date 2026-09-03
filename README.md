# Software Dependability Analysis: Concurrent Banking Ledger Engine

![Build Status](https://github.com/moumen-bouguessa/Banking-ledger-engine/workflows/CI%20Build,%20Test,%20Security,%20and%20Push%20Docker%20Image/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=moumen-bouguessa_Banking-ledger-engine&metric=alert_status&token=1a6b64f14a0a0f77f597c85ef11d05a195300226)](https://sonarcloud.io/summary/overall?id=moumen-bouguessa_Banking-ledger-engine)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=moumen-bouguessa_Banking-ledger-engine&metric=sqale_rating&token=1a6b64f14a0a0f77f597c85ef11d05a195300226)](https://sonarcloud.io/summary/overall?id=moumen-bouguessa_Banking-ledger-engine)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=moumen-bouguessa_Banking-ledger-engine&metric=security_rating&token=1a6b64f14a0a0f77f597c85ef11d05a195300226)](https://sonarcloud.io/summary/overall?id=moumen-bouguessa_Banking-ledger-engine)

## 1. Executive Summary

This repository contains the source code, verification artifacts, and dependability analysis for a custom-built Concurrent Banking Ledger Engine, developed for the Software Dependability course at the University of Salerno (Project Option #2). 

Unlike traditional static analyses of third-party repositories, this project demonstrates the end-to-end engineering of a secure, concurrent application. The methodology applies proactive fault avoidance (JML) alongside reactive fault tolerance (Mutation Testing) and continuous security auditing (DevSecOps).

### 1.1 Key Quantitative Findings

* **Structural Coverage (JaCoCo):** 99% Line Coverage (90/91 instructions).
* **Mutation Adequacy (PiTest):** 98% Mutation Score (50/51 synthetic faults killed).
* **Performance Baseline (JMH):** 0.044 microseconds (us) per isolated transaction operation.
* **Security Posture (SonarCloud/Snyk):** 0 vulnerabilities, 0 leaked secrets, passing Quality Gate.
* **ISO/IEC 25010 Compliance:** High compliance across Reliability, Security, and Maintainability axes.

The full academic report detailing the formal verification and testing methodology can be found in `Software_Dependability_Report.pdf`.

---

## 2. Methodology & Analytical Phases

The dependability of the Banking Ledger Engine was assessed through six systematic phases:

### Phase 1: Architectural Design & Threat Modeling
Financial ledgers are highly susceptible to concurrency anomalies (race conditions) and invalid state transitions (negative balances). 
* **Concurrency Mitigation:** Implementation of explicit Java `ReentrantLock` primitives to guarantee atomic, thread-safe ledger updates.
* **Vulnerability Mitigation:** Output contexts vulnerable to reflected Cross-Site Scripting (XSS) were neutralized using Spring's `HtmlUtils.htmlEscape()`.

### Phase 2: Formal Specification (JML)
To eliminate ambiguity in state transitions, Java Modeling Language (JML) was embedded into the domain logic to enforce strict mathematical contracts.

| Specification Type | Implementation Constraint | Protection Vector |
|--------------------|---------------------------|-------------------|
| **Precondition**   | `requires delta > 0`      | Prevents zero or negative transaction amounts from initiating. |
| **Postcondition**  | `ensures balance_new = balance_old +/- delta >= 0` | Guarantees exact delta application and prevents overdrafts. |
| **Class Invariant**| `public invariant balance >= 0;` | Enforces global state integrity for all Account instances. |

### Phase 3: Structural Code Coverage Analysis
Functional behavior was evaluated using JUnit 5, while execution thoroughness was measured via JaCoCo.

| Metric Type | Executed | Total | Coverage Percentage |
|-------------|----------|-------|---------------------|
| **Lines**   | 90       | 91    | 99%                 |
| **Methods** | 100%     | 100%  | 100%                |
| **Classes** | Core     | All   | 100%                |

*Analysis:* Unexecuted branches (1%) are strictly confined to framework-level exception catch blocks. All core financial classes (`Account`, `Ledger`, `TransactionService`) achieved complete structural coverage.

### Phase 4: Fault-Tolerance via Mutation Testing
To prove that the 99% coverage metric actually detects bugs, bytecode mutation analysis was executed using PiTest. Synthetic faults (mutants) were injected into the compiled bytecode.

| Mutation Metric | Achieved Value | Industry Standard Assessment |
|-----------------|----------------|------------------------------|
| Total Mutations Generated | 51 | N/A |
| Mutations Killed by Tests | 50 | N/A |
| **Overall Mutation Score**| **98%** | Outstanding (>80% is considered Very Good) |

*Analysis:* The test suite successfully detected boundary modifications (changing `>` to `>=`), inverted logic, and altered return values, proving exceptional resilience against software regressions.

### Phase 5: Performance Microbenchmarking
Transaction latency was profiled using the Java Microbenchmark Harness (JMH).

| Benchmark Parameter | Configuration / Result |
|---------------------|------------------------|
| **JVM Environment** | OpenJDK 21, Single-Threaded Baseline |
| **Iterations**      | 5 Warmup, 5 Measurement |
| **Measured Latency**| **0.044 microseconds (us) / op** |

*Analysis:* The `ReentrantLock` concurrency model introduces negligible synchronization overhead. This baseline indicates high computational efficiency for the isolated transaction operation.

### Phase 6: DevSecOps & Security Integration
Security was enforced through a continuous, multi-layered CI/CD pipeline using GitHub Actions.

1. **GitGuardian:** Scanned commit history. Result: [PASS] 0 secrets leaked.
2. **Snyk CLI:** Audited Maven dependency tree. Result: [PASS] 0 CVEs detected.
3. **SonarCloud SAST:** Analyzed codebase. Result: [PASS] 'A' Rating across Security, Reliability, and Maintainability.

---

## 3. ISO/IEC 25010 Software Quality Assessment

The Banking Ledger Engine maps directly to the standard ISO/IEC 25010 software quality characteristics:

| Characteristic | Evaluation Evidence | Assessment |
|----------------|---------------------|------------|
| **Functional Suitability** | JML contracts guarantee accurate state transitions. | 5/5 |
| **Performance Efficiency** | JMH benchmarks confirm 0.044us latency per operation. | 5/5 |
| **Reliability** | 83 robust unit tests with 98% PiTest mutation kill rate. | 5/5 |
| **Security** | Automated SAST (SonarCloud) and Supply-Chain scans (Snyk). | 5/5 |
| **Maintainability** | Automated CI/CD pipeline ensures continuous quality control. | 5/5 |

---

## 4. Local Development & Execution

### Prerequisites
* Java Development Kit (JDK 17 or higher)
* Apache Maven 3.8+
* Docker Desktop

### 4.1 Clone & Build
```bash
git clone [https://github.com/moumen-bouguessa/Banking-ledger-engine.git](https://github.com/moumen-bouguessa/Banking-ledger-engine.git)
cd Banking-ledger-engine
mvn clean package
```

### 4.2 Execute Verification Suites
```bash
# Generate JaCoCo Structural Coverage Report
mvn clean test jacoco:report
# Report output: target/site/jacoco/index.html

# Execute PiTest Mutation Analysis
mvn pitest:mutationCoverage
# Report output: target/pit-reports/index.html
```

---

## 5. Containerization & CI/CD Architecture

The repository utilizes an immutable GitHub Actions workflow triggered on pushes to the `main` branch. 

```text
[ Git Push ] 
     |
     +---> 1. GitGuardian (Secret Leak Detection)
     |
     +---> 2. Environment Provisioning (JDK 17 + Maven Cache)
     |
     +---> 3. Snyk (Dependency Security Audit)
     |
     +---> 4. Testing Phase (JUnit 5 + JaCoCo + PiTest)
     |
     +---> 5. Static Analysis (JDK 21 + SonarCloud SAST)
     |
     +---> 6. Docker Build & Push (DockerHub)
```

### Docker Deployment
The engine is containerized for highly reproducible deployments.

```bash
# Pull and run directly from DockerHub
docker pull moumenbouguessa/banking-ledger-engine:latest
docker run -d -p 8080:8080 --name banking-ledger moumenbouguessa/banking-ledger-engine:latest

# Or build locally
docker build -t banking-ledger-engine:local .
docker run -d -p 8080:8080 banking-ledger-engine:local
```

---

## 6. License
This project is open-source and available under the MIT License.