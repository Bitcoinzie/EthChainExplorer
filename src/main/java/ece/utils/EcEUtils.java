/*
*
*
*/
package ece.utils;

import org.ethereum.config.SystemProperties;

import static ece.EcEWindow.eth;
import static fetch.ASU.peerCount;
import javafx.embed.swing.JFXPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Insets;

/**
 *
 * @author Bitcoinzie
 * @since 07.01.2015
 * @version 1.0
 */
public class EcEUtils {
    static final String ip = SystemProperties.CONFIG.activePeerIP();
    static final int port = SystemProperties.CONFIG.activePeerPort();
    /**
     *
     * @param panel
     */
    public static void addStyle(JFXPanel panel){
        Border raisedetched;
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        panel.setBorder(raisedetched);
        panel.setBackground(Color.darkGray);
        
    }
    
    /**
     *
     * @param panel
     */
    public static void addStyle(JPanel panel){
        Border empty;
        empty = BorderFactory.createEmptyBorder(3, 3, 3, 3);
        panel.setBorder(empty);
    
    }
    
    public static void setEtchP(JPanel panel, String name){
        Border raisedetched;
        raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        panel.setBorder(raisedetched);
    }
    
    /**
     *
     * @param panel
     * @param panel1
     */
    public static void addStyle(JPanel panel, JPanel panel1){
        Color dg;
        dg = Color.darkGray;
        panel.setBackground(dg);
        panel1.setBackground(dg);
    }
    
    /**
     *
     * @param btn
     * @param name
     */
    public static void addStyle(JToggleButton btn, String name){
        if(name.toLowerCase().contains("fav")){
            //btn.setContentAreaFilled(false);
            //btn.setToolTipText("Add to MyÐApps");
            //btn.setBorderPainted(false);
            //btn.setFocusPainted(true);
            //btn.setBorder(BorderFactory.createEmptyBorder(0, 1, 1, 1));
        }else if(name.toLowerCase().contains("editor")){
            btn.setText("< />");
            btn.setToolTipText("Contract Editor");
            //btn.setBorderPainted(true);
            btn.setMargin(new Insets(4,3,5,3));
        }else if(name.toLowerCase().contains("dapps")){
            btn.setText("ÐApps");
            btn.setToolTipText("MyÐApps");
            //btn.setBorderPainted(true);
            btn.setMargin(new Insets(4,3,5,3));
        }else if(name.toLowerCase().contains("sites")){
            btn.setText("≡");
            btn.setToolTipText("MySites");
            //btn.setBorderPainted(true);
            btn.setMargin(new Insets(4,8,5,8));
        }else if(name.toLowerCase().contains("acnts")){
            btn.setText("Acnts");
            btn.setToolTipText("Global Accounts");
            //btn.setBorderPainted(true);
            btn.setMargin(new Insets(4,3,5,3));
        }else if(name.toLowerCase().contains("tools")){
            btn.setToolTipText("MyToolset");
            java.net.URL imageURL_tool = ClassLoader.getSystemResource("buttons/tools.png");
            ImageIcon tools = new ImageIcon(imageURL_tool);
            btn.setIcon(tools);
            btn.setMargin(new Insets(3,3,3,3));
        }else if(name.toLowerCase().contains("settings")){
            btn.setToolTipText("MySettings");
            java.net.URL imageURL_set = ClassLoader.getSystemResource("buttons/settings.png");
            ImageIcon settings = new ImageIcon(imageURL_set);
            btn.setIcon(settings);
            btn.setMargin(new Insets(3,3,3,3));
        }
    }
    
    public static void addStyle(JButton btn, String name){
        if(name.toLowerCase().contains("fav")){
            btn.setContentAreaFilled(false);
            btn.setToolTipText("Add to MyÐApps");
            btn.setBorderPainted(false);
            btn.setFocusPainted(true);
            btn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        }else if(name.toLowerCase().contains("fwd")){
            btn.setText(">");
            btn.setToolTipText("Next Block");
            btn.setBorderPainted(true);
            btn.setMargin(new Insets(4,8,5,8));
        }else if(name.toLowerCase().contains("back")){
            btn.setText("<");
            btn.setToolTipText("Prev. Block");
            btn.setBorderPainted(true);
            btn.setMargin(new Insets(4,4,4,4));
        }
    }
    
    
    public static void addStyle(JToolBar t){
        t.setFloatable(false);
        t.setRollover(true);
    }
    
    public static void addStyle(JSplitPane sp){
        sp.setOrientation(VERTICAL_SPLIT);
         
    }
    
    public static void addStyle(JScrollPane sc, String name){
        if(name.equalsIgnoreCase("spDap")){
            sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            sc.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            sc.setPreferredSize(new Dimension(230, 450));
            sc.setMinimumSize(new Dimension(230, 450));
        }else if(name.equalsIgnoreCase("spWal")){
            sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            sc.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            sc.setPreferredSize(new Dimension(230, 450));
            sc.setMinimumSize(new Dimension(230, 450));
        }
        
    }
    
    public static void connect2Net(){ 
        Logger c2n = LoggerFactory.getLogger("Connect 2 Net");
        Thread t = new Thread() {    
            @Override
            public void run() {
                eth.connect(ip, port);
                    //Testing for peers
                    boolean prin = true;
                    c2n.info("Connected to: " + peerCount() + " Peers");
                    int pri = 0;
                    while(peerCount() == 0 && prin) {
                        if(pri != 1){
                            c2n.info("No Peers... \nAttempting to connect to peers at" + " " + ip + " " + port);
                            pri++;
                        }
                        if(peerCount() > 0)
                            prin = false;
                }
                c2n.info("Connected to: " + peerCount() + " Peers");
            }
        };
        t.start();
    }
}
     
     
    

   