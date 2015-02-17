/*
The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package org.ethchainexplorer.fetch;

import org.ethereum.core.Block;
import org.ethereum.core.Transaction;
import org.ethereum.util.ByteUtil;

import java.util.ArrayList;

/**
 *
 * @author Bitcoinzie
 */
public class TransUtils {
    //TODO: include get block info by hash
    private TransUtils(){
    }
    
    //static ArrayList<Transaction> t;
    public static ArrayList<Transaction> blockTransactions(Long blockindex, Block block) throws InterruptedException {
        ArrayList t = new ArrayList<>();
        for (Transaction transaction : block.getTransactionsList()) {
           Transaction trans = transaction;
           t.add(trans);
        }return t;
    }
    
    public static String transSender(Transaction t) {
        return ByteUtil.toHexString(t.getSender());
    }
    
    public static String transGasPrice(Transaction t) {
        return ByteUtil.toHexString(t.getGasPrice());
    }
    
    public static String transMaxGas(Transaction t) {
        return ByteUtil.toHexString(t.getGasLimit());
    }
    
    public static String transReceiver(Transaction t) {
        return ByteUtil.toHexString(t.getReceiveAddress());
    }
    
    public static String transHash(Transaction t) {
        return ByteUtil.toHexString(t.getHash());
    }
    
    public static String transValue(Transaction t) {
        return t.getValue() != null ? ByteUtil.toHexString(t.getValue
                ()) : "No Transactions";
    }
    
    public static String transNonce(Transaction t) {
        return ByteUtil.toHexString(t.getNonce());
    }
    
    public static String transData(Transaction t) {
        return ByteUtil.toHexString(t.getData());
    }
    
}
