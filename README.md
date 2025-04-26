# SauceDemo Test Automation Framework

This project is a test automation framework for [SauceDemo](https://www.saucedemo.com/) using Java 15, Selenium 4.12+, and TestNG.

## Features

- Page Object Model (POM) design pattern
- Parallel test execution
- Cross-browser testing (Chrome, Firefox)
- Automatic WebDriver management
- Test retry mechanism for flaky tests
- Extensive logging with Log4j2
- Comprehensive reporting with ExtentReports
- Automatic screenshots on test failure
- Docker support for containerized test execution
- CI/CD integration (GitHub Actions, GitLab CI, Jenkins)

## Prerequisites

- Java 15 or higher
- Maven 3.8+
- Docker (optional, for containerized execution)

## Project Structure

```
├── src
│   ├── main/java/com/saucedemo
│   │   ├── core          # Core framework components
│   │   ├── pages        # Page objects
│   │   ├── reporting    # Reporting utilities
│   │   └── utils        # Helper utilities
│   └── test
│       ├── java/com/saucedemo/tests  # Test classes
│       └── resources                 # Test configuration
├── .github/workflows    # GitHub Actions configuration
├── Dockerfile          # Docker configuration
├── docker-compose.yml  # Docker Compose configuration
├── Jenkinsfile        # Jenkins pipeline
└── .gitlab-ci.yml     # GitLab CI configuration
```

## Running Tests

### Local Execution

1. Clone the repository:
```bash
git clone <repository-url>
```

2. Run tests using Maven:
```bash
mvn clean test
```

To run specific test classes:
```bash
mvn clean test -Dtest=LoginTest
```

To run with a specific browser:
```bash
mvn clean test -Dbrowser=firefox
```

### Docker Execution

1. Build and run tests using Docker Compose:
```bash
./run-tests-docker.sh
```

Or manually:
```bash
docker-compose up --build
```

### CI/CD Execution

The framework supports multiple CI/CD platforms:

- **GitHub Actions**: Automatically runs on push and pull requests to main branch
- **GitLab CI**: Configured for build and test stages
- **Jenkins**: Uses Jenkinsfile for pipeline configuration

## Test Reports

After test execution, reports are generated in:

- Extent Reports: `target/extent-reports/test-report.html`
- TestNG Reports: `target/surefire-reports/index.html`
- Screenshots (on failure): `target/screenshots/`
- Logs: `target/logs/application.log`

## Configuration

Test configuration is managed through properties files:

- `src/test/resources/config.properties`: Base configuration
- `src/test/resources/config.dev.properties`: Environment-specific configuration

## Framework Components

### Page Objects

- Located in `src/main/java/com/saucedemo/pages`
- Each page has its own class with relevant elements and actions
- Extends `BasePage` for common functionality

### Test Classes

- Located in `src/test/java/com/saucedemo/tests`
- Organized by functionality (Login, Products, Cart, Checkout)
- Extends `BaseTest` for common test setup and teardown

### Utilities

- `DriverFactory`: WebDriver initialization and configuration
- `ConfigReader`: Test configuration management
- `ScreenshotUtils`: Capture screenshots on test failure
- `RetryAnalyzer`: Automatic retry for failed tests

### Reporting

- ExtentReports for detailed HTML reports
- TestNG reports for test execution summary
- Log4j2 for comprehensive logging
- Screenshots captured automatically on test failure

## Best Practices

1. **Test Independence**: Each test should be independent and not rely on other tests
2. **Page Object Pattern**: Maintain separation between test logic and page interactions
3. **Explicit Waits**: Use explicit waits instead of Thread.sleep()
4. **Logging**: Include appropriate logging statements for debugging
5. **Clean Code**: Follow Java coding standards and maintain clean, readable code

## Contributing

1. Create a feature branch
2. Commit your changes
3. Push to the branch
4. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details