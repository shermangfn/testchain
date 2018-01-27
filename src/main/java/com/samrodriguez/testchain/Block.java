/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samrodriguez.testchain;

import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author samuelrodriguez
 */
public class Block {
    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private long timeStamp; //as number of milliseconds since 1/1/1970.
    private int nonce;
    
    //Block Constructor.
    public Block(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        
        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }
    
    public String calculateHash() {
        String calculatedhash = StringUtil.applySha256( 
                        previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +        
                        merkleRoot 
                        );
        return calculatedhash;
    }
    
    public void mineBlock(int difficulty) {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
        while(!hash.substring( 0, difficulty).equals(target)) {
            ++nonce;
            hash = calculateHash();
        }
        System.out.println("Block Mined: " + hash);
    }
    
    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;		
        if((previousHash != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
