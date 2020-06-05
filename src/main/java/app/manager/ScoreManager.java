package app.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScoreManager {
    private static final Logger logger = LogManager.getLogger(ScoreManager.class);
    private static final int LIST_SIZE = 10;
    private static final String FILENAME = ".score";

    private final int[] scores;
    private final String[] names;

    private int lowerScoreIndex;

    private ScoreManager() {
        scores = new int[LIST_SIZE];
        names = new String[LIST_SIZE];
        initArrays();
        loadScores();
    }

    private static class ScoreManagerHelper {
        private static final ScoreManager INSTANCE = new ScoreManager();
    }

    public static ScoreManager getInstance() {
        return ScoreManagerHelper.INSTANCE;
    }

    private void initArrays() {
        for (int i = 0; i < LIST_SIZE; i++) {
            names[i] = "N/A";
            scores[i] = 0;
        }
    }

    private void loadScores() {
        var file = new File(FILENAME);
        try {
            var scanner = new Scanner(file);
            var i = 0;
            while (scanner.hasNext() && i < LIST_SIZE) {
                var line = scanner.nextLine();
                var score = line.split("=");
                names[i] = score[0];
                scores[i] = Integer.parseInt(score[1]);
                i++;
            }
            i = i == LIST_SIZE ? i - 1 : i;
            lowerScoreIndex = i;
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error("Could not load file " + FILENAME, ex);
        }
    }

    private void writeScores() {
        try {
            var printWriter = new PrintWriter(FILENAME);
            for (int i = 0; i < LIST_SIZE && scores[i] > 0; i++) {
                printWriter.println(names[i] + "=" + scores[i]);
            }
            printWriter.close();
        } catch (FileNotFoundException ex) {
            logger.error("Could not write file " + FILENAME, ex);
        }
    }

    public String[] getNames() {
        return names;
    }

    public int[] getScores() {
        return scores;
    }

    public int getLowerScore() {
        return scores[lowerScoreIndex];
    }

    public int getLowerScoreIndex() {
        return lowerScoreIndex;
    }

    public boolean checkHighScore(int score) {
        return score >= scores[lowerScoreIndex];
    }

    public void insertScore(String name, int score) {
        int i;
        for (i = lowerScoreIndex; i >= 0; i--) {
            if (score >= scores[i]) {
                if (i < LIST_SIZE - 1) {
                    scores[i + 1] = scores[i];
                    names[i + 1] = names[i];
                }
            } else break;
        }
        scores[i+1] = score;
        names[i+1] = name.substring(0, Math.min(name.length(), 5));
        if (lowerScoreIndex < LIST_SIZE - 1) {
            lowerScoreIndex++;
        }
        writeScores();
    }
}
