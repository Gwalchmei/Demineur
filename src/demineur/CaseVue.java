/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demineur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.geom.Path2D;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
//import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author gauvain
 */
public class CaseVue extends JLabel{
    /* indice correspond à l'indice de la CaseVue dans le tableau de la fenetre, largeur correspond à la largeur du tableau de l'environnement */
    protected int largeur;
    protected int longueur;
    protected Environnement env;
//    private FenetreP vue;
    protected MouseAdapter m;
    protected boolean sens = true;
    
//    private static final Color BG = Color.blue;
//    private static final Color BORDER = Color.red;
    
//    private Path2D trianglePath = new Path2D.Double();
//    
//    private int SQUARE = 0;
//    private int TRIANGLE = 1;
//    private int type = SQUARE;
    
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
//    
//    @Override
//    public int getX() {
//        if(type == SQUARE) {
//            return super.getX();
//        } else {
//            return sens ? super.getX()/2 : super.getX();
//        }
//    }

//   @Override
//   protected void paintComponent(Graphics g) {
//      super.paintComponent(g);
//      if(type == TRIANGLE) {
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setColor(Color.GREEN);
//
//        // to smooth out the jaggies
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//              RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.fill(trianglePath);  // fill my triangle
//      }
//   }
    
    public CaseVue(int _largeur, int _longueur, Environnement _env, FenetreP _vue, int _type, boolean _sens) {
        super();
        
        defaultView(false);
        
        largeur = _largeur;
        longueur = _longueur;
        env = _env;
//        vue = _vue;
//        type = _type;
        sens = _sens;
        
        m = new MouseAdapter () {
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
        };
        addMouseListener(m);
        setOpaque(true);

        
//        Dimension dim = this.getPreferredSize();
//        double W = dim.width;
//        double H = dim.height;
//        double firstX = 0;
//        double firstY = 0;
//
//        trianglePath.moveTo(firstX, firstY);
//        trianglePath.lineTo(W, firstY);
//        trianglePath.lineTo(W / 2.0, H);
//        trianglePath.closePath();
    }
    
    public final void defaultView(boolean s){
        Color bgColor = new Color(0x5a5a5a);
        setBackground(bgColor);
        setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16));
        setHorizontalAlignment(SwingConstants.CENTER);
        setText(" ");
    }
    
    public void openedView(Case c) {
        Color bgColor = new Color(0xF3F3F3);
        if (c.getType() == Case.WALL) {
            bgColor = new Color(0x2a2a2a);
            setText(" ");
        }
        Color darkBlue = new Color(0x0C165C);
        Color brown = new Color(0x5C380D);
        setBackground(bgColor);
        if (c.getNbMined() != 0) {
            setText(Integer.toString(c.getNbMined()));
            switch(c.getNbMined()) {
                case 1: setForeground(Color.BLUE);
                        break;
                case 2: setForeground(Color.GREEN);
                        break;
                case 3: setForeground(Color.RED);
                        break;
                case 4: setForeground(darkBlue);
                        break;
                case 5: setForeground(brown);
                        break;
                case 6: setForeground(Color.CYAN);
                        break;
                case 7: setForeground(Color.BLACK);
                        break;
                case 8: setForeground(Color.GRAY);
                        break;
                default: setForeground(Color.BLACK);
                         break;
            }
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
        env.cliqueSouris(largeur,longueur,e.getButton() == MouseEvent.BUTTON1);
    }
    
    public void coloriserMotif() {
        setBorder(BorderFactory.createLineBorder(Color.red));       
    }
    
    public void effacerMotif() {
        Color borderColor = new Color(0x393638);
        setBorder(BorderFactory.createLineBorder(borderColor));
    }
    
    public void destroy()
    {
        setBackground(new Color(0x121212));
        removeMouseListener(m);
    }
}