package domain;

public class MusicService extends AbstractService {
    
    public MusicService() {
        super("Music", 1200.0);
    }
    
    @Override
    public double calculatePrice() {
        return 1200.0;
    }
    
    @Override
    public void addService(String serviceName, String serviceDescription) {
        // No necesario para servicios simples
    }
}