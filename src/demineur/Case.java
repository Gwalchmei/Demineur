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
    
    static int CLOSE = 0;
    static int OPEN = 1;
    static int FLAGGED = 2;
    
    public Case()
    {
        super();
        etatCourant = Case.CLOSE;
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
        if(!env.getPause()) {
            if(!env.getDemarre()) { env.demarrer(); }
            if (mainclick) {
                if (etatCourant == Case.CLOSE) {
                    etatCourant = Case.OPEN;
                    env.addOpened();
                    if (!mined) {
                        Case[] voisins = env.getVoisins(this);
                        int _nbMined = 0;
                        for (int i = 0; i < voisins.length; i++){
                            if (voisins[i].getMined()) { _nbMined++; }
                        }
                        nbMined = _nbMined;
                        if (nbMined == 0){
                            cliqueVoisins(env, voisins);
                        }
                    } else {
                        env.setLost(true);
                    }
                } else if (etatCourant == Case.OPEN && nbMined > 0) {
                    Case[] voisins = env.getVoisins(this);
                    Case currentVoisin = null;
                    int _nbFlagged = 0;
                    boolean[] closedCase = new boolean[8];
                    for (int i = 0; i < voisins.length; i++){
                        currentVoisin = voisins[i];
                        if (currentVoisin.isFlagged()) { _nbFlagged++; }
                        closedCase[i] = !currentVoisin.isOpen();
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
        
    }
    
    public void cliqueVoisins(Environnement env, Case[] voisins)
    {
        for (int i = 0; i < voisins.length; i++){
            voisins[i].cliqueSouris(env, true);
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
