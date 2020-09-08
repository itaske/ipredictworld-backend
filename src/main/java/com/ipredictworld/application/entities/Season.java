package com.ipredictworld.application.entities;


import lombok.Data;

import java.io.Serializable;

@Data
public class Season implements Serializable, Comparable {

    int yearStarted;
    int yearEnded;

    @Override
    public String toString(){
        return String.format("%d/%d",yearStarted,yearEnded);
    }

    public static Season convertStringToSeason(String value){
        String yearStarted = value.substring(0, value.indexOf("/"));
        String yearEnded = value.substring(value.indexOf("/")+1, value.length());

        Season season = new Season();
        season.setYearStarted(Integer.parseInt(yearStarted));
        season.setYearEnded(Integer.parseInt(yearEnded));
        return season;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof Season){
            Season s = (Season) o;
            return s.yearStarted == this.yearStarted ? Integer.compare(s.yearEnded,this.yearEnded) : this.yearStarted - s.yearStarted;
        } else{
            return -1;
        }
    }
}
