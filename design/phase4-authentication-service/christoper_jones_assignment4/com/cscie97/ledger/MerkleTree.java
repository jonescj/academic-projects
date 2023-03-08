package com.cscie97.ledger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/*
Hash trees can be used to verify any kind of data stored, handled and transferred in and between computers.
They can help ensure that data blocks received from other peers in a peer-to-peer network are received
undamaged and unaltered, and even to check that the other peers do not lie and send fake blocks.
Resource: https://www.youtube.com/watch?v=_hGa1jfx584&ab_channel=GlobalSoftwareSupport
*/
public class MerkleTree {
    // The transactions are the list of data elements to be hashed by the MerkleTree
    private List<String> transactions = new ArrayList<String>();

    // Hash trees can be used to verify any kind of data stored, handled and transferred in and between computers.
    // They can help ensure that data blocks received from other peers in a peer-to-peer network are received
    // undamaged and unaltered, and even to check that the other peers do not lie and send fake blocks.
    // Resource: https://www.youtube.com/watch?v=_hGa1jfx584&ab_channel=GlobalSoftwareSupport
    public MerkleTree(){}

    // Get the root of the MerkleTree to verify the unique address of data elements
    public List<String> getRoot() throws NoSuchAlgorithmException {
        return construct(this.transactions);
    }

    // Hash and add a data element to the transaction list to be referenced later
    public void add(String transaction) throws NoSuchAlgorithmException {
        this.transactions.add(toSHA256(transaction));
    }

    // Construct a list of hashed transactions
    private List<String> construct(List<String> transactions) throws NoSuchAlgorithmException {
        if(transactions.size()==1){
            return transactions;
        }

        List<String> updatedList = new ArrayList<>();
        for(int i = 0; i < transactions.size()-1; i+=2){
            updatedList.add(mergedHash(transactions.get(i), transactions.get(i+1)));
        }

        if(transactions.size() % 2 == 1){
            updatedList.add(mergedHash(transactions.get(transactions.size()-1), transactions.get(transactions.size()-1)));
        }

        return construct(updatedList);
    }

    // Merged two hashes together and convert them to SHA256
    private String mergedHash(String hash1, String hash2) throws NoSuchAlgorithmException {
        String hash = hash1 + hash2;
        return toSHA256(hash);
    }

    // Convert a given string value to a SHA256 hexadecimal string
    private String toSHA256(String value) throws NoSuchAlgorithmException {

        // Resource: https://www.baeldung.com/sha-256-hashing-java
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));

        StringBuffer hexString = new StringBuffer();
        for(int i = 0; i < hash.length; i++){
            String currentHexValue = Integer.toHexString(0xff & hash[i]);
            if(currentHexValue.length() == 1){
                hexString.append(currentHexValue);
            }
        }
        return hexString.toString();
    }
}