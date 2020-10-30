/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DB.Layers.DTO;

/**
 *
 * @author nguye
 */
public class Rank {
    int ID;
    String name;
    int scoreRequired;
    String description;

    public Rank() {
    }

    public Rank(int ID, String name, int scoreRequired, String description) {
        this.ID = ID;
        this.name = name;
        this.scoreRequired = scoreRequired;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScoreRequired() {
        return scoreRequired;
    }

    public void setScoreRequired(int scoreRequired) {
        this.scoreRequired = scoreRequired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
