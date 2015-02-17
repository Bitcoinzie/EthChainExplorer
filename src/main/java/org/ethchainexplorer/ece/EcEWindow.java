/*
*
*
*/
package org.ethchainexplorer.ece;


import static org.ethchainexplorer.ece.utils.EcEUtils.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;

import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import static org.ethereum.config.SystemProperties.CONFIG;
import org.ethereum.facade.Ethereum;
import org.ethereum.facade.EthereumFactory;
import org.ethereum.util.Utils;

import static org.ethchainexplorer.ece.EcEWindow.eth;
import org.ethchainexplorer.ece.utils.NumericTextField;
import org.ethchainexplorer.fetch.BlockFetch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import static java.lang.Thread.sleep;
import java.text.ParseException;


/**
 *
 * @author Bitcoinzie
 * @since 01.10.2014
 * @version 1.0
 */

public class EcEWindow extends JFrame {
    private final Logger introLogger = LoggerFactory.getLogger("Intro");
    public static Ethereum eth;
    private final static Logger user = LoggerFactory.getLogger("User Out");
    EcEWindow browserWindow;
    private final JPanel topBar;
    private final JFXPanel jfxPanel;
    private WebEngine engine;
    private final JPanel eastPanel;
    private final JPanel panel;
    private final JPanel bPanel;
    private final JLabel txtLable;
    private final NumericTextField blcTxt;
    private final JButton blcFwd;
    private final JButton blcBack;
    private static final String dir = System.getProperty("user.dir") + "/html/";
    private final BlockFetch bf;
    
    String north;
    String south;
    String east;
    String west;
    String center;
    String old;
    
    public EcEWindow() throws InterruptedException, IOException {
        super();
        String version = CONFIG.projectVersion();

        introLogger.info("");
        introLogger.info("|Ξ|  EthereumJ [v" + version + "]");
        introLogger.info("|Ξ|  Code by Roman Mandeleil, (c) 2014.");
        introLogger.info("|Ξ|  Contribution: Nick Savers ");
        introLogger.info("|Ξ|  Based on a design by Vitalik Buterin.");
        introLogger.info("|Ξ|  EthChain Explorer v0.01 (c) 2015.");
        introLogger.info("|Ξ|  Code by Bitcoinzie");
        introLogger.info("");
        introLogger.info("java.version: " + System.getProperty("java.version"));
        introLogger.info("java.home:    " + System.getProperty("java.home"));
        introLogger.info("java.vendor:  " + System.getProperty("java.vendor"));
        introLogger.info("");

        if (Utils.JAVA_VERSION < 1.8 && Utils.JAVA_VERSION != 0) {
            introLogger.info("EthChain Explorer supports version 1.8 and higher Java Runtime Versions please update to the latest update");
            System.exit(0);
        }
        java.net.URL url = ClassLoader.getSystemResource("EcEico.png");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.createImage(url);
        this.setIconImage(img);
        this.center = BorderLayout.CENTER;
        this.west = BorderLayout.WEST;
        this.east = BorderLayout.EAST;
        this.south = BorderLayout.SOUTH;
        this.north = BorderLayout.NORTH;
        this.topBar = new JPanel(new BorderLayout(5, 0));
        this.jfxPanel = new JFXPanel();
        this.eastPanel = new JPanel(new GridLayout(0,1,0,2));
        this.panel = new JPanel(new BorderLayout());
        this.bPanel = new JPanel(new BorderLayout());
        this.txtLable = new JLabel("Enter Block #");
        this.blcTxt = new NumericTextField("", 20);
        this.blcFwd = new JButton(">");
        this.blcBack = new JButton("<");
        this.bf = new BlockFetch();

        initComponents();
    }
    
    private void initComponents() throws IOException {
        createScene();
        eth = EthereumFactory.createEthereum();
        String ip = "5.1.83.141";
        int port = 30303;
        connect2Net();
        blcTxt.addActionListener(e ->{
            try {
                blcTxt.setValue(blcTxt.getLongValue());
                while(eth.getBlockchain().getSize()<=blcTxt.getLongValue()){
                    sleep(10L);
                }
                loadURL("file:///" + bf.blockFetch(blcTxt.getLongValue()).toString());    
            } catch (InterruptedException | ParseException | IOException ex) {
            }
        });
        blcFwd.addActionListener(e ->{
            try {
                loadURL("file:///" + bf.blockFetch(blcTxt.getLongValue()+1).toString());
                blcTxt.setValue((blcTxt.getLongValue()+1));
            } catch (IOException | InterruptedException | ParseException ex) {
            }
        });
        blcBack.addActionListener(e ->{
            try {
                loadURL("file:///" + bf.blockFetch(blcTxt.getLongValue()-1).toString());
                blcTxt.setValue((blcTxt.getLongValue()-1));
            } catch (IOException | InterruptedException | ParseException ex) {
            }
        });
        this.jfxPanel.setLayout(new BorderLayout());
        
        addStyle(topBar);
        addStyle(this.blcBack, "back");
        addStyle(this.blcFwd, "fwd");
        
        JToolBar tool = new JToolBar();
        tool.setLayout(new FlowLayout());
        tool.add(this.txtLable);
        tool.add(Box.createHorizontalGlue());
        tool.add(this.blcTxt);
        tool.add(this.blcBack);
        tool.add(this.blcFwd);
        addStyle(tool);
        this.topBar.add(tool, north);
        
        this.eastPanel.setPreferredSize(new Dimension(200, 500));
        this.eastPanel.setVisible(false);
        this.bPanel.add(this.eastPanel, east);
        
        
        this.bPanel.add(this.jfxPanel, center);
        
        setEtchP(panel, "panel");
        
        this.panel.add(this.topBar, north);
        this.panel.add(this.bPanel, center);
        getContentPane().add(this.panel);
        
        this.setResizable(true);
        this.setPreferredSize(new Dimension(900, 900));//sets the default window size
        addCloseAction();
        
        this.pack();
    }

    private void createScene() {
        Platform.runLater(() -> {
            WebView view = new WebView();
            engine = view.getEngine();
            loadURL("file:///" + dir + "blockNull.html");
            engine.titleProperty().addListener((ObservableValue<? extends String> observable, String oldValue, final String newValue) -> {
                SwingUtilities.invokeLater(() -> {
                    this.setTitle(newValue);
                });
            });
            jfxPanel.setScene(new Scene(view));
        });
    }
    
    /**
     *
     * @param url Loads the URL to the browser
     */
    public void loadURL(final String url){
        Platform.runLater(() -> {
            String tmp = url;
            if(toURL(url) == null)
                tmp = toURL("http://" + url);
            engine.load(tmp);
        });
    }

    private String toURL(String str) {
        try{
            return new URL(str).toExternalForm();
        }catch (MalformedURLException exception) {
            return null;
        }
    }
    public void addCloseAction() {
        this.addWindowListener(new WindowAdapter() {
        });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        SwingUtilities.invokeLater(() -> {
            try {
                new EcEWindow().setVisible(true);
            } catch (InterruptedException | IOException ex) {
            }
        });
        
    }
        
}


