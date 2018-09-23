package validation.view;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 * This class is not used in this project
 */
@ManagedBean
public class ImageSwitchView {
    
    private List<String> images;
    
    @PostConstruct
    public void init(){
        images = new ArrayList<>();
        images.add("");
        images.add("");
        images.add("");
    }
    
    public List<String> getImages() {
        return images;
    }
    
}
