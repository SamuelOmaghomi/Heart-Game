/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import cst8218.omag0003.heartbeat.entity.Heart;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author samue
 */
public class TestHeartbeat {
    
    public TestHeartbeat() {
    }

//    @org.junit.jupiter.api.BeforeAll
//    public static void setUpClass() throws Exception {
//    }
//
//    @org.junit.jupiter.api.AfterAll
//    public static void tearDownClass() throws Exception {
//    }
//
//    @org.junit.jupiter.api.BeforeEach
//    public void setUp() throws Exception {
//    }
//
//    @org.junit.jupiter.api.AfterEach
//    public void tearDown() throws Exception {
//    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testNewBeat() {
         Heart heart = new Heart(true);
         int referenceSize = heart.getSize();   //size before newbeat
         heart.newBeat();   //run new beat method
         assertEquals(heart.getSize(),(referenceSize + Heart.BEAT_INCREMENT));  //check if the size incremented by the beat Increment    
     }
     
     @Test
     public void testContinueContracting() {
         Heart heart = new Heart(true);
         int referenceSize = heart.getSize();   //size before contraction
         heart.continueContracting();   //run continue contracting method
         assertEquals(heart.getSize(),(referenceSize - Heart.CONTRACTION_DECREMENT));  //check if the size decremented by the contraction decrement   
     }
     
     @Test
     public void testStillBeating() {
         Heart heart = new Heart(true);
         int referenceContractedSize = heart.getContractedSize(); //contracted size
         assertEquals(heart.stillBeating(), referenceContractedSize > Heart.STOP_SIZE); //check if the still beating method correctly compares the contracted size to the stop size
     }
}
