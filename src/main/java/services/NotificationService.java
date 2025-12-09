package services;
import domain.INotification;

public class NotificationService implements INotification {
    public void send(String m,String e){
        System.out.println("Sending email to "+e+": "+m);
    }
}
