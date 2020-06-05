package app.manager;

import org.junit.Assert;
import org.junit.Test;

public class ScoreManagerTest {

    @Test
    public void checkIfHighScoreShouldBeTrue() {
        var lowerScore = ScoreManager.getInstance().getLowerScore();
        var newScore = lowerScore + 1;
        var result = ScoreManager.getInstance().checkHighScore(newScore);

        Assert.assertTrue(result);
    }
}
