package services;
import domain.*;

public class PaymentService {
    private IPaymentProcessor processor;
    private INotification notifier;

    public PaymentService(IPaymentProcessor p, INotification n){
        processor = p; notifier = n;
    }

    public void executePayment(User u, double amount){
        processor.process(amount);
        notifier.send("Payment confirmed for $" + amount, u.getEmail());
    }
}
