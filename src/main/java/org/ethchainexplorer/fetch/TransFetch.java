/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ethchainexplorer.fetch;

import org.ethereum.core.Block;
import org.ethereum.core.Transaction;
import org.ethereum.util.ByteUtil;

import static org.ethchainexplorer.fetch.TransUtils.blockTransactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.spongycastle.util.encoders.Hex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Bitcoinzie
 */
public class TransFetch {

    private static final Logger trans = LoggerFactory.getLogger("TransFetch");
    private File fr;
    public Long blockindex;
    static Writer w = null;
    public String dir;
    //private Transaction tr;
    private Block block;
    
    public TransFetch(){
    }
    
    public TransFetch(Long blockindex, Block block){
        this.blockindex = blockindex;
        this.dir = System.getProperty("user.dir") + "/html/blockCache/block" + blockindex + "/trans";
        this.block = block;
    }
    
    public void transFetch(Long blockindex) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException{
        trans.info("BlockFetch is Fetching Block: " + blockindex);
        List<Transaction> t;
        t = blockTransactions(blockindex, block);
        File f = new File(System.getProperty("user.dir") + "/html/blockCache/block" + blockindex + "/");
        if(!f.exists())
            f.mkdir();
        for(int i=0; i<t.size(); i++){
            fr = new File(dir + (i+1) + ".html");
            try {
                w = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fr, true), "utf-8"));
                w.write("<!doctype html>\n" +
                        "\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "\n" +
                        "  <title>Exploring Block:&nbsp;" + block.getNumber() + "'s&nbsp;Transactions</title>\n" +
                        "  <meta name=\"description\" content=\"EthChain Explorer\">\n" +
                        "  <meta name=\"author\" content=\"EthChain Explorer\">\n" +
                        "\n" +
                        "  <link rel=\"stylesheet\" href=\"../../styles.css\">\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
                        "<header>\n" +
                        "   <div id=\"logo\"></div>\n" +
                        "</header>\n");
                w.write("  <div id=\"blocks\">\n");
                w.write( "    <p><strong>Block Number:</strong>&nbsp;" + block.getNumber() + "</p>\n");
                w.write( "    <p><strong>Block Hash:</strong>&nbsp;"+ Hex.toHexString(block.getHash()) + "</p>\n");
                if(t.size()==1)
                    w.write( "    <p><strong>Transaction Number:</strong>&nbspThere was&nbsp;" + t.size() + "&nbsp;<strong>Transactions</strong>&nbsp;inside block&nbsp;" + block.getNumber() + "</p>\n");
                else
                    w.write( "    <p><strong>Transaction Number:</strong>&nbspThere were&nbsp;" + t.size() + "&nbsp;<strong>Transactions</strong>&nbsp;inside block&nbsp;" + block.getNumber() + "</p>\n");
                w.write( "    <p><strong>Hash:</strong>&nbsp;" + ByteUtil.toHexString(t.get(i).getHash()) + "</p>\n");
                w.write( "    <p><strong>Nonce:</strong>&nbsp;" + ByteUtil.toHexString(t.get(i).getNonce()) + "</p>\n");
                w.write( "    <p><strong>Sender Address:</strong>&nbsp;" + ByteUtil.toHexString(t.get(i).getSender()) + "</p>\n");
                w.write( "    <p><strong>Recieving Address:</strong>&nbsp;" + ByteUtil.toHexString(t.get(i).getReceiveAddress()) + "</p>\n");
                w.write( "    <p><strong>Key:</strong>&nbsp;" + t.get(i).getKey() + "</p>\n");
                if(t.get(i).isContractCreation())
                    w.write( "    <p><strong>Contract Address:</strong>&nbsp;" + Hex.toHexString(t.get(i).getContractAddress()) + "</p>\n");
                w.write( "    <p><strong>Approximate Time Stamp:</strong>&nbsp;" + block.getTimestamp() + "&nbsp;read from block Timestamp.</p>\n");
                if(t.get(i).isValueTx())
                    w.write( "    <p><strong>Transaction Value Transferred:</strong>&nbsp;" + Hex.toHexString(t.get(i).getValue()) + "</p>\n");
                else
                    w.write( "    <p><strong>Transaction Value Transferred:</strong>&nbsp;No Value Transferred</p>\n");
                BigInteger gl = new BigInteger(ASU.toDecimal("0x" + ByteUtil.toHexString(t.get(i).getGasLimit())));
                BigInteger gp = new BigInteger(ASU.toDecimal("0x" + ByteUtil.toHexString(t.get(i).getGasPrice())));
                w.write( "    <p><strong>Transaction Gas Limit:</strong>&nbsp;" + ASU.toDecimal("0x" + ByteUtil.toHexString(t.get(i).getGasLimit())) + ": " + ASU.toEth(gl) + "</p>\n");
                w.write( "    <p><strong>Transaction Gas Price:</strong>&nbsp;" + ASU.toDecimal("0x" + ByteUtil.toHexString(t.get(i).getGasPrice())) + ": " + ASU.toEth(gp) + "</p>\n");
                if(t.get(i).getData()==null)
                    w.write( "    <div id=\"uncles\"><span><strong>Transaction Data:</strong>&nbsp;</span><p>No Data</p></div>\n");
                else    
                    w.write( "    <div id=\"uncles\"><span><strong>Transaction Data:</strong>&nbsp;</span><p>" + Hex.toHexString(t.get(i).getData()) + "</p></div>\n");
                w.write( "    <div id=\"uncles\"><span><strong>Transaction ECDSA Signature:</strong>&nbsp;</span><p>" + t.get(i).getSignature().toBase64() + "</p></div>\n");
                w.write( "    <div id=\"uncles\"><span><strong>Transaction Raw Hash:</strong>&nbsp;</span><p>" + ByteUtil.toHexString(t.get(i).getRawHash())+ "</p></div>\n");
                if(i!=0){
                    if(t.iterator().hasNext())
                        w.write( "    <div id=\"footer\"><a href\"file:///"+ dir + (i) + "\">Prev</a>&nbsp;&nbsp;&nbsp;<a href\"file:///"+ dir + (i+2) + "\">Next</a></div>");
                    else
                       w.write( "    <div id=\"footer\"><a href\"file:///"+ dir + (i) + "\">Prev</a></div>"); 
                }else{
                    if(i==0 && !t.iterator().hasNext())
                        w.write( "    <div id=\"footer\"></div>");
                    else
                        w.write( "    <div id=\"footer\"><a href\"file:///"+ dir + (i+2) + "\"></a></div>");
                }
                w.write("  </div>\n");
                w.write("</body>\n" + "</html>");
            } catch (IOException ex) {
            // report
            } finally {
            try {
                w.close();
            }catch (Exception ex) {}
            }
        }
    }
}

        
    

    

