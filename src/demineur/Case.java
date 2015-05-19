/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;
import java.lang.Math;

/**
 *
 * @author gauvain
 */
public class Case {
    
    protected boolean etatCourant;
    protected boolean etatSuivant;
    protected boolean mined;
    protected int nbMined;
    protected boolean open;
    
    public void setEtatAlea()
    {
        etatCourant = (Math.random() > 0.5);
    }
    
    public void setEtatCourant(boolean b)
    {
        etatCourant = b;
    }
    public boolean getEtatCourant()
    {
        return etatCourant;
    }
    
    public void miseAjour()
    {
        etatCourant = etatSuivant;
    }
    
    public void cliqueSouris(Environnement env)
    {
        etatCourant = !etatCourant;
        if (!open) {
            Case[] voisins = env.getVoisins(this);
            int _nbMined = 0;
            for (int i = 0; i < voisins.length; i++){
                if (voisins[i].getMined()) { _nbMined++; }
            }
            nbMined = _nbMined;
            open = true;
            if (nbMined == 0){
                for (int i = 0; i < voisins.length; i++){
                    voisins[i].cliqueSouris(env);
                }
            }
        }     
    }
    
    public boolean getOpen()
    {
        return open;
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
    
    public void setEtatSuivant(Environnement env)
    {
        // Décide de l'état suivant en appliquant les règles de bases
        Case[] voisins = env.getVoisins(this);
        int nombreVoisins=0;
        for (int i = 0; i < voisins.length; i++)
        {
            if (voisins[i].getEtatCourant())
            {
                nombreVoisins++;
            }
        }
        
        switch (nombreVoisins)
        { 
            case 2 : etatSuivant = etatCourant;
                     break;
            case 3 : etatSuivant = true;
                     break;
            default : etatSuivant = false;
                      break;   
        }   
    }
}
