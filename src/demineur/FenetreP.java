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
    protected JButton bPlayPause;
    protected JComboBox ccharge = new JComboBox();
    protected JTextField textVit = new JTextField();
    protected JLabel lbTimer = new JLabel();
    public FenetreP (Environnement _env)
    {
        super();
        env= _env;
        env.addObserver(this);
        longueur = env.tabCases.length;
        largeur = env.tabCases[0].length;
        tabCasesVue = new CaseVue[longueur*largeur];
        build();
        
        addWindowListener(new WindowAdapter(){
        
        @Override
        public void windowClosing(WindowEvent arg0) {
            super.windowClosing(arg0);
            System.exit(0);
        }
    });   
    }
    
    public void miseEnPause()
    {
        System.out.println("miseEnPause()");
        env.setPause(true);
    }
    
    public void sauvegarde()
    {
        //env.creerXMLenv();
    }
    
    public void charge()
    {
        //env.chargerXML("quicksave.xml");
    }
    
    public void charge(Object c)
    {
        String s = (String) c;
        //env.chargerXML("motif/"+s+".xml");
    }
    
    public void mettreEnActivite()
    {
        System.out.println("mettreEnActivite()");
        env.setActif();
    
    }
    
    public int getLongueur()
    {
        return longueur;
    }
    
    public void gestionText(KeyEvent e)
    {
        if (e.getKeyChar() == KeyEvent.VK_ENTER)
                    {
                        env.setVitesse(Integer.parseInt(textVit.getText()));
                        
                    }
                    else
                    {
                        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9' )
                        {
                            textVit.setEditable(true);
                        }
                        else
                        {
                            if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
                            {
                                textVit.setEditable(false);
                            }
                            
                            
                        }
                    }
    }
    public void mettreEnActivitePause() 
    {

        if(!"Pause".equals(bPlayPause.getText()))
        {
            bPlayPause.setText("Pause");
            mettreEnActivite();
        }else 
        {
            bPlayPause.setText("Play");
            miseEnPause();    
        }
    
    }
    
    
    public void lancerLaPartie()
    {
        env.demarrer();
    }
    
    public void effacer()
    {
        env.effacer();
    }
    
    public void lancerLaPartieAlea()
    {
        env.demarrerAlea();
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
        
        JMenuItem mPause = new JMenuItem("Mettre en pause");
        
        mPause.addMouseListener(new MouseAdapter () {
            @Override
            public void mousePressed(MouseEvent arg0) {
                System.out.println("test");
                super.mouseClicked(arg0);
                miseEnPause();
            }
        });
        
        JMenuItem mPlay = new JMenuItem("Mettre en activité");
        mPlay.addMouseListener(new MouseAdapter () {
            @Override
            public void mousePressed(MouseEvent arg0) {
                super.mouseClicked(arg0);
                mettreEnActivite();
            }
        });
        
        
        
        m.add(mPause);
        m.add(mPlay);
        m.add(mi);
        jm.add(m);
        
        setJMenuBar(jm);
        setTitle("Démineur Roger Thomas");
        setSize(1080, 1080);
        
        
        JPanel ensemble = new JPanel(new BorderLayout());
        
        /* CREATION DES BOUTONS */
        JPanel Option = new JPanel(new GridLayout(1,1));
        
        JButton blance = new JButton("Lancer la partie");
        blance.addMouseListener(new MouseAdapter () {
            @Override
            public void mousePressed(MouseEvent arg0) {
                super.mouseClicked(arg0);
                lancerLaPartie();
            }
        });
        Option.add(blance); 
//        
//        JButton blancealea = new JButton("grille aleatoire");
//        blancealea.addMouseListener(new MouseAdapter () {
//            @Override
//            public void mousePressed(MouseEvent arg0) {
//                super.mouseClicked(arg0);
//                lancerLaPartieAlea();
//            }
//        });
//        Option.add(blancealea); 
        
        bPlayPause = new JButton("Pause");

        bPlayPause.addMouseListener(new MouseAdapter () {
            @Override
            public void mousePressed(MouseEvent arg0) {
                super.mouseClicked(arg0);
                mettreEnActivitePause();
            }
        }); 
        
        Option.add(bPlayPause);
        
        lbTimer.setText("000");
        lbTimer.setHorizontalAlignment(SwingConstants.CENTER);
        lbTimer.setFont(new  Font("Consolas", Font.PLAIN, 36));
        
        Option.add(lbTimer);
        
//        JButton bcharger = new JButton("Charger");
//        
//        bcharger.addMouseListener(new MouseAdapter () {
//            @Override
//            public void mousePressed(MouseEvent arg0) {
//                super.mouseClicked(arg0);
//                charge();
//            }
//        });
//        
//        Option.add(bcharger);
//        
//        JButton bsauve = new JButton("Sauvegarde");
//        
//        bsauve.addMouseListener(new MouseAdapter () {
//            @Override
//            public void mousePressed(MouseEvent arg0) {
//                super.mouseClicked(arg0);
//                sauvegarde();
//            }
//        });
//        Option.add(bsauve);
//        
//        JButton brm = new JButton("Effacer");
//        
//        brm.addMouseListener(new MouseAdapter () {
//            @Override
//            public void mousePressed(MouseEvent arg0) {
//                super.mouseClicked(arg0);
//                effacer();
//            }
//        });
        
//        Option.add(brm);
        /* CREATION DES MENUS DEROULANTS */
        
//        ccharge.addItem("canon");
//        ccharge.addItem("etoile");
//        ccharge.addItem("pulsar");
//        ccharge.addItem("prixnobel");
//        ccharge.addActionListener(new ItemAction2());
//        Option.add(ccharge);
        
        /* TextField pour changer la vitesse */
//        
//        textVit = new JTextField(Integer.toString(env.getVitesse()));
//        
//        
//        textVit.addKeyListener( new KeyListener(){
//
//                @Override
//                public void keyPressed(KeyEvent e){
//
//                    gestionText(e);
//                }
//                @Override
//                public void keyReleased(KeyEvent e)
//                {
//                    
//                }
//                
//                @Override
//                public void keyTyped(KeyEvent e)
//                {
//                    
//                }
//        });
        
//        Option.add(textVit);
        
        JComponent pan = new JPanel (new GridLayout(longueur,largeur));
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
        
        ensemble.add(pan,BorderLayout.NORTH);
        ensemble.add(Option,BorderLayout.CENTER);
        
        add(ensemble);
        
        pack();

    }

    
    class ItemAction2 implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                charge(ccharge.getSelectedItem());
            }
        }
    
    @Override
    public void update(Observable o, Object arg) {
        for (int i= 0; i<longueur; i++)
        {
            for (int j = 0; j<largeur; j++)
            {
                /*if (env.tabCases[i][j].getEtatCourant())
                {
                    
                    tabCasesVue[i*largeur+j].setBackground(Color.YELLOW);
                    int n = i*largeur+j;                   
                }
                else
                {
                    tabCasesVue[i*largeur+j].setBackground(Color.GRAY);
                    
                }*/
                Case temp = env.tabCases[i][j];
                Color iconColor = new Color(0xEE1700);

                if (temp.isOpen()) {
                    tabCasesVue[i*largeur+j].openedView(temp);
                } else if (temp.isFlagged()) {
                    tabCasesVue[i*largeur+j].flaggedView();
                } else {
                    tabCasesVue[i*largeur+j].defaultView();
                }
                
            }
        }
        
        if (env.getPause())
        {
            bPlayPause.setText("Play");
        }
        else
        {
            bPlayPause.setText("Pause");
            lbTimer.setText(Integer.toString(env.getTimer()));
        }
       
    }
    
    
}
