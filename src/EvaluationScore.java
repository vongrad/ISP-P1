import java.util.HashMap;
import java.util.Map;

/**
 * Created by vongrad on 3/12/17.
 */
public class EvaluationScore {

    private Map<Integer, Integer> us;
    private Map<Integer, Integer> oponent;

    private Map<Integer, Integer> scoreMapping;

    private Integer playerId;
    private Integer oponentId;

    public EvaluationScore(Integer playerId, Integer oponentId) {
        this.playerId = playerId;
        this.oponentId = oponentId;

        this.us = new HashMap<>();
        this.oponent = new HashMap<>();
        this.scoreMapping = initializeScoringMap();

    }

    public void addCount(Integer[] possibility, Integer playerId) {

        int key = Integer.parseInt(possibilityToString(possibility), 2);

        // Us
        if(this.playerId == playerId) {
            us.put(key, (us.get(key) == null ? 0 : us.get(key)) + 1);
        }
        // Opponent
        else {
            oponent.put(key, (oponent.get(key) == null ? 0 : oponent.get(key)) + 1);
        }
    }

    public Map<Integer, Integer> initializeScoringMap() {

        Map<Integer, Integer> score = new HashMap<>();

        score.put(0, 0);        // 0000
        score.put(1, 10);       // 1000
        score.put(2, 30);       // 0100
        score.put(3, 50);       // 1100
        score.put(4, 30);       // 0010
        score.put(5, 40);       // 1010
        score.put(6, 75);       // 0110
        score.put(7, 1000);     // 1110
        score.put(8, 10);       // 0001
        score.put(9, 30);       // 1001
        score.put(10, 30);      // 0101
        score.put(11, 1000);    // 1101
        score.put(12, 50);      // 0011
        score.put(13, 1000);    // 1011
        score.put(14, 1000);    // 0111
        score.put(15, 1000);    // 1111

        return score;
    }

    /**
     * Convert array to string
     * @param possibility
     * @return
     */
    public String possibilityToString(Integer[] possibility) {
        StringBuilder sb = new StringBuilder();

        for (Integer i: possibility) {
            if(i == null) {
                sb.append(0);
            }
            else{
                sb.append(1);
            }
        }
        return sb.toString();
    }

    public double evaluateState() {

        if(hasWinningState()) {
            return 0.99999;
        }

        if(hasLosingState()) {
            return -0.99999;
        }

        double usSum = 0.0;

        for (Map.Entry<Integer, Integer> entry : us.entrySet()) {

            Integer key = entry.getKey();
            Integer count = entry.getValue();

            Integer score = scoreMapping.get(key);
            usSum += count * score;
        }

        double usProportion = usSum / Double.MAX_VALUE;

        double opponentSum = 0.0;

        for (Map.Entry<Integer, Integer> entry : oponent.entrySet()) {

            Integer key = entry.getKey();
            Integer count = entry.getValue();

            Integer score = scoreMapping.get(key);
            opponentSum += count * score;
        }

        double opponentProportion = opponentSum / Double.MAX_VALUE;
//
//        if(usSum == opponentSum) {
//            return 0;
//        }
//        else if(usSum > opponentSum) {
//            return 1 - (opponentSum / usSum);
//        }
//        else {
//            return -1  + (usSum / opponentSum);
//
//        }

        return usProportion - opponentProportion;
    }

    public boolean hasWinningState() {

        for (Map.Entry<Integer, Integer> entry : scoreMapping.entrySet()) {

            Integer key = entry.getKey();
            Integer score = entry.getValue();

            if(score == 1000) {
                if(us.get(key) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasLosingState() {

        int losingCount = 0;

        for (Map.Entry<Integer, Integer> entry : scoreMapping.entrySet()) {

            Integer key = entry.getKey();
            Integer score = entry.getValue();

            if(score == 1000) {
                if(oponent.get(key) != null) {
                    losingCount++;

                    if(losingCount == 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
