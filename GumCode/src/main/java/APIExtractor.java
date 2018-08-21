// Written by Sarah Nadi and Aida Radu
// July 2018

// Last updated: July 20, 2018

// requirements:
// - GumTreeDiff release 2.1.1 and above: https://github.com/GumTreeDiff/gumtree/releases

import java.io.*;
import java.util.*;

import com.github.gumtreediff.actions.ActionGenerator;
import com.github.gumtreediff.actions.model.Action;

import com.github.gumtreediff.actions.model.Update;
import com.github.gumtreediff.gen.Generators;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.matchers.Matcher;
import com.github.gumtreediff.matchers.Matchers;
import com.github.gumtreediff.tree.ITree;
import com.github.gumtreediff.client.Run;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;


public class APIExtractor {

    public static void main(String[] args) throws IOException {
        /* Prints API changes between two files */
        
        /* ***** NOTES *****/
           /* Currently the file names are hardcoded for testing on the simple examples.
           Location of change is currently identified by hash code, but we can change this 
           to node type, position, line number, etc. */
    
        Run.initGenerators();

        String file1 = "./Test.java";
        String file2 = "./TestEdited.java";

        ITree src = Generators.getInstance().getTree(file1).getRoot();
        ITree dst = Generators.getInstance().getTree(file2).getRoot();

        Matcher m = Matchers.getInstance().getMatcher(src, dst); 
        m.match();
        MappingStore ms = m.getMappings();  // obtain pairs of matched nodes
        ActionGenerator gen = new ActionGenerator(src, dst, ms);
        gen.generate();
        List<Action> actions = gen.getActions(); // obtain the changes between trees
        
        // get ASTs from the files
        ASTNode src_ast = treeAST(file1);
        ASTNode dst_ast = treeAST(file2);

        System.out.println("API CHANGES:");

        for (int i = 0; i < actions.size(); i++) {
            // output relevant changes
            
            Action current = actions.get(i);
            if (current instanceof Update) {
            
                // info for the original api
                int src_len = current.getNode().getLength();
                int src_pos = current.getNode().getPos();
                
                // info for the new api
                int dst_len = ms.getDst(current.getNode()).getLength();
                int dst_pos = ms.getDst(current.getNode()).getPos();


                NodeFinder src_nf = new NodeFinder(src_ast, src_pos, src_len);
                NodeFinder dst_nf = new NodeFinder(dst_ast, dst_pos, dst_len);
                ASTNode src_node = src_nf.getCoveredNode();
                ASTNode dst_node = dst_nf.getCoveredNode();

                String src_name = fullName(src_node);
                String dst_name = fullName(dst_node);
                
                // skip changes like renaming the class
                if (src_name.contains(".") || dst_name.contains(".")){
                    System.out.println(current.getNode().getHash()+": "+ src_name + " -> "+ dst_name);
                }

                }
            }


        }

    public static ASTNode treeAST(String file_name){
        // Created by snadi on 2018-07-16
        // Adapted by Aida Radu 2018-07-19

        File file = new File(file_name);

        String name = file.getName();
        String source = readStringFromFile(file.getAbsolutePath());
        Map options = JavaCore.getOptions();
        options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_8);
        options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8);
        ASTParser parser = ASTParser.newParser(AST.JLS8);
        parser.setCompilerOptions(options);
        parser.setEnvironment(
                new String[]{},
                new String[]{},
                new String[]{},
                true);
        parser.setResolveBindings(true);
        parser.setBindingsRecovery(true);
        parser.setSource(source.toCharArray());
        parser.setUnitName(name);
        ASTNode ast = parser.createAST(null);

        return ast;

    }

    public static String fullName(ASTNode node){
        // Created by snadi on 2018-07-16 as preVisit(ASTNode node)
        // Adapted by Aida Radu 2018-07-19

        ITypeBinding b = null;
        if (node instanceof Name) {
            Name name = (Name) node;
            b = name.resolveTypeBinding();
            if (b != null)
                return b.getQualifiedName();
        }
        else if (node instanceof Expression) {
            Expression e = (Expression) node;
            b = e.resolveTypeBinding();
            if (b != null)
                return b.getQualifiedName();

        }

        return "null";

    }

    public static String readStringFromFile(String inputFile) {
        // Created by snadi on 2018-07-16
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile));
            byte[] bytes = new byte[(int) new File(inputFile).length()];
            in.read(bytes);
            in.close();
            return new String(bytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
