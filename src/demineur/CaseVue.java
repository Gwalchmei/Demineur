/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

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
    
    
    public CaseVue(int _indice, int _largeur, Environnement _env, FenetreP _vue) {
        super();
        
        setBackground(Color.GRAY);
        indice = _indice;
        largeur = _largeur;
        env = _env;
        vue = _vue;
        addMouseListener(new MouseAdapter () {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                super.mouseClicked(arg0);
                cliqueSouris();
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
        setText(" ");
        setOpaque(true);
        
        
    }
    
    public void cliqueSouris(){
        //System.out.println("On a clique sur la case !");
        int x;
        int y;
        switch(vue.motif)
        {
            case 0 : 
                x = indice/largeur;
                y = indice%largeur;
                env.cliqueSouris(x,y);
                break;
            case 1 :
                x = indice/largeur;
                y = indice%largeur;
                env.cliqueSouris(x,y);
                
                if (((y < largeur-1) && (y>0)) && (x < vue.getLongueur()-2))
                {
                x = (indice+largeur+1)/largeur;
                y = (indice+largeur+1)%largeur;
                env.cliqueSouris(x,y);

                x = (indice+2*largeur+1)/largeur;
                y = (indice+2*largeur+1)%largeur;
                env.cliqueSouris(x,y);

                x = (indice+2*largeur-1)/largeur;
                y = (indice+2*largeur-1)%largeur;
                env.cliqueSouris(x,y);

                x = (indice+2*largeur)/largeur;
                y = (indice+2*largeur)%largeur;
                env.cliqueSouris(x,y);
                }
                break;
            case 2:
                x = indice/largeur;
                y = indice%largeur;
                env.cliqueSouris(x,y);
                
                if (((y>0) && (y<largeur-2)) && (x< vue.getLongueur() - 1))
                {
                x = (indice+1)/largeur;
                y = (indice+1)%largeur;
                env.cliqueSouris(x,y);
                
                x = (indice+2)/largeur;
                y = (indice+2)%largeur;
                env.cliqueSouris(x,y);
                
                x = (indice+largeur+1)/largeur;
                y = (indice+largeur+1)%largeur;
                env.cliqueSouris(x,y);
                
                x = (indice+largeur-1)/largeur;
                y = (indice+largeur-1)%largeur;
                env.cliqueSouris(x,y);
                
                x = (indice+largeur)/largeur;
                y = (indice+largeur)%largeur;
                env.cliqueSouris(x,y);
                }
                break;
                 case 3 :
                x = indice/largeur;
                y = indice%largeur;
                env.cliqueSouris(x,y);
                
                if ((y < largeur-2) && (x < vue.getLongueur()-2))
                {
                x = (indice+1)/largeur;
                y = (indice+1)%largeur;
                env.cliqueSouris(x,y);

                x = (indice+largeur)/largeur;
                y = (indice+largeur)%largeur;
                env.cliqueSouris(x,y);

                x = (indice+largeur+2)/largeur;
                y = (indice+largeur+2)%largeur;
                env.cliqueSouris(x,y);

                x = (indice+2*largeur)/largeur;
                y = (indice+2*largeur)%largeur;
                env.cliqueSouris(x,y);
                }
                break;
            
        }
        
    }
    
    public void coloriserMotif() {
        int x = indice/largeur;
        int y = indice%largeur;
        switch(vue.motif)
        {
            case 0 :
               vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.red));
               break;
            case 1 :
               vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.red));
               if (((y < largeur-1) && (y>0)) && (x < vue.getLongueur()-2))
                {
                   vue.tabCasesVue[indice+largeur+1].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+2*largeur].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+2*largeur-1].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+2*largeur+1].setBorder(BorderFactory.createLineBorder(Color.red)); 
                }
               break;
            case 2 :
               vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.red));
               if (((y>0) && (y<largeur-2)) && (x< vue.getLongueur() - 1))
                {
                   vue.tabCasesVue[indice+largeur+1].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+largeur-1].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+largeur].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+1].setBorder(BorderFactory.createLineBorder(Color.red)); 
                   vue.tabCasesVue[indice+2].setBorder(BorderFactory.createLineBorder(Color.red)); 
                }
               break;
            case 3 :
               vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.red));
               if ((y < largeur-2)&& (x < vue.getLongueur()-2))
                {
                   vue.tabCasesVue[indice+1].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+largeur].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+largeur+2].setBorder(BorderFactory.createLineBorder(Color.red));
                   vue.tabCasesVue[indice+2*largeur].setBorder(BorderFactory.createLineBorder(Color.red));
                }
               break;
        }
        
    }
    
    public void effacerMotif() {
        int x = indice/largeur;
        int y = indice%largeur;
        switch(vue.motif)
        {
            case 0 :
               vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.black));
               break;
            case 1 :
               vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.black));
               if (((y < largeur-1) && (y>0))&& (x < vue.getLongueur()-2))
                {
                   vue.tabCasesVue[indice+largeur+1].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+2*largeur].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+2*largeur-1].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+2*largeur+1].setBorder(BorderFactory.createLineBorder(Color.black));
                }
               break;
            case 2 :
               vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.black));
               if (((y>0) && (y<largeur-2)) && (x< vue.getLongueur() - 1))
                {
                   vue.tabCasesVue[indice+largeur+1].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+largeur-1].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+largeur].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+1].setBorder(BorderFactory.createLineBorder(Color.black)); 
                   vue.tabCasesVue[indice+2].setBorder(BorderFactory.createLineBorder(Color.black)); 
                }
               break;
            case 3 :
                vue.tabCasesVue[indice].setBorder(BorderFactory.createLineBorder(Color.black));
               if ((y < largeur-2)&& (x < vue.getLongueur()-2))
                {
                   vue.tabCasesVue[indice+1].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+largeur].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+largeur+2].setBorder(BorderFactory.createLineBorder(Color.black));
                   vue.tabCasesVue[indice+2*largeur].setBorder(BorderFactory.createLineBorder(Color.black));
                }
               break;
                
        }
    }
}
