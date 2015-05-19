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
    protected boolean miseenpause;
    protected HashMap<Case, Point> map;
    protected boolean demarre;
    protected int vitesse;
    protected int timer;
    protected int totalMine;
    protected int largeur;
    protected int longueur;
    
    public Environnement(int x, int y)
    {
        largeur = x;
        longueur = y;
        map = new HashMap(largeur*longueur);
        tabCases = new Case[largeur][longueur];
        totalMine = largeur*longueur/10;
        int nbMine = 0;
        for (int i = 0; i<largeur; i++)
        {
            for(int j = 0; j<longueur; j++)
            {
                tabCases[i][j] = new Case();
                map.put(tabCases[i][j], new Point(i,j));
                if (Math.random() < 0.1 && nbMine < totalMine) {
                    tabCases[i][j].setMined();
                    nbMine++;
                }
            }
        }
        miseenpause = false;
        demarre = false; 
        vitesse = 1000;
        timer = 0;
        
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
    
    public void setPause(boolean p)
    {
        miseenpause = p;
        
        //System.out.println("dans setPause : miseenpause = " + miseenpause);
    }
    
    public boolean getPause()
    {
        return miseenpause;
    }
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
    
    synchronized public void setActif()
    {
        miseenpause = false;
        notifyAll();
    }
    
    synchronized public void demarrer()
    {
        demarre = true;
        miseenpause = false;
        notifyAll();
    }
    
    synchronized public void demarrerAlea()
    {
        demarre = true;
        for (int i = 0; i<tabCases.length; i++)
        {
            for (int j = 0; j<tabCases[0].length; j++)
            {
                tabCases[i][j].setEtatAlea();
            }
        }
        
        notifyAll();
    }
    
    
    @Override
    public void run() {
       while(!demarre)
       {
           synchronized (this)
           {
               try {
                   wait();
               } catch (InterruptedException ex) {
                   Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
               }
           }      
       }
        while(true)
        {
            if(miseenpause){
                synchronized(this){
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            int _nbUsed = 0;
            for(Entry<Case, Point> entry : map.entrySet()) {
                Case current = entry.getKey();
                if (current.isOpen() || current.isFlagged()) {
                    _nbUsed++;
                }
            }
            if (_nbUsed == largeur*longueur) {
                System.out.println("fini");
            }
            try {
                Thread.currentThread().sleep(vitesse);
            } catch (InterruptedException ex) {
                Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
            }
            timer++;
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
    
}

