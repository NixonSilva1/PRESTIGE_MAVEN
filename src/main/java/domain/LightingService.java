package domain;

public class LightingService extends AbstractService {
    
    public LightingService() {
        super("Lighting", 400.0);
    }
    
    @Override
    public double calculatePrice() {
        return 400.0;
    }
    
    @Override
    public void addService(String serviceName, String serviceDescription) {
        // No necesario para servicios simples
    }
}