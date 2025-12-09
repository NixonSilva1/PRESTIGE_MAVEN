package services;
import domain.IPaymentProcessor;

public class OnlinePaymentProcessor implements IPaymentProcessor {
    public void process(double amount){
        System.out.println("Online payment processed: $"+amount);
    }
}
