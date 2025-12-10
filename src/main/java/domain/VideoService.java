package domain;

public class VideoService extends AbstractService {
    
    public VideoService() {
        super("Video", 1000.0);
    }
    
    @Override
    public double calculatePrice() {
        return 1000.0;
    }
    
    @Override
    public void addService(String serviceName, String serviceDescription) {
        // No necesario para servicios simples
    }
}