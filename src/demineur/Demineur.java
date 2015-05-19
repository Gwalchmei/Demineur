/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

/**
 *
 * @author Epulapp
 */
public class Demineur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Environnement env = new Environnement(40,40);
        FenetreP fen = new FenetreP(env);
        fen.setVisible(true);
        new Thread(env).start();
    }
    
}
