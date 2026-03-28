# ineform/ineframe Migration Log — Java 25 Modernization

## 2026-03-28: Initial migration (WIP)

### Decisions

| Decision | Choice | Rationale | Revisit? |
|----------|--------|-----------|----------|
| ORM | Hibernate 7.0.0 replacing EclipseLink 2.7.0 | Only 5 files used EclipseLink-specific APIs | No |
| DI (server) | Guice 7.0.0 replacing Guice 3.0 | Backward-compatible upgrade, 2k+ files use Guice | No |
| DI (GWT client) | GIN replaced with Guice AbstractModule | GIN is dead, doesn't work on Java >11. Dagger 2 migration deferred. | Yes — migrate to Dagger 2 |
| GWT | 2.12.1 (org.gwtproject) with gwt-servlet-jakarta | Provides jakarta.servlet support | No |
| GWT dispatch | Custom JakartaStandardDispatchServlet | gwt-dispatch 1.1.0 uses javax.servlet, no jakarta version exists. Replacement is ~20 lines. | No |
| MongoDB driver | 5.6.4 + mongodb-driver-legacy for compat | PropDao uses BasicDBObject/JSON extensively. Full Document API rewrite deferred. | Yes — rewrite PropDao to Document API |
| Java target | 17 (stepping stone to 25) | GWT 2.12.1 may not support Java 25 source level | Yes — bump to 25 |

### Technical Debt Introduced

| Item | Location | What | Priority |
|------|----------|------|----------|
| GIN → Dagger 2 | `ineframe/client/gin/`, `ineform/client/gin/` | GIN modules converted to Guice AbstractModule. Works for compilation but won't run in GWT client. Need Dagger 2 for proper GWT DI. | Medium |
| MongoDB legacy shim | `ineform/pom.xml` | Using `mongodb-driver-legacy` for BasicDBObject/JSON compat. Should rewrite PropDao + BaseDao to use `org.bson.Document` API. | Low |
| gwt-dispatch dependency | `ineom/pom.xml`, `inei18n/pom.xml` | Still depends on gwt-dispatch 1.1.0 for Action/Result/Dispatch interfaces (plain POJOs, no servlet dependency). Could be internalized. | Low |
| EclipseLinkSLF4jLogger | `ineframe/server/di/jpa/` | Deprecated stub kept for backward compat. Delete once inetrack is migrated. | Low |
| CaptchaServlet | `ineframe/server/CaptchaServlet.java` | Rewritten to not extend simplecaptcha's javax servlet. Uses ImageIO directly. | None — done |

### Files Changed

- 35 files in initial commit (649e12b6)
- Key changes: POMs (5), javax→jakarta (24), GIN→Guice (3), EclipseLink removal (2), new file (1)

### Dependency Version Changes

| Dependency | Old | New |
|-----------|-----|-----|
| EclipseLink | 2.7.0 | **Removed** → Hibernate 7.0.0 |
| javax.persistence | 2.1.0 | jakarta.persistence-api 3.2.0 |
| Guice | 3.0 | 7.0.0 |
| GWT | 2.8.1 (com.google.gwt) | 2.12.1 (org.gwtproject) |
| GIN | 2.1.2 | **Removed** → Dagger 2.57 (deps added, migration pending) |
| SLF4J | 1.6.6 | 2.0.17 |
| Jackson | 2.4.0 | 2.18.3 |
| Guava | 22.0 | 33.4.0-jre |
| MongoDB driver | 3.8.0 (mongo-java-driver) | 5.6.4 (mongodb-driver-sync + legacy) |
| BouncyCastle | 1.46 (jdk16) | 1.83 (jdk18on) |
| Velocity | 1.6.2 (velocity) | 2.4.1 (velocity-engine-core) |
| Netty | 4.0.20.Final | 4.1.132.Final |
| Mockito | 1.9.0 | 5.23.0 |
| Commons IO | 2.0.1 | 2.18.0 |
| Commons FileUpload | 1.2.1 | 1.5 |
| Jukito | 1.1 | 1.5 |
| Maven Compiler Plugin | 3.2 | 3.13.0 |
| Maven Surefire | 2.16 | 3.5.2 |
| Java source/target | 1.8 | 17 |
