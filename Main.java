import java.util.ArrayList;
import java.util.List;

interface Statement {
    String generateStatement();
}

abstract class Account implements Statement {
    protected String accountNo;
    protected double balance;

    public Account(String accountNo, double opening) {
        this.accountNo = accountNo;
        this.balance = opening;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public abstract boolean withdraw(double amount);

    public double getBalance() {
        return this.balance;
    }

    @Override
    public String generateStatement() {
        return "Account Number: " + this.accountNo + ", Current Balance: UGX " + this.balance;
    }
}

class SavingsAccount extends Account {
    private double rate;

    public SavingsAccount(String accountNo, double opening, double rate) {
        super(accountNo, opening);
        this.rate = rate;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (this.balance - amount) >= 0) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public void addInterest() {
        double interest = this.balance * (this.rate / 100.0);
        this.balance += interest;
    }
}

class CurrentAccount extends Account {
    private double overdraft;

    public CurrentAccount(String accountNo, double opening, double overdraft) {
        super(accountNo, opening);
        this.overdraft = overdraft;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && (this.balance - amount) >= -this.overdraft) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}

class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        if (account != null) {
            this.accounts.add(account);
        }
    }

    public double totalWorth() {
        double total = 0.0;
        for (Account account : accounts) {
            total += account.getBalance();
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Running Banking System Test ===");

        Customer customer = new Customer("Simon Ssemugera Emmanuel");

        SavingsAccount savings = new SavingsAccount("UG-1001", 500.0, 10.0);
        savings.deposit(100.0);
        System.out.println("Savings " + savings.generateStatement());
        
        boolean svCheck = savings.withdraw(800.0);
        System.out.println("Withdraw 700 UGX from Savings? " + (svCheck ? "Success" : "Refused (Cannot go below zero)"));

        savings.addInterest();
        System.out.println("Savings after interest: " + savings.generateStatement());

        CurrentAccount current = new CurrentAccount("UG-2002", 100.0, 200.0);
        
        boolean cuCheck = current.withdraw(250.0);
        System.out.println("Withdraw 250 UGX from Current? " + (cuCheck ? "Success (Overdraft used)" : "Refused"));
        System.out.println("Current " + current.generateStatement());

        customer.addAccount(savings);
        customer.addAccount(current);

        System.out.println("\nCustomer Total Worth: UGX " + customer.totalWorth());
        System.out.println("===================================");
    }
}