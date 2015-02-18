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

import org.ethereum.core.Account;
import static org.ethereum.core.Denomination.toFriendlyString;
import org.ethereum.facade.Ethereum;
import org.ethereum.util.Utils;
import static org.ethereum.util.Utils.hexStringToDecimalString;
import org.ethereum.vm.DataWord;

import org.ethchainexplorer.ece.EcEWindow;

import java.math.BigInteger;
import java.util.ArrayList;


/**
 *
 * @author Bitcoinzie
 */
public class ASU {
    private static final Ethereum eth = EcEWindow.eth;
//    private static final Wallet wallet = eth.getWallet();
//    private static final Repository repository = eth.getRepository();
    private static final ArrayList acntList = new ArrayList<>();
    private static Account account;
    public ASU(){}
    
    /**
     * loads the users wallet accounts.
     * @return ArrayList<byte[]> returns ArrayList of owned Accounts
     */
    public static ArrayList<Account> wallet(){
        //for(Iterator<Account> it = wallet.getAccountCollection().iterator(); it.hasNext();) {
          //  account = it.next();
            System.out.println(account);
             if(!acntList.contains(account))
                 acntList.add(account);
        return null;
        //}
        //return acntList;
    }    
    /**
     *
     * Adds new account to the users wallet
     */
    public static void addNew() {
        //wallet.addNewAccount();   
    }
    public static void importAc(Account acnt){
        //wallet.importKey(acnt.getEcKey().getPrivKeyBytes());
    }    
    public static void saveAcnt(byte[] addr){
        //repository.addBalance(addr, BigInteger.ZERO);
//        repository.createAccount(addr);
    }    
    /**
     *
     * @param str hex string representing a value in the format 0x21 to be converted to decimal in the format 15
     * @return  string representation of a decimal number
     */
    public static String toDecimal(String str) {
        return hexStringToDecimalString(str);
    }    
    /**
     *Checks the balance at a give address
     * @param address a 20 character Hex String
     * @return returns BigInt value at the given account
     */
    public static BigInteger balanceAt(String address) {
	byte[] addr = Utils.addressStringToBytes(address);
//            return repository.getBalance(addr);
        return null;
    }    
    /**
     *
     * @param addr byte [] Address of the Account to check storage
     * @param key DataWord key to the storage location
     * @return the values stored or null if empty
     */
    public static DataWord storageAt(byte[] addr, DataWord key) {
        return null;
//         return repository.getStorageValue(addr, key);
    }    
    /**
     *
     * @param address
     * @return byte array containing the code 
     */
    public static byte[] codeAt(String address) {
	//Any Valid 20 char hex address make sure to validate your address
	//String address = "cd2a3d9f938e13cd947ec05abc7fe734df8dd826"
	//Use the utils class to make it a byte[] array
	byte[] addr = Utils.addressStringToBytes(address);
	
	//Get the repository and return the contract details of the address provided
//	ContractDetails contractDetails = repository.getContractDetails(addr);
	
	//Get the code from inside the contract details and assign it to a byte array
//	final byte[] programCode = contractDetails.getCode();
//	return programCode;
        return null;
    }    
    /**
     *
     * @param addr byte[] array representing the address of the account to get the transactions nonce from
     * @return nonce BigInteger transactions nonce
     */
    public static BigInteger countAt(byte[] addr) {
        return null;
//        return repository.getNonce(addr); 
    }
    public static BigInteger countAt(Account ac) {
        return ac.getNonce(); 
    }  
    /**
     *
     * @return int value representing the number of peers this client is connected to
     */
    public static int peerCount() {
        return 0;
        //Set<PeerInfo> peers = eth.getPeers();
        //return peers.size();
    }
    /**
     *
     * @param BgIn Balance of the Account
     * @return Word representation of the balance.. "Infinity ETHER"
     */
    public static String toEth(BigInteger BgIn) {
        return toFriendlyString(BgIn);
    }
}