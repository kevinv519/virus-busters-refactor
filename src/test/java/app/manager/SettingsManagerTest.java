package app.manager;

import org.junit.Assert;
import org.junit.Test;

public class SettingsManagerTest {

    @Test
    public void countSettingsShouldReturnFourSettings() {
        var settingsManager = new SettingsManager();

        Assert.assertEquals(4, settingsManager.countAvailableSettings());
    }
}
