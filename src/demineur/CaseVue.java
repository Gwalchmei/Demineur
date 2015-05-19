/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author gauvain
 */
public class CaseVue extends JLabel{
    /* indice correspond à l'indice de la CaseVue dans le tableau de la fenetre, largeur correspond à la largeur du tableau de l'environnement */
    private int indice;
    private int largeur;
    private Environnement env;
    private FenetreP vue;
    
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(20,20);
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(20, 20);
    }
    
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(20, 20);
    }
    
    public CaseVue(int _indice, int _largeur, Environnement _env, FenetreP _vue) {
        super();
        
        defaultView();
        
        indice = _indice;
        largeur = _largeur;
        env = _env;
        vue = _vue;
        addMouseListener(new MouseAdapter () {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                super.mouseClicked(arg0);
                cliqueSouris(arg0);
            }
            
            @Override
            public void mouseEntered(MouseEvent arg0) {
                super.mouseEntered(arg0);
                coloriserMotif();
            }
            
            @Override
            public void mouseExited(MouseEvent arg0) {
                super.mouseExited(arg0);
                effacerMotif();
            }
        });
        setOpaque(true);
        
        
    }
    
    public final void defaultView(){
        Color bgColor = new Color(0x5a5a5a);
        setBackground(bgColor);
        setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        setHorizontalAlignment(SwingConstants.CENTER);
        setText(" ");
    }
    
    public void openedView(Case c) {
        Color bgColor = new Color(0xF3F3F3);
        setBackground(bgColor);
        if (c.getNbMined() != 0) {
            setText(Integer.toString(c.getNbMined()));
            setForeground(Color.BLACK);
        }
        if (c.getMined()){
            Color iconColor = new Color(0xEE1700);
            setText("\uD83D\uDCA3");
            setForeground(iconColor);
        }
    }
    
    public void flaggedView() {
        Color iconColor = new Color(0xEE1700);
        setText("\u2691");
        setForeground(iconColor);
    }
    
    public void cliqueSouris(MouseEvent e){
        //System.out.println("On a clique sur la case !");
        int x;
        int y;
        
        x = indice/largeur;
        y = indice%largeur;
        env.cliqueSouris(x,y,e.getButton() == MouseEvent.BUTTON1 ? true : false);
    }
    
    public void coloriserMotif() {
        int x = indice/largeur;
        int y = indice%largeur;
        vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.red));
        
    }
    
    public void effacerMotif() {
        int x = indice/largeur;
        int y = indice%largeur;
        Color borderColor = new Color(0x393638);
        vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(borderColor));
    }
}
