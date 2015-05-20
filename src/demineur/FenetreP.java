/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.*;


/**
 *
 * @author gauvain
 */
public class FenetreP extends JFrame implements Observer {

    protected Environnement env;
    protected int longueur;
    protected int largeur;
    protected CaseVue tabCasesVue[];
    protected JComboBox ccharge = new JComboBox();
    protected JTextField textVit = new JTextField();
    protected JLabel lbTimer = new JLabel();
    protected JLabel lbNbFlagged = new JLabel();
    protected JPanel ensemble;
    protected JComponent pan;
    public FenetreP (Environnement _env)
    {
        super();
        env= _env;
        env.addObserver(this);
        build();
        
        addWindowListener(new WindowAdapter(){
        
        @Override
        public void windowClosing(WindowEvent arg0) {
            super.windowClosing(arg0);
            System.exit(0);
        }
    });   
    }
    
    public int getLongueur()
    {
        return longueur;
    }
    
    public void lancerLaPartie()
    {
        env.initialisation(env.getDifficulte());
    }

    public void effacer()
    {
        env.effacer();
    }

    public void build()
    {
        JMenuBar jm = new JMenuBar();
        
        JMenu m = new JMenu("Jeu");
        
        JMenuItem mi = new JMenuItem("Lancer la partie");

        mi.addMouseListener(new MouseAdapter () {
            @Override
            public void mousePressed(MouseEvent arg0) {
                System.out.println("test");
                super.mouseClicked(arg0);
                lancerLaPartie();
            }
        });
        
        JMenuItem miQuitter = new JMenuItem("Quitter la partie");
        
        miQuitter.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });

        m.add(mi);
        m.add(miQuitter);
        
        JMenu mDifficulte = new JMenu("Difficulté");
        
        JMenuItem miEasy = new JMenuItem("Facile");
        miEasy.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                super.mouseClicked(e);
                env.initialisation(Environnement.EASY);
            }
        });
        
        JMenuItem miMedium = new JMenuItem("Moyen");
        miMedium.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                super.mouseClicked(e);
                env.initialisation(Environnement.MEDIUM);
            }
        });
        
        JMenuItem miHard = new JMenuItem("Difficile");
        miHard.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                super.mouseClicked(e);
                env.initialisation(Environnement.HARD);
            }
        });
        
        mDifficulte.add(miEasy);
        mDifficulte.add(miMedium);
        mDifficulte.add(miHard);
        jm.add(m);
        jm.add(mDifficulte);
        
        setJMenuBar(jm);
        setTitle("Démineur Roger Thomas");
        setSize(1080, 1080);
        
        
        ensemble = new JPanel(new BorderLayout());
        
        /* CREATION DES BOUTONS */
        JPanel Option = new JPanel(new GridLayout(1,3));
        
        JButton blance = new JButton("Lancer la partie");
        blance.addMouseListener(new MouseAdapter () {
            @Override
            public void mousePressed(MouseEvent arg0) {
                super.mouseClicked(arg0);
                lancerLaPartie();
            }
        });
        
        lbTimer.setText("000");
        lbTimer.setHorizontalAlignment(SwingConstants.CENTER);
        lbTimer.setFont(new  Font("Consolas", Font.PLAIN, 36));
        
        Option.add(lbTimer);
        Option.add(blance); 
        
        lbNbFlagged.setText(env.getNbFlagged()+"/"+env.getTotalMine());
        lbNbFlagged.setHorizontalAlignment(SwingConstants.CENTER);
        lbNbFlagged.setFont(new  Font("Consolas", Font.PLAIN, 36));
        
        Option.add(lbNbFlagged);

        initCaseVue();
        
        ensemble.add(Option,BorderLayout.CENTER);
        
        add(ensemble);
        
        pack();

    }

    
    class ItemAction2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //charge(ccharge.getSelectedItem());
        }
    }
    
    public void initCaseVue()
    {
        longueur = env.getLongueur();
        largeur = env.getLargeur();
        tabCasesVue = new CaseVue[longueur*largeur];
        
        pan = new JPanel (new GridLayout(longueur,largeur));
        Color borderColor = new Color(0x393638);
        Border blackline = BorderFactory.createLineBorder(borderColor, 1);
        
        for(int i = 0; i<(longueur*largeur); i++)
        {
            CaseVue c = new CaseVue(i, largeur, env, this);
            c.setBorder(blackline);
            
            pan.add(c);
            tabCasesVue[i]=c;
        }
        pan.setBorder(blackline);
        
        ensemble.add(pan,BorderLayout.SOUTH);
        
    }
    
    @Override
    public void update(Observable o, Object arg) {
        boolean flag = false;
        if (env.getLargeur() != largeur || env.getLongueur() != longueur) {
            ensemble.remove(pan);
            initCaseVue();
            pack();
        }
        for (int i= 0; i<longueur; i++)
        {
            for (int j = 0; j<largeur; j++)
            {
                Case temp = env.tabCases[i][j];
                if (temp.isOpen()) {
                    tabCasesVue[i*largeur+j].openedView(temp);
                } else if (temp.isFlagged()) {
                    tabCasesVue[i*largeur+j].flaggedView();
                } else {
                    tabCasesVue[i*largeur+j].defaultView();
                }
                
            }
        }

        lbTimer.setText(Integer.toString(env.getTimer()));
        lbNbFlagged.setText(Integer.toString(env.getNbFlagged())+"/"+env.getTotalMine());
       
    }
    
    
}
