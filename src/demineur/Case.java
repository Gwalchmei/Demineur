/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;
import java.awt.event.MouseEvent;
import java.lang.Math;

/**
 *
 * @author gauvain
 */
public class Case {
    
    protected int etatCourant;
    protected boolean mined;
    protected int nbMined;
    protected int type;
    protected boolean sens;
    /* Etat */
    static int CLOSE = 0;
    static int OPEN = 1;
    static int FLAGGED = 2;
    
    /* Type */
    static int NORMAL = 0;
    static int WALL = 1;
    
    /* Sens */
    static boolean UP = true;
    static boolean DOWN = false;
    
    public Case(int _type, boolean _sens)
    {
        super();
        type = _type;
        sens = _sens;
        if (type == Case.NORMAL) {
        etatCourant = Case.CLOSE;
        } else {
            etatCourant = Case.OPEN;
            nbMined = 0;
        }
    }
    
    public int getType()
    {
        return type;
    }
    
    public boolean getSens()
    {
        return sens;
    }
    
    public void setEtatCourant(int e)
    {
        etatCourant = e;
    }
    public int getEtatCourant()
    {
        return etatCourant;
    }
    
    public void cliqueSouris(Environnement env, boolean mainclick)
    {
        if(!env.getDemarre()) { env.demarrer(); }
        if (mainclick && type != Case.WALL) {
            if (etatCourant == Case.CLOSE) {
                etatCourant = Case.OPEN;
                env.addOpened();
                if (!mined) {
                    Case[] voisins = env.getVoisins(this);
                    int _nbMined = 0;
                    for (Case voisin : voisins) {
                        if (voisin != null && voisin.getMined()) {
                            _nbMined++;
                        }
                    }
                    nbMined = _nbMined;
                    if (nbMined == 0){
                        cliqueVoisins(env, voisins);
                    }
                } else {
                    env.showMines();
                    env.setLost(true);
                }
            } else if (etatCourant == Case.OPEN && nbMined > 0) {
                Case[] voisins = env.getVoisins(this);
                Case currentVoisin;
                int _nbFlagged = 0;
                boolean[] closedCase = new boolean[12];
                for (int i = 0; i < voisins.length; i++){
                    currentVoisin = voisins[i];
                    if (currentVoisin != null) {
                    
                        if (currentVoisin.isFlagged()) { 
                            _nbFlagged++; 
                        }
                        closedCase[i] = !currentVoisin.isOpen();
                    } else {
                        closedCase[i] = false;
                    }
                    
                      
                }

                if (nbMined == _nbFlagged){
                    for (int i = 0; i < voisins.length; i++){
                        if (closedCase[i]) {
                            voisins[i].cliqueSouris(env, true);
                        }
                    }
                }
            }
        } else if (!mainclick && etatCourant != Case.OPEN) {
            if (etatCourant == Case.CLOSE) {
                etatCourant = Case.FLAGGED;
                env.addFlagged();
            } else {
                etatCourant = Case.CLOSE;
                env.removeFlagged();
            }
        }
        
    }
    
    public void cliqueVoisins(Environnement env, Case[] voisins)
    {
        for (Case voisin : voisins) {
            if(voisin != null) {voisin.cliqueSouris(env, true);}
        }
    }
    
    public boolean isOpen()
    {
        return (etatCourant == Case.OPEN);
    }
    
    public boolean isFlagged()
    {
        return (etatCourant == Case.FLAGGED);
    }
    
    public boolean getMined()
    {
        return mined;
    }
    
    public void setMined()
    {
        mined = true;
    }
    
    public int getNbMined()
    {
        return nbMined;
    }
}
