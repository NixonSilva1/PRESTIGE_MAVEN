package domain;

public class DecorationService extends AbstractService {
    
    public DecorationService() {
        super("Decoration", 600.0);
    }
    
    @Override
    public double calculatePrice() {
        return 600.0;
    }
    
    @Override
    public void addService(String serviceName, String serviceDescription) {
        // No necesario para servicios simples
    }
}