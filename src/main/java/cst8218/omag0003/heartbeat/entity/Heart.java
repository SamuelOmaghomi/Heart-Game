/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cst8218.omag0003.heartbeat.entity;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * This contains all the business layer implementation of the game
 * @author samue
 */
@Entity
public class Heart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private static final int INITIAL_SIZE = 50;
    public static final int CHANGE_RATE = 10;
    public static final int CONTRACTION_DECREMENT = 7;
    public static final int BEAT_INCREMENT = 200;
    private static final int BEATS_TO_EXHAUSTION = 200;
    private static final int SHRINK_DECREMENT = 2;
    public static final int STOP_SIZE= 10;
    private static final int X_MAX = 200;
    private static final int Y_MAX = 200;
    private static final int  SIZE_MAX = 300;
    private static final int CONTRACTED_MAX = 300;
   
    @Min(0)
    @Max(X_MAX)
    @NotNull
    private Integer x;
    
    @Min(0)
    @Max(Y_MAX)
    @NotNull
    private Integer y;
    
    @Min(1)
    @Max(SIZE_MAX)
    @NotNull
    private Integer size;
    
    @Min(1)
    @Max(CONTRACTED_MAX)
    @NotNull
    private Integer contractedSize;
    
    @Min(0)
    @Max(BEATS_TO_EXHAUSTION)
    @NotNull
    private Integer beatCount;
    
    public Heart(){}
    
    /**
     * Creates a new heart with default values
     * @param fromController 
     */
    public Heart(boolean fromController){
        if(fromController == true){
            size = INITIAL_SIZE;
            contractedSize = INITIAL_SIZE;
            beatCount = 0;
            x = 0;
            y = 0;
        }
    }
    
    /**
     * Creates a new heart with all the values of another heart 
     * @param heart 
     */
    public Heart(Heart heart){
        this.setBeatCount(heart.getBeatCount());
        this.setContractedSize(heart.getContractedSize());
        this.setId(heart.getId());
        this.setSize(heart.getSize());
        this.setX(heart.getX());
        this.setY(heart.getY());
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getContractedSize() {
        return contractedSize;
    }

    public void setContractedSize(Integer contractedSize) {
        this.contractedSize = contractedSize;
    }

    public Integer getBeatCount() {
        return beatCount;
    }

    public void setBeatCount(Integer beatCount) {
        this.beatCount = beatCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Heart)) {
            return false;
        }
        Heart other = (Heart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.omag0003.heartbeat.Heart[ id=" + id + " ]";
    }
    
    /** 
     * Updates the properties to simulate the passing of one unit of time.
     */
    public void advanceOneTimeIncrement() {
        if (stillBeating()){                    //if still beating
            if (finishedCurrentBeat()){            //if size has decreased to contracted size
                newBeat();                             //suddenly increase size to begin new beat
                setBeatCount(getBeatCount()+1);      //increment beat count
                if (exhausted()){                      //if beat count has reached exhausted level
                    shrink();                               //descrease contracted size
                    setBeatCount(0);                        //now not exhausted - reset beat count
                }
            } else {                               //else 
                continueContracting();                 //continue the contracting phase of a beat
            }
        }
    }
    /* Returns true if the Heart has not yet stopped and is still beating
    */
    public boolean stillBeating(){
        return getContractedSize() > STOP_SIZE;
    }
    
    /*
    Returns true if the size has decreased to contracted size
    */
    private boolean finishedCurrentBeat(){
        return getSize() <= getContractedSize();
    }
    
    /*
    Increments the size to begin a new beat
    */
    public void newBeat(){
        setSize(getSize() + BEAT_INCREMENT);
    }
    
    /*
    Checks if the beat count has reached the maximum
    */
    private boolean exhausted(){
        return getBeatCount() >= BEATS_TO_EXHAUSTION;
    }
    
    /*
    Decreases the contracted size
    */
    private void shrink(){
        setContractedSize(getContractedSize()-SHRINK_DECREMENT);
    }
    
    /*
    Reduce to size of heart
    */
    public void continueContracting(){
        setSize(getSize()-CONTRACTION_DECREMENT);
    }
    
    /*
    Updates the values of the current heart to the heart passed if the value isn't null
    */
    public void updates(Heart newHeart){
        if(newHeart.getX() != null){
            this.setX(newHeart.getX());
        }
        
        if(newHeart.getY() != null){
            this.setY(newHeart.getY());
        }
        
        if(newHeart.getContractedSize() != null){
            this.setContractedSize(newHeart.getContractedSize());
        }
        
        if(newHeart.getSize() != null){
            this.setSize(newHeart.getSize());
        }
               
        if(newHeart.getBeatCount() != null){
            this.setBeatCount(newHeart.getBeatCount());
        }
        
    }
    
    /*
     * Sets the default values of a heart entity if they are null
     * @param heart
     * @return 
     */
    public static Heart setDefaultValues(Heart h){
        Heart heart = new Heart(h);
        if(heart.getBeatCount() == null){
           heart.setBeatCount(0); 
        }
        
        if(heart.getX() == null){
            heart.setX(0);
        }
        
        if(heart.getY() == null){
            heart.setY(0);
        }
        
        if(heart.getContractedSize() == null){
            heart.setContractedSize(INITIAL_SIZE);
        }
        
        if(heart.getSize() == null){
            heart.setSize(INITIAL_SIZE);
        } 
        return heart;
    }

}
