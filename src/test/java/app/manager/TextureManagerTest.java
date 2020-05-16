package app.manager;

import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class TextureManagerTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Test
    public void CreateTextureManagerShouldLoadAllImages() {
        var textureManager = new TextureManager();
        var size = textureManager.countAvailableImages();
        Assert.assertEquals(27, size);
    }

    @Test
    public void LoadResourceShouldBeNotNull() {
        var textureManager = new TextureManager();
        var image = textureManager.getImage("logo");
        Assert.assertNotNull(image);
    }
}
