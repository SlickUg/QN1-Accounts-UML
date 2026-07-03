class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class PaymentSystem {
    private double balance = 50000.0; 

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > this.balance) {
            throw new InsufficientFundsException("Error: Balance is too low for this withdrawal.");
        }
        this.balance -= amount;
        System.out.println("Withdrawal successful! Remaining balance: UGX " + this.balance);
    }
}

class Main {
    public static void main(String[] args) {
        System.out.println("--- Testing Custom Exception ---");
        PaymentSystem payment = new PaymentSystem();

        try {
            System.out.println("Attempting to withdraw UGX 20000...");
            payment.withdraw(20000);
            
            System.out.println("\nAttempting to withdraw UGX 40000...");
            payment.withdraw(40000);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
