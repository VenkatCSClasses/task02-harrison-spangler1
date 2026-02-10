# Bank Account Project

## Overview

The BankAccount project implements a simple bank account model with an email identifier and a monetary balance. The `BankAccount` class provides methods for managing the account, including depositing, withdrawing, and transferring funds, while ensuring that all operations adhere to strict validation rules.

## Features

- **Email Identifier**: Each bank account is associated with a unique email address.
- **Monetary Balance**: The account maintains a balance that can be modified through deposits and withdrawals.
- **Validation**: All operations include validation to ensure that inputs are correct and that the account state remains consistent.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Building the Project

To build the project, navigate to the project directory and run:

```
mvn clean install
```

This command compiles the Java code and packages the application.

### Running Tests

To execute the tests, use the following command:

```
mvn test
```

This will run all unit tests defined in the `BankAccountTest.java` file, ensuring that the `BankAccount` class behaves as expected.

## Testing Framework

The project uses JUnit 5 as the testing framework. Each test case is designed to validate the functionality of the `BankAccount` class according to the specifications outlined in the project documentation.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.