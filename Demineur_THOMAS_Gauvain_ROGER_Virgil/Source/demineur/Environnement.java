/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Integer.max;


/**
 *
 * @author gauvain
 */
public class Environnement extends Observable implements Runnable {

    protected Case[][] tabCases;
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
    protected boolean gameOver;
    protected int difficulte;
    protected int mode;
    
    static final int EASY = 0;
    static final int MEDIUM = 1;
    static final int HARD = 2;
    static final int SQUARE = 0;
    static final int TRIANGLE = 1;
    
    public Environnement()
    {
        initialisation(Environnement.MEDIUM, Environnement.SQUARE);
    }
    
    public void initialisation(int _difficulte, int _mode)
    {
        difficulte = _difficulte;
        if (_mode == Environnement.SQUARE) {
            switch(difficulte) {
                case Environnement.EASY:
                    initialisation(10, 10, 10, _mode);
                    break;
                case Environnement.MEDIUM:
                    initialisation(18,18,40, _mode);
                    break;
                case Environnement.HARD:
                    initialisation(18,33,99, _mode);
                    break;
                default:
                    initialisation(33,33,99, _mode);
                    break;
            }
        } else {
            switch(difficulte) {
                case Environnement.EASY:
                    initialisation(9,13,5, _mode);
                    break;
                case Environnement.MEDIUM:
                    initialisation(12,19,10, _mode);
                    break;
                case Environnement.HARD:
                    initialisation(18,31,15, _mode);
                    break;
                default:
                    initialisation(30,30,10, _mode);
                    break;
            }
        }
    }
    
    public void initialisation(int _largeur, int _longueur, int _totalMine, int _mode) {
        largeur = _largeur;
        longueur = _longueur;
        totalMine = _totalMine;
        mode = _mode;
        nbOpened = 0;
        int max = max(largeur, longueur);
        map = new HashMap(max*max);
        tabCases = new Case[largeur][longueur];
        int nbMine = 0;
        if (mode == Environnement.SQUARE) {
            for (int i = 0; i<largeur; i++)
            {
                for(int j = 0; j<longueur; j++)
                {
                    if (j < 1 || i < 1 || j > longueur-2 || i > largeur-2) {
                        tabCases[i][j] = new Case(Case.WALL, Case.UP);
                        addOpened();
                    } else {
                        tabCases[i][j] = new Case(Case.NORMAL, Case.UP);
                    }
                    
                    map.put(tabCases[i][j], new Point(i,j));
                }
            }
        } else {
            boolean lastSens = Case.DOWN;
            for (int i = 0; i<largeur; i++)
            {
                for(int j = 0; j<longueur; j++)
                {
                    if (j >= Math.floor(longueur/2)-i+2 && j <= Math.floor(longueur/2)+i-2 && i < largeur-2) {
                        lastSens = !lastSens;
                        tabCases[i][j] = new Case(Case.NORMAL, lastSens);
                        
                    } else {
                        tabCases[i][j] = new Case(Case.WALL, Case.UP);
                        addOpened();
                    }
                    map.put(tabCases[i][j], new Point(i,j));
                }
            }
        }
        while (nbMine < totalMine) {
            for (int i = 0; i<largeur; i++)
            {
                for(int j = 0; j<longueur; j++)
                {
                    Case current = tabCases[i][j];
                    if (current.getType() != Case.WALL && Math.random() < 0.1 && nbMine < totalMine && !current.getMined()) {
                        current.setMined();
                        nbMine++;
                    }
                }
            }
        }
        
        gameOver = demarre = lost = false; 
        vitesse = 1000;
        nbFlagged = timer = 0;
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

        if (mode == Environnement.TRIANGLE){
           voisins = new Case[12];
           voisins[8] = tabCases[x][y-2];
           voisins[9] = tabCases[x][y+2];
           if (c.getSens() == Case.UP) {
               voisins[10] = tabCases[x+1][y-2];
               voisins[11] = tabCases[x+1][y+2];
           } else {
               voisins[10] = tabCases[x-1][y-2];
               voisins[11] = tabCases[x-1][y+2];
           }
        } else {
            voisins = new Case[8];
        }
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
    
    synchronized public void demarrer()
    {
        demarre = true;
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
            
            try {
                Thread.currentThread().sleep(vitesse);
            } catch (InterruptedException ex) {
                Logger.getLogger(Environnement.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (nbOpened+totalMine == largeur*longueur || lost) {
                this.gameOver();
                initialisation(difficulte, mode);
            }
            setChanged();
            notifyObservers();
        }
    }
    
    public void gameOver() {
        gameOver = true;
        setChanged();
        notifyObservers();
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
    
    public int getMode()
    {
        return mode;
    }
    
    public void showMines() {
        for(Case[] row : tabCases) {
            for(Case c : row) {
                if(c.getMined()) {
                    c.setEtatCourant(Case.OPEN);
                }
            }
        }
    }
}