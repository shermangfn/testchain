/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.samrodriguez.testchain;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 *
 * @author samuelrodriguez
 */
public class TestChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 3;
    
    public static void main(String[] args) {

        //add our blocks to the blockchain ArrayList:
        blockchain.add(new Block("Genesis block", "0"));
        System.out.println("Trying to Mine block 1... ");
	blockchain.get(0).mineBlock(difficulty);
        blockchain.add(new Block("This is the second block",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to Mine block 2... ");
	blockchain.get(1).mineBlock(difficulty);
        blockchain.add(new Block("This is the third block",blockchain.get(blockchain.size()-1).hash));
        System.out.println("Trying to Mine block 3... ");
	blockchain.get(2).mineBlock(difficulty);
        
        System.out.println("\nBlockchain should be valid: " + isChainValid());
        System.out.println("\n");
        blockchain.get(1).change("Some changed data");
        System.out.println("\nBlockchain should not be valid: " + isChainValid());
        
        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);		
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);

    }
    
    public static Boolean isChainValid() {
	Block currentBlock; 
	Block previousBlock;
	
	//loop through blockchain to check hashes:
	for(int i=1; i < blockchain.size(); i++) {
		currentBlock = blockchain.get(i);
		previousBlock = blockchain.get(i-1);
                String hashTarget = new String(new char[difficulty]).replace('\0', '0');
                
		//compare registered hash and calculated hash:
		if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                    System.out.println("Current Hashes not equal");			
                    return false;
		}
		//compare previous hash and registered previous hash
		if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                    System.out.println("Previous Hashes not equal");
                    return false;
		}
                //check if hash is solved
                if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                    System.out.println("This block hasn't been mined");
                    return false;
                }
	}
	return true;
    }
}
