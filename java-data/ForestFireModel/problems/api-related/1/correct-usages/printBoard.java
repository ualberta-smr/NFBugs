package ForestFireModell;

import automaton.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator;

/**
 * @author Matthias Willenbrink;
 *
 */
 
public class ForestFire_CA extends CellularAutomaton {

    private static final boolean flagOutputOnStream = true;


    // private String boardVector;
    private StringBuilder sb = new StringBuilder();
  public void printBoard(long timer) {
        CellProperty cp;
        int state;
        int xDim, yDim;

        xDim = (int) this.getDimension().getXDim();
        yDim = (int) this.getDimension().getYDim();

        // create a graphic
        BufferedImage bi = new BufferedImage(xDim, yDim,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();

        // boardVector = "";

        Object[] gac = this.getCellList().toArray();

        // Define Colors for the output file:

        Color ground = new Color(255, 207, 179);
        Color rock = new Color(40, 40, 40);
        Color fire1 = new Color(255,54, 20  );
        Color fire2 = new Color(209,45, 17);
        Color fire3 = new Color(163,35, 13);
        Color fire4 = new Color(116,25, 10);
        Color fire5 = new Color(70,15, 6);
        Color tree = new Color(12, 135, 87);
        Color grass = new Color(86, 202, 43);
        Color grassFire = new Color(255, 130, 32);

        for (int y = 0; y <= yDim - 1; y++) {
            for (int x = 0; x <= xDim - 1; x++) {
                cp = ((ForestFire_Cell) gac[xDim * y + x]).getCellProperty();
                state = (int) cp.getValue();

                switch(state){
                    case 0:
                        if(flagOutputOnStream) System.out.print("□");
                        sb.append("□ ");
                        // boardVector += "□ ";
                        g2d.setColor(rock);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 1:
                        if(flagOutputOnStream) System.out.print("-");
                        sb.append("- ");
                        // boardVector += "- ";
                        g2d.setColor(ground);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 2:
                        if(flagOutputOnStream) System.out.print("w");
                        sb.append("w ");
                        // boardVector += "w ";
                        g2d.setColor(grass);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 3:
                        if(flagOutputOnStream) System.out.print("w");
                        sb.append("w ");
                        // boardVector += "w ";
                        g2d.setColor(grassFire);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 4:
                        if(flagOutputOnStream) System.out.print("Δ");
                        sb.append("Δ ");
                        // boardVector += "Δ ";
                        g2d.setColor(tree);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 5:
                        if(flagOutputOnStream) System.out.print("Δ");
                        sb.append("Δ ");
                        // boardVector += "Δ ";
                        g2d.setColor(fire1);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 6:
                        if(flagOutputOnStream) System.out.print("Δ");
                        sb.append("Δ ");
                        // boardVector += "Δ ";
                        g2d.setColor(fire2);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 7:
                        if(flagOutputOnStream) System.out.print("Δ");
                        sb.append("Δ ");
                        // boardVector += "Δ ";
                        g2d.setColor(fire3);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 8:
                        if(flagOutputOnStream) System.out.print("Δ");
                        sb.append("Δ ");
                        // boardVector += "Δ ";
                        g2d.setColor(fire4);
                        g2d.drawLine(x, y, x, y);
                        break;
                    case 9:
                        if(flagOutputOnStream) System.out.print("Δ");
                        sb.append("Δ ");
                        // boardVector += "Δ ";
                        g2d.setColor(fire5);
                        g2d.drawLine(x, y, x, y);
                        break;
                    default:
                        System.err.println("Invalid state in printBoard()");
                        break;


                }
            }

            sb.append("\n");
            // boardVector = sb.toString();
            // boardVector += "\n";
            if(flagOutputOnStream) System.out.println();
        }

        // save the result as a png file
        g2d.dispose();
        String typ = "png";

        DecimalFormat df = new DecimalFormat("0000");

        // sample sample sample
        // TODO: Create new folder for each run instead of overwriting the old files.
        File datei = new File("C:/Users/Matthias/JavaProjects/Waldbrandmodell/out/BoardImages/Step" + df.format(timer)
                + ".".concat(typ));
        try {
            ImageIO.write(bi, typ, datei);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

