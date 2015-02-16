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
package fetch;

import org.ethereum.core.Block;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongycastle.util.encoders.Hex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author Bitcoinzie
 */
public class BlockFetch {
    private static final Logger blc = LoggerFactory.getLogger("BlockFetch");
    static Writer w = null;
    public  String dir;
    static Block block;
    private File fr;
    public Long blockindex;
    
    public BlockFetch(){
        this.dir = System.getProperty("user.dir") + "/html/block";
    }
    
    public BlockFetch(Long blockindex){
        this.blockindex = blockindex;
        this.dir = System.getProperty("user.dir") + "/html/block";
    }
    
    public File blockFetch(long blockindex) throws IOException, InterruptedException {
        blc.info("BlockFetch is Fetching Block: " + blockindex);
        block = BlcU.block(blockindex);
        File f = new File("html");
        if(!f.exists()){
            f.mkdir();
        }
        fr = new File(dir + blockindex + ".html");
        if(fr.exists()){
            blc.info("File Already Exists in File System\n ");
            blc.info("Returning block" + blockindex + ".html");
            return fr = new File(dir + blockindex + ".html");
        }else{
            try {
                w = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fr, true), "utf-8"));
                w.write("<!doctype html>\n" +
                        "\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "  <meta charset=\"utf-8\">\n" +
                        "\n" +
                        "  <title>Exploring Block: " + block.getNumber() + "</title>\n" +
                        "  <meta name=\"description\" content=\"EthChain Explorer\">\n" +
                        "  <meta name=\"author\" content=\"EthChain Explorer\">\n" +
                        "\n" +
                        "  <link rel=\"stylesheet\" href=\"styles.css\">\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
                        "<header>\n" +
                        "   <div id=\"logo\"></div>\n" +
                        "</header>\n");
                w.write("  <div id=\"blocks\">\n");
                w.write( "    <p><strong>Block Number:</strong>&nbsp;" + block.getNumber() + "</p>\n");
                w.write( "    <p><strong>Block Hash:</strong>&nbsp;"+ Hex.toHexString(block.getHash()) + "</p>\n");
                w.write( "    <p><strong>Uncle Hash:</strong>&nbsp;" + Hex.toHexString(block.getUnclesHash()) + "</p>\n");
                w.write( "    <p><strong>Nonce:</strong>&nbsp;" + Hex.toHexString(block.getNonce()) + "</p>\n");
                w.write( "    <p><strong>State Root:</strong>&nbsp;" + Hex.toHexString(block.getStateRoot()) + "</p>\n");
                w.write( "    <p><strong>Tx Trie Root:</strong>&nbsp;" + Hex.toHexString(block.getTxTrieRoot()) + "</p>\n");
                w.write( "    <p><strong>Time Stamp:</strong>&nbsp;" + block.getTimestamp() + "</p>\n");
                w.write( "    <p><strong>Gas Limit:</strong>&nbsp;" + block.getGasLimit() + "</p>\n");
                w.write( "    <p><strong>Gas Used:</strong>&nbsp;" + block.getGasUsed() + "</p>\n");
                w.write( "    <p><strong>Miner Coinbase:</strong>&nbsp;" + Hex.toHexString(block.getCoinbase()) + "</p>\n");
                w.write( "    <p><strong>Block Difficulty:</strong>&nbsp;" + ASU.toDecimal("0x" + Hex.toHexString(block.getDifficulty())) + "</p>\n");
                if(block.getUncleList().isEmpty())
                    w.write( "    <div id=\"uncles\"><span><strong>Uncles:</strong>&nbsp;</span><p>No Uncles</p></div>\n");
                else
                    w.write( "    <div id=\"uncles\"><span><strong>Uncles:</strong>&nbsp;</span><p>" + block.getUncleList().toString() + "</p></div>\n");
                //if(Arrays.toString(block.getExtraData())==null)
                w.write( "    <div id=\"extra\"><span><strong>Extra Data:</strong>&nbsp;</span><p>No Extra Data</p></div>\n");
                //else
                //    w.write( "    <div id=\"extra\"><span><strong>Extra Data:</strong>&nbsp;</span><p>" + Arrays.toString(block.getExtraData()) + "</p></div>\n");
                if(block.getTransactionsList().isEmpty())
                    w.write( "    <div id=\"trans\"><span><strong>Transactions:</strong>&nbsp;</span><p>No Transactions</p></div>\n");
                else
                    w.write( "    <div id=\"trans\"><span><strong>Transactions:</strong>&nbsp;</span><p>" + block.getTransactionsList() + "</p></div>\n");
                w.write( "    <p><strong>Bloom Filter:</strong>&nbsp;<br />" + Hex.toHexString(block.getLogBloom()) + "</p>\n");
                w.write( "    <div id=\"rlp\"><span><strong>RLP Encoded:</strong>&nbsp;</span><p>" + Hex.toHexString(block.getEncoded()) + "</p></div>\n");
                w.write("  </div>\n");
                w.write("</body>\n" + "</html>");
            } catch (IOException ex) {
                // report
            } finally {
                try {
                    w.close();
                }catch (Exception ex) {}
            }return fr = new File(dir + blockindex + ".html");
        }
    }
}
