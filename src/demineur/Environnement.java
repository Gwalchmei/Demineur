/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

import java.util.List;
import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
// Pour la gestion XML
import java.io.*;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;

/**
 *
 * @author gauvain
 */
public class Environnement extends Observable implements Runnable {

    protected Case[][] tabCases;
    //protected boolean miseenpause;
    protected HashMap<Case, Point> map;
    protected boolean demarre;
    protected int vitesse;
    protected int timer;
    protected int totalMine;
    protected int largeur;
    protected int longueur;
    protected int nbOpened;
    protected int nbFlagged;
    protected boolean lost;
    protected int difficulte;
    
    static final int EASY = 0;
    static final int MEDIUM = 1;
    static final int HARD = 2;
    
    public Environnement()
    {
        initialisation(Environnement.MEDIUM);
    }
    
    public void initialisation(int _difficulte)
    {
        switch(_difficulte) {
            case Environnement.EASY:
                largeur = longueur = 8;
                totalMine = 10;
                break;
            case Environnement.MEDIUM:
                largeur = longueur = 16;
                totalMine = 40;
                break;
            case Environnement.HARD:
                largeur = 31;
                longueur = 16;
                totalMine = 99;
            default:
                largeur = longueur = 30;
                totalMine = 99;
                break;
        }
        difficulte = _difficulte;
        map = new HashMap(largeur*longueur);
        tabCases = new Case[largeur][longueur];
        int nbMine = 0;
        for (int i = 0; i<largeur; i++)
        {
            for(int j = 0; j<longueur; j++)
            {
                tabCases[i][j] = new Case();
                map.put(tabCases[i][j], new Point(i,j));
            }
        }
        while (nbMine < totalMine) {
            for (int i = 0; i<largeur; i++)
            {
                for(int j = 0; j<longueur; j++)
                {
                    Case current = tabCases[i][j];
                    if (Math.random() < 0.1 && nbMine < totalMine && !current.getMined()) {
                        current.setMined();
                        nbMine++;
                    }
                }
            }
        }
        
        /*miseenpause = */demarre = lost = false; 
        vitesse = 1000;
        nbOpened = nbFlagged = timer = 0;
        setChanged();
        notifyObservers();
    }
    
    public int getLongueur()
    {
        return longueur;
    }
    
    public int getLargeur()
    {
        return largeur;
    }
    
    public int getTimer()
    {
        return timer;
    }
    
    public int getVitesse()
    {
        return vitesse;
    }
    
