import java.util.HashMap;
import java.util.Map;

/**
 * Created by vongrad on 3/12/17.
 */
public class EvaluationScore {

    private Map<Integer, Integer> us;
    private Map<Integer, Integer> oponent;

    private Integer playerId;
    private Integer oponentId;

    public EvaluationScore(Integer playerId, Integer oponentId) {
        this.playerId = playerId;
        this.oponentId = oponentId;

        this.us = new HashMap<>();
        this.oponent = new HashMap<>();

    }

    public void addCount(Integer[] possibility, int playerId) {

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
}
