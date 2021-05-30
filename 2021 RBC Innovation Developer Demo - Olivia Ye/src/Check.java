
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author oliviaye
 */
public class Check {

    private String name;
    private String course;
    private boolean valid;
    private boolean contains;
    private boolean misspelling;

    /**
     * Constructor -> sets all the verifiers to false
     * @param name              name of the food
     */
    public Check(String name) {
        this.name = name;
        valid = false;
        contains = false;
        misspelling = false;
        course = "nothing, yet";
    }

    /**
     *  Checks if the user input is only composed of numbers and spaces
     */
    public void validInput() {
        valid = true;
        boolean letter = false;
        String alphabet = "abcdefghijklmnopqrstuvwxyz ";
        for (int i = 0; i < name.length() && valid == true; i++) {
            letter = false;
            for (int n = 0; n < alphabet.length() && letter == false && valid == true; n++) {
                if (name.charAt(i) == alphabet.charAt(n)) {
                    letter = true;
                }
            }
            if (letter != true) {
                valid = false;
            }
        }
    }

    /**
     * Checks if the input is an appetizer, entree, or dessert 
     * @param appetizers        array of appetizers
     * @param entrees           array of entrees
     * @param desserts          array of desserts
     * @param type              click or manual input
     */
    public void checkCourse(String[] appetizers, String[] entrees, String[] desserts, char type) {
        boolean match = false;
        switch (type) {
            case 'c':
                match = checkIfMatch(appetizers, match, "Appetizer", 'c');
                if (match == false) {
                    match = checkIfMatch(entrees, match, "Entree", 'c');
                    if (match == false) {
                        match = checkIfMatch(desserts, match, "Dessert", 'c');
                    }
                }
                break;
            case 'i':
                match = checkIfMatch(appetizers, match, "Appetizer", 'i');
                if (match == false) {
                    match = checkIfMatch(entrees, match, "Entree", 'i');
                    if (match == false) {
                        match = checkIfMatch(desserts, match, "Dessert", 'i');
                    }
                }
                break;
        }
    }
    
    /**
     * Checks if the input is in the list of foods
     * @param food              the array of foods
     * @param match             whether the inputted food matches a food in the list
     * @param courseType        the food name's course type
     * @param type              list click or manual input       
     * @return 
     */
    public boolean checkIfMatch(String[] food, boolean match, String courseType, char type) {
        int size = food.length;
        for (int i = 0; i < size & match == false; i++) {
            switch (type) {
                case 'c':
                    if (food[i].compareTo(this.name) == 0) {
                        match = true;
                        setCourse(courseType);
                    }
                    break;
                case 'i':
                    // If the player manually inputted a food, the input is set to lower case as the input is not case sensitive
                    if (food[i].toLowerCase().compareTo(this.name) == 0) {
                        match = true;
                        setCourse(courseType);
                    }
                    break;
            }
        }
        return match;
    }
    
    /**
     * Checks if the manual input contains the food name (e.g cheese pizza)
     * @param appetizers        array of appetizers
     * @param entrees           array of entrees
     * @param desserts          array of desserts
     */
    public void checkIfContains(String[] appetizers, String[] entrees, String[] desserts) {
        int size = appetizers.length;
        // Checks if the input contains a food from the appetizers
        for (int i = 0; i < size & contains == false; i++) {
            if (name.contains(appetizers[i].toLowerCase()) == true) {
                contains = true;
                course = "Appetizer";
            }
        }
        // Checks if the input contains a food from the entrees
        if (contains == false) {
            size = entrees.length;
            for (int i = 0; i < size & contains == false; i++) {
                if (name.contains(entrees[i].toLowerCase()) == true) {
                    contains = true;
                    course = "Entree";
                }
            }
        }
        // Checks if the input contains a food from the desserts
        if (contains == false) {
            size = desserts.length;
            for (int i = 0; i < size & contains == false; i++) {
                if (name.contains(desserts[i].toLowerCase()) == true) {
                    contains = true;
                    course = "Dessert";
                }
            }
        }
    }
    
    /**
     * Checks if the manual input is one of the common misspellings
     * @param appetizers        array of appetizers
     * @param entrees           array of appetizers
     * @param desserts          array of desserts
     * @param misspellings      array containing misspellings for all foods
     */
    public void checkMisspellings(String[] appetizers, String[] entrees, String[] desserts, String[] misspellings) {
        // Compares against the misspellings of each food
        for (int i = 0; i < misspellings.length; i++) {
            String line = misspellings[i];
            StringTokenizer token = new StringTokenizer(line,";", false);
            token.nextToken();
            while(token.hasMoreTokens() == true) {
                // Sets the name to the correct food name if a misspelling is found
                if (name.compareTo(token.nextToken()) == 0) {
                    StringTokenizer token1 = new StringTokenizer(line,";", false);
                    name = token1.nextToken();
                    misspelling = true;
                }
            }
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the course
     */
    public String getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * @return the valid
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * @param valid the valid to set
     */
    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @return the contains
     */
    public boolean isContains() {
        return contains;
    }

    /**
     * @param close the close to set
     */
    public void setContains(boolean contains) {
        this.contains = contains;
    }

    /**
     * @return the misspelling
     */
    public boolean isMisspelling() {
        return misspelling;
    }

    /**
     * @param misspelling the misspelling to set
     */
    public void setMisspelling(boolean misspelling) {
        this.misspelling = misspelling;
    }
}