    public void setVitesse(int _v)
    {
        vitesse = _v;
    }
    public Case[] getVoisins(Case c){
        Case[] voisins;
        
        int x = (int) map.get(c).getX();
        int y = (int) map.get(c).getY();
        
        // La case est sur la première ligne
        if (x == 0)
        {
            if (y==0)
            {
                voisins = new Case[3];
                voisins[0] = tabCases[0][1];
                voisins[1] = tabCases[1][0];
                voisins[2] = tabCases[1][1];
                return voisins;
            }
            if (y == (tabCases[0].length)-1)
            {
                voisins = new Case[3];
                voisins[0] = tabCases[0][y-1];
                voisins[1] = tabCases[1][y];
                voisins[2] = tabCases[1][y-1];
                return voisins;
            }
            voisins = new Case[5];
            voisins[0] = tabCases[0][y-1];
            voisins[1] = tabCases[0][y+1];
            voisins[2] = tabCases[1][y];
            voisins[3] = tabCases[1][y-1];
            voisins[4] = tabCases[1][y+1];
            return voisins;
        }
        // la case est sur la derniere ligne
        if (x == (tabCases.length)-1)
        {
            if (y==0)
            {
                voisins = new Case[3];
                voisins[0] = tabCases[x][1];
                voisins[1] = tabCases[x-1][0];
                voisins[2] = tabCases[x-1][1];
                return voisins;
            }
            if (y == (tabCases[0].length)-1)
            {
                voisins = new Case[3];
                voisins[0] = tabCases[x][y-1];
                voisins[1] = tabCases[x-1][y];
                voisins[2] = tabCases[x-1][y-1];
                return voisins;
            }
            voisins = new Case[5];
            voisins[0] = tabCases[x][y-1];
            voisins[1] = tabCases[x][y+1];
            voisins[2] = tabCases[x-1][y];
            voisins[3] = tabCases[x-1][y-1];
            voisins[4] = tabCases[x-1][y+1];
            return voisins;
        }
        // la case est sur la premiere colonne
        if (y==0)
        {
            voisins = new Case[5];
            voisins[0] = tabCases[x-1][y];
            voisins[1] = tabCases[x+1][y];
            voisins[2] = tabCases[x-1][y+1];
            voisins[3] = tabCases[x][y+1];
            voisins[4] = tabCases[x+1][y+1];
            return voisins;
        }
        // la case est sur la dernière colonne
        if (y==(tabCases[0].length)-1)
        {
            voisins = new Case[5];
            voisins[0] = tabCases[x-1][y];
            voisins[1] = tabCases[x+1][y];
            voisins[2] = tabCases[x-1][y-1];
            voisins[3] = tabCases[x][y-1];
            voisins[4] = tabCases[x+1][y-1];
            return voisins;
        }
        // sinon on est en plein milieu
        voisins = new Case[8];
        voisins[0] = tabCases[x-1][y-1];
        voisins[1] = tabCases[x-1][y];
        voisins[2] = tabCases[x-1][y+1];
        voisins[3] = tabCases[x+1][y-1];
        voisins[4] = tabCases[x+1][y];
        voisins[5] = tabCases[x+1][y+1];
        voisins[6] = tabCases[x][y-1];
        voisins[7] = tabCases[x][y+1];    
        return voisins;
    }
    
    /*public void setPause(boolean p)
    {
        miseenpause = p;
        
        //System.out.println("dans setPause : miseenpause = " + miseenpause);
    }
    
    public boolean getPause()
    {
        return miseenpause;
    }*/
    public void effacer()
    {
        for (int i = 0; i<tabCases.length; i++)
        {
            for (int j = 0; j<tabCases[0].length; j++)
            {
                tabCases[i][j].setEtatCourant(Case.CLOSE);
            }
        }
    }
    
    /*synchronized public void setActif()
    {
        miseenpause = false;
        notifyAll();
    }*/
    
    synchronized public void demarrer()
    {
        demarre = true;
        //miseenpause = false;
        notifyAll();
    }
    
    public boolean getDemarre() {
        return demarre;
    }
    
    @Override
    public void run() {
        while(true)
        {
            if (!demarre) {
                synchronized (this)
                {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                timer++;
            }
            
            /*if(miseenpause){
                synchronized(this){
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                timer++;
            }*/
            
            try {
                Thread.currentThread().sleep(vitesse);
            } catch (InterruptedException ex) {
                Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (nbOpened+totalMine == largeur*longueur || lost) {
                initialisation(difficulte);
            }
            //System.out.println("Je me réveille et je notifie les mises à jours");
            setChanged();
            notifyObservers();
        }
    }
    
    public void cliqueSouris(int x, int y, boolean mainclick)
    {
        tabCases[x][y].cliqueSouris(this, mainclick);
        setChanged();
        notifyObservers();
    }
    
    synchronized public void addOpened()
    {
        nbOpened++;
    }
    
    public int getNbFlagged()
    {
        return nbFlagged;
    }
    
    public int getTotalMine()
    {
        return totalMine;
    }
    
    public void addFlagged()
    {
        nbFlagged++;
    }
    
    public void removeFlagged()
    {
        nbFlagged--;
    }
    
    public void setLost(boolean flag)
    {
        lost = flag;
    }
    
    public int getDifficulte()
    {
        return difficulte;
    }
}

