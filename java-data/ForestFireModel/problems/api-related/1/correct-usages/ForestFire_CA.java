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
 */
 
public class ForestFire_CA extends CellularAutomaton {

    private StringBuilder sb = new StringBuilder();
    
    public void pattern(long timer) {
        
        for (int y = 0; y <= yDim - 1; y++) {
            for (int x = 0; x <= xDim - 1; x++) {
                cp = ((ForestFire_Cell) gac[xDim * y + x]).getCellProperty();
                state = (int) cp.getValue();

                switch(state){
                    case 0:
                        sb.append("□ ");
                        break;
                    case 1:
                        sb.append("- ");
                        break;
                    case 2:                        
                        sb.append("w ");                      
                        break;
                    case 3:
                        sb.append("w ");
                        break;
                    case 4:
                        sb.append("Δ ");
                        break;
                    case 5:
                        sb.append("Δ ");
                        break;
                    case 6:
                        sb.append("Δ ");
                        break;
                    case 7:
                        sb.append("Δ ");
                        break;
                    case 8:
                        sb.append("Δ ");
                        break;
                    case 9:
                        sb.append("Δ ");
                        break;
                    default:
                        System.err.println("Invalid state in printBoard()");
                        break;
                }
            }

            sb.append("\n");
        }

    }
}
