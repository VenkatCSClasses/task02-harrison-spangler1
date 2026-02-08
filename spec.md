## Overview

BankAccount models a single bank account with an email identifier and a monetary balance.  
The specification defines behavior only. No implementation details are prescribed.

## Design Principles

1. Deterministic behavior
Given the same inputs and state, all operations produce the same results.

2. Explicit validation 
Invalid inputs are rejected consistently and do not modify account state.

3. Two-decimal money model 
Monetary values are limited to at most two decimal places.

4. Atomic state updates
Operations that modify multiple accounts either complete fully or have no effect.

## Data Model

A BankAccount contains:
- an email identifier
- a numeric balance

The email is immutable after construction.  
The balance may change only through deposit, withdraw, and transfer.

## Money Rules

A value is considered a valid amount if all of the following are true:
- it is numeric
- it is finite
- it is greater than zero
- it has no more than two digits after the decimal point

Zero is not a valid amount for deposit, withdraw, or transfer.

The constructor permits an initial balance of zero as a special case.

### BankAccount(email, initialBalance)

Creates a new BankAccount instance.

Validation rules
- The email must satisfy isEmailValid.
- The initial balance must be zero or a valid amount.
- The initial balance must not have more than two decimal places.
- The initial balance must not be NaN or infinite.

Error behavior
- Throws IllegalArgumentException if any validation rule is violated.

Postconditions
- The stored email equals the input email.
- The stored balance equals the initial balance.

## getBalance

Returns the current balance.

Behavior
- Does not modify account state.

## getEmail

Returns the email associated with the account.

Behavior
- Returns the exact string provided to the constructor.
- Does not modify account state.

### withdraw(amount)

Removes funds from the account.

Validation rules
- The amount must be a valid amount.
- The amount must not exceed the current balance.

Behavior
- On success, decreases the balance by the given amount.

Error behavior
- Throws IllegalArgumentException if the amount is invalid.
- Throws InsufficientFundsException if the amount exceeds the balance.
- On error, the balance remains unchanged.

### deposit(amount)

Adds funds to the account.

Validation rules
- The amount must be a valid amount.

Behavior
- On success, increases the balance by the given amount.

Error behavior
- Throws IllegalArgumentException if the amount is invalid.
- On error, the balance remains unchanged.

### transfer(toAccount, amount)

Moves funds from this account to another account.

Validation rules
- The target account must not be null.
- The target account must not be the same object as the source account.
- The amount must be a valid amount.
- The source account must have sufficient funds.

Behavior
- On success, decreases the source balance and increases the target balance by the same amount.

Error behavior
- Throws IllegalArgumentException if the target account is null.
- Throws IllegalArgumentException if the target account is the same as the source.
- Throws IllegalArgumentException if the amount is invalid.
- Throws InsufficientFundsException if the amount exceeds the source balance.
- On error, neither account balance is modified.

### isEmailValid(email) → boolean

Determines whether an email string is valid according to assignment rules.

Validation rules
An email is valid if all of the following are true:
- it is not null
- it is not empty
- it contains exactly one at symbol
- it contains at least one character before the at symbol
- it contains a domain after the at symbol
- the domain contains at least one dot
- the final domain extension is at least two characters long
- the local part does not start with a dot
- the local part does not end with a dash
- the email does not contain consecutive dots
- domain labels do not start or end with a dash
- the domain does not contain illegal characters such as hash symbols

Behavior
- Returns true if all rules are satisfied.
- Returns false otherwise.

### isAmountValid(amount) → boolean

Determines whether a numeric value is a valid monetary amount.

Validation rules
An amount is valid if all of the following are true:
- it is numeric
- it is finite
- it is greater than zero
- it has no more than two decimal places

Behavior
- Returns true if all rules are satisfied.
- Returns false otherwise.

## Decimal Handling

Any value with more than two decimal places is treated as invalid input.

This rule applies to:
- constructor initial balances
- deposit amounts
- withdraw amounts
- transfer amounts

Invalid decimal precision results in IllegalArgumentException where applicable.

