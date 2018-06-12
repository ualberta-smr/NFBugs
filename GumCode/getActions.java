
import com.github.gumtreediff.gen.Generators;
import com.github.gumtreediff.client.Run;
import com.github.gumtreediff.actions.*;
import com.github.gumtreediff.matchers.*;
import com.github.gumtreediff.actions.model.*;
import com.github.gumtreediff.tree.*;
import java.util.List;
import java.io.IOException;

public class getActions{
    public static void main (String[] args) throws IOException{
        Run.initGenerators();
        String file1 = "/Users/radu/Documents/HelloWorld.java";
        String file2 = "/Users/radu/Documents/Hello.java";
        ITree src = Generators.getInstance().getTree(file1).getRoot();
        ITree dst = Generators.getInstance().getTree(file2).getRoot();
        Matcher m = Matchers.getInstance().getMatcher(src, dst); // retrieve the default matcher
        m.match();
        ActionGenerator g = new ActionGenerator(src, dst, m.getMappings());
        g.generate();
        List<Action> actions = g.getActions(); // return the actions
    }
}
