package beans;

import services.ResourceService;

public class ResourceServerController implements ResourceServerControllerMBean {
    private final ResourceService resourceService;

    public ResourceServerController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    @Override
    public int getAge() {
        return resourceService.getTestResourceAge();
    }
    
    @Override
    public String getName() {
        return resourceService.getTestResourceName();
    }
}
