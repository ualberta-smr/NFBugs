package ch.hearc.st.app;

import java.util.ArrayList;

public class Utils{

  private static String pattern(int[] tab) {
      StringBuilder perm = new StringBuilder();
      int nbiter = tab.length;

      for (int m = 0; m < nbiter; m++) {
        perm.append(tab[m]);
        if (m < nbiter - 1) {
          perm.append(",");
        }
      }
      return perm.toString();
    }
}
