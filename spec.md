# spec.md — BankAccount

## Purpose
The BankAccount represents a single bank account with:
- an email used as the account identifier
- a balance representing money

## Money rules used everywhere
Whenever this spec refers to a valid amount, it means:
- the value is numeric and finite
- the value is greater than zero
- the value has no more than two digits after the decimal point

Examples:
- valid values: 1, 12.3, 99.99, 0.01
- invalid values: 0, -1, 25.999, NaN, Infinity

One special case:
- the constructor is allowed to start with an initial balance of 0.00

### 1. Constructor
BankAccount email: String, initialBalance: Number

Creates a new account and stores the email and starting balance.

What it must do:
- Store the email exactly as provided
- Initialize the balance using the given starting value

Validation:
- The email must pass isEmailValid
- The initial balance must either be exactly zero or be a valid amount under the money rules

Errors:
- Throw IllegalArgumentException if:
  - the email is invalid, null, or empty
  - the initial balance is negative
  - the initial balance has more than two decimal places
  - the initial balance is NaN or Infinity

### 2. getBalance
Returns the current balance.

### 3. getEmail
Returns the email associated with the account. The returned value must be exactly the same string that was passed to the constructor.

### 4. withdraw
withdraw amount: Number

Attempts to remove money from the account.

Validation:
- The amount must be a valid amount under the money rules

If successful:
- Decrease the balance by the given amount

If it fails:
- Throw IllegalArgumentException if the amount is invalid, zero, NaN, Infinity, or has more than two decimal places
- Throw InsufficientFundsException if the amount is greater than the current balance
- The balance must remain unchanged if an exception is thrown

### 5. deposit
deposit amount: Number

Adds money to the account.

Validation:
- The amount must be a valid amount under the money rules

If successful:
- Increase the balance by the given amount

If it fails:
- Throw IllegalArgumentException if the amount is invalid, zero, NaN, Infinity, or has more than two decimal places
- The balance must remain unchanged if an exception is thrown

### 6. transfer
transfer toAccount: BankAccount, amount: Number

Moves money from this account to another account.

Validation:
- The target account must not be null
- The target account must not be the same object as the source account
- The amount must be a valid amount under the money rules
- The source account must have sufficient funds

If successful:
- Subtract the amount from the source account
- Add the amount to the target account

If it fails:
- Throw IllegalArgumentException if:
  - the target account is null
  - the target account is the same as the source account
  - the amount is invalid, zero, NaN, Infinity, or has more than two decimal places
- Throw InsufficientFundsException if the amount is greater than the source balance

Atomicity:
- Transfers must be all or nothing
- If the transfer fails, neither account balance may change

### 7. isEmailValid
isEmailValid email: String returns boolean

This is a simple email validator designed to match the Task01 tests, not a full internet email specification.

Minimum rules:
- the email must not be null or empty
- the email must contain exactly one at symbol
- there must be at least one character before the at symbol
- there must be a domain after the at symbol
- the domain must contain at least one dot
- the final domain extension must be at least two characters long
- reject patterns identified in the tests:
  - the local part cannot start with a dot
  - the local part cannot end with a dash
  - consecutive dots are not allowed
  - domain labels cannot start or end with a dash
  - illegal characters such as hash symbols are not allowed in the domain

### 8. isAmountValid
isAmountValid amount: Number returns boolean

Returns true only if:
- the amount is numeric and finite
- the amount is greater than zero
- the amount has no more than two decimal places

Returns false otherwise.

Note:
- isAmountValid returns false for zero
- the constructor separately allows a starting balance of zero

## Decimal handling note
Any value with more than two decimal places must be treated as invalid input and must result in an IllegalArgumentException when used in the constructor, deposit, withdraw, or transfer.
