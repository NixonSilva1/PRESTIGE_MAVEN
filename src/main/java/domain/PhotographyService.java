package domain;

public class PhotographyService extends AbstractService {
    
    public PhotographyService() {
        super("Photography", 800.0);
    }
    
    @Override
    public double calculatePrice() {
        return 800.0;
    }
    
    @Override
    public void addService(String serviceName, String serviceDescription) {
        // No necesario para servicios simples
    }
}