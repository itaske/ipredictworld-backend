package com.ipredictworld.application.response;

import com.ipredictworld.application.entities.Score;
import com.ipredictworld.application.entities.ScoreBoard;

import java.util.HashMap;
import java.util.Map;

public class ScoreBoardResponse implements Response{

    public Map<String,String> scoreBoard;

    public ScoreBoardResponse(ScoreBoard scoreBoard){
        this.scoreBoard = new HashMap<>();
        this.scoreBoard.put("id", scoreBoard.getId());
        this.scoreBoard.put("username", scoreBoard.getUser().getUsername());
        this.scoreBoard.put("points", ""+(int)(scoreBoard.getCurrentScore()+scoreBoard.getInitialScore()));
        this.scoreBoard.put("noOfPerfectScore", ""+scoreBoard.getNumberOfPerfectScore());
    }
}
