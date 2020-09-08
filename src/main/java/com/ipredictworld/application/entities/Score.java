package com.ipredictworld.application.entities;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class Score implements Serializable {

    final static String SCORE_SEPARATOR = ":";

    @Min(0)
    private int home;

    @Min(0)
    private int away;

    public String toString(){
        return String.format("%d%s%d",home,SCORE_SEPARATOR, away);
    }

    public static Score covertFromStringToScore(String scoreString){

        if (scoreString != null && StringUtils.hasText(scoreString)){
            String home = scoreString.substring(0, scoreString.indexOf(SCORE_SEPARATOR));
            String away = scoreString.substring(scoreString.indexOf(SCORE_SEPARATOR)+1, scoreString.length());
            Score score = new Score();
            score.setHome(Integer.parseInt(home));
            score.setAway(Integer.parseInt(away));
            return score;
        }
        Score score = new Score();
        score.setAway(0);
        score.setHome(0);
        return score;
    }

}


