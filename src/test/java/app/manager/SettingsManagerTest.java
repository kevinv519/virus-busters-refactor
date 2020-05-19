package app.manager;

import org.junit.Assert;
import org.junit.Test;

public class SettingsManagerTest {

    @Test
    public void countSettingsShouldReturnFourSettings() {
        Assert.assertEquals(4, SettingsManager.getInstance().countAvailableSettings());
    }
}
