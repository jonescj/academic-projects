package com.cscie97.ledger;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/*
The Block aggregates groups of 10 transactions. Transactions should be added to blocks in the order that they
are received. Prior to adding a transaction to a block, the transaction must be validated. Transactions that
are invalid should be discarded. The block also contains an account balance map that reflects the balance of
all accounts after all the transactions within the block have been applied. The account balance map should be
copied from the previous block and updated to reflect the transactions in the current block. The block contains
the hash of the previous block. It also contains the hash of itself.
*/
public class Block{

    // A sequentially incrementing block number assigned to the block. The first block or genesis block is assigned a
    // block number of 1.
    private int blockNumber;

    // The hash of the previous block in the blockchain. For the genesis block, this is empty. Use the Sha-256
    // algorithm and Merkle tree to compute the hash per the requirements.
    private String previousHash;

    // The hash of the current block is computed based on all properties and associations of the current Block except
    // for this attribute. Use the Sha-256 algorithm and Merkle tree to compute the
    private String hash;

    // An ordered list of Transactions that are included in the current block. There should be exactly 10 transactions
    // per block.
    public ArrayList<Transaction> transactionList = new ArrayList<Transaction>();

    // The full set of accounts managed by the Ledger. The account balances should reflect the account state after all
    // transactions of the current block have been applied. Note that each Block has its own immutable copy of the
    // accountBalanceMap.
    public HashMap<String, Account> accountBalanceMap = new HashMap<String, Account>();

    // The previousBlock association references the preceding Block in the blockchain.
    public Block previousBlock;

    // Hash trees can be used to verify any kind of data stored, handled and transferred in and between computers.
    // They can help ensure that data blocks received from other peers in a peer-to-peer network are received
    // undamaged and unaltered, and even to check that the other peers do not lie and send fake blocks.
    // Resource: https://www.youtube.com/watch?v=_hGa1jfx584&ab_channel=GlobalSoftwareSupport
    private MerkleTree merkleTree = new MerkleTree();

    // The Block aggregates groups of 10 transactions. Transactions should be added to blocks in the order that they
    // are received. Prior to adding a transaction to a block, the transaction must be validated. Transactions that
    // are invalid should be discarded. The block also contains an account balance map that reflects the balance of
    // all accounts after all the transactions within the block have been applied. The account balance map should be
    // copied from the previous block and updated to reflect the transactions in the current block. The block contains
    // the hash of the previous block. It also contains the hash of itself.
    public Block(int blockNumber, String previousHash, String hash) throws LedgerException {
        this.blockNumber = blockNumber;
        this.previousHash = previousHash;
        this.hash = computeMerkleHash(hash);
    }

    // Compute Merkle hash and add to the MerkleTree
    private String computeMerkleHash(String hash) throws LedgerException {
        try {
            merkleTree.add(hash);
            return merkleTree.getRoot().get(0);
        }catch(NoSuchAlgorithmException e){
            throw new LedgerException(e.toString(),e.getMessage());
        }
    }

    // This is a getter for the hash field
    public String getHash(){
        return this.hash;
    }

    // This is a getter for the block number
    public int getBlockNumber() {
        return this.blockNumber;
    }
}