/*
 * The GUI
 *
 * This class is based on some really old things I did while I was learning Java
 * It's not good. I know - RootCellar
 *
 */

package VirtualComputer.GUI;

import VirtualComputer.Util.OutputUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainGUI implements ActionListener, OutputUser {

    public JLabel label = new JLabel("Loading...");
    public JLabel label2 = new JLabel("Loading...");
    public JLabel label3 = new JLabel("Loading...");
    public JLabel label4 = new JLabel("Loading...");
    public JLabel label5 = new JLabel("Loading...");

    //The stuff that's useful
    private final JFrame frame = new JFrame();
    private GUIUser user;

    private final TerminalPanel term = new TerminalPanel();
    private final TerminalPanel term2 = new TerminalPanel();

    public MainGUI() {

        frame.setLocationRelativeTo(null);

        frame.setMinimumSize(new Dimension(800, 800));

        frame.setLayout(new GridLayout(1, 2));

        frame.setBackground(Color.BLUE);

        JTabbedPane tabbedPane = new JTabbedPane();
        JTabbedPane tabbedPane2 = new JTabbedPane();

        //Create Main tab

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(4, 2));

        panel.add(getNewButton("BLANK", "BLANK"));
        panel.add(getNewButton("BLANK", "BLANK"));
        panel.add(getNewButton("BLANK", "BLANK"));
        panel.add(getNewButton("BLANK", "BLANK"));
        panel.add(getNewButton("BLANK", "BLANK"));
        panel.add(getNewButton("BLANK", "BLANK"));
        panel.add(getNewButton("BLANK", "BLANK"));
        panel.add(getNewButton("BLANK", "BLANK"));

        //Create Info tab

        JPanel panel2 = new JPanel();
        //panel2.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel2.setLayout(new GridLayout(5, 1));

        panel2.add(label);
        panel2.add(label2);
        panel2.add(label3);
        panel2.add(label4);
        panel2.add(label5);

        //Set up tabs
        tabbedPane.add("Main Tab", panel);
        //tabbedPane.add("Tab 2", panel3);
        tabbedPane.add("Info Tab", panel2);
        tabbedPane.add("Debug Tab", term2);

        /*

        //Set up panels and buttons for Tabcursion

        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(4, 2));

        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );
        panel3.add( getNewButton("BLANK", "BLANK") );

        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(4, 2));

        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );
        panel4.add( getNewButton("BLANK", "BLANK") );

        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(4, 2));

        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );
        panel5.add( getNewButton("BLANK", "BLANK") );

        //Set up the Tabcursion
        tabbedPane2.add("Buttons", panel3);
        tabbedPane2.add("More Buttons", panel4);
        tabbedPane2.add("Even More Buttons",panel5);

        //Add the last tab
        //tabbedPane.add("Tabcursion",tabbedPane2);

        */

        //frame.add(panel, BorderLayout.CENTER);

        frame.add(tabbedPane);

        frame.add(term);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);
    }

    public void setUser(GUIUser u) {
        user = u;

        term.setUser(u);
        term2.setUser(u);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if( user != null ) user.inputString(command);
    }

    public void update() {

    }

    public void out(String s) {
        term.write(s);
    }

    public void debug(String s) {
        term2.write(s);
    }

    public void inputString(String s) {
        out(s);
    }

    public void inputDebug(String s) {
        debug(s);
    }

    //Helpful little method
    //Makes setting up buttons MUCH easier
    public JButton getNewButton(String name, String command) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(command);

        button.setBackground(new Color((float) 0.8, (float) 0.6, (float) 0.6));

        //button.setBackground( new Color( (float)Math.random(), (float)Math.random(), (float)Math.random() ) ); //Caution: Random

        //button.setForeground( new Color( (float)1, (float)1, (float)1 ) );
        button.setForeground(new Color((float) 0, (float) 0, (float) 0));

        return button;
    }

}
