package com.rainyalley.architecture.transaction.tpc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/** 
* MasterNode Tester. 
*/ 
@RunWith(BlockJUnit4ClassRunner.class)
public class MasterNodeTest {

    private MasterNode masterNode;

    private List<Node> slaves = new ArrayList<Node>();

    @Before
    public void before() throws Exception {
        masterNode = new MasterNode();

        for (int i = 0; i < 5; i++) {
            Node n = new Node("slave-"+i);
            slaves.add(n);
        }
        masterNode.setSlaves(slaves);
    } 

    @After
    public void after() throws Exception { 
    } 

    /** 
    * 
    * Method: prepare() 
    * 
    */ 
    @Test
    public void testPrepare() throws Exception {
        masterNode.prepare();
        Thread.sleep(5000);
    } 

    /** 
    * 
    * Method: commit() 
    * 
    */ 
    @Test
    public void testCommit() throws Exception {
        boolean prepare = masterNode.prepare();
        if(prepare){
            masterNode.commit();
        } else {
            masterNode.abort();
        }




        Thread.sleep(5000);
    } 

    /** 
    * 
    * Method: abort() 
    * 
    */ 
    @Test
    public void testAbort() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
    * 
    * Method: setSlaves(List<Node> slaves) 
    * 
    */ 
    @Test
    public void testSetSlaves() throws Exception { 
        //TODO: Test goes here... 
    } 

    /** 
    * 
    * Method: call() 
    * 
    */ 
    @Test
    public void testCall() throws Exception { 
        //TODO: Test goes here... 
    } 









// private methods ~~~~







} 
