package com.c.refactoring.movie;

import com.c.refactoring.StringUtils;

import java.util.Arrays;
import java.util.List;

public class Movie {

    String rating;
    List<String> BLIST = Arrays.asList("B1", "B2", "B3", "B4");

    public Movie(String rating) {
        super();
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    /*Axx or By
    Where x represents any digit between 0 and 9, and y represents 
    any digit between 1 and 4*/
    public boolean isValidRating() {
        if (rating == null) {
            return false;
        }
        if (isValidARating()) {
            return true;
        }
        return isValidBRating();
    }

    private boolean isValidBRating() {
        return BLIST.contains(rating);
    }

    private boolean isValidARating() {

        if (this.getRating().substring(0, 1).equalsIgnoreCase("A")
                && this.getRating().length() == 3
                && StringUtils.isNumeric(this.getRating().substring(1, 3))) {
            return true;
        }
        return false;
    }


    public void setRating(String rating) {
        this.rating = rating;
    }
}
