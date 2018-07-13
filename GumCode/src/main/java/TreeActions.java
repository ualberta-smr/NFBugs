
import com.github.gumtreediff.gen.Generators;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.actions.*;
import com.github.gumtreediff.matchers.*;
import com.github.gumtreediff.actions.model.*;
import com.github.gumtreediff.tree.*;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.tree.hash.HashUtils.*;
import java.util.List;
import java.io.IOException;

import javassist.compiler.ast.ASTree;
import org.eclipse.jdt.core.dom.*;
import com.github.gumtreediff.gen.jdt.*;
import java.io.PrintWriter;

public class TreeActions{
    public static void main (String[] args) throws IOException{
        Run.initGenerators();
        String file1 = "/Users/radu/Documents/Test2.java";
        String file2 = "/Users/radu/Documents/TestEDITED2.java";
        ITree src = Generators.getInstance().getTree(file1).getRoot();
        ITree dst = Generators.getInstance().getTree(file2).getRoot();
        PrintWriter writer = new PrintWriter("GumOut.txt","UTF-8");
        try {

            writer.println("------------------------ TREE of ORIGINAL ---------------------------");
            writer.println(src.toTreeString());




            writer.println("------------------------ NEW TREE ---------------------------");
            writer.println(dst.toTreeString());

            Matcher m = Matchers.getInstance().getMatcher(src, dst); // retrieve the default matcher
            m.match();
            ActionGenerator g = new ActionGenerator(src, dst, m.getMappings());
            g.generate();
            List<Action> actions = g.getActions(); // return the actions



            writer.println("------------------- ACTIONS BETWEEN TREES --------------------------------------\n");
            for (int i = 0; i < actions.size(); i++) {
                writer.println(actions.get(i).toString());
            }


            writer.println("----------------------------------------------------------------");
            writer.println("We want the FULLY QUALIFIED version of:");
            writer.println(dst.getDescendants().get(10).getLabel());
        } finally {
            writer.close();

        }

    }
}
