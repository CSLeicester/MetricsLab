import util.cfg.CFGExtractor;
import util.cfg.Graph;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nw91 on 07/10/2015.
 */
public class WMCAnalysis {

    protected Map<ClassNode,Double> weightedMethodsPerClass;


    public WMCAnalysis(String root){

        weightedMethodsPerClass = new HashMap<ClassNode,Double>();
        String packageRoot = root;

        File f = new File(packageRoot); // root directory;
        Iterator<File> fileIt = FileUtils.iterateFiles(f, new WildcardFileFilter("*.class"), new WildcardFileFilter("*"));
        while(fileIt.hasNext()){
            File currentClass = fileIt.next();
            analyseClass(currentClass);
        }
        printToCSV();

    }


    // TODO print the calculated metrics in the weightedMethodsPerClass map to a csv file.
    private void printToCSV() {

    }

    private void analyseClass(File currentClass) {
        try {
            FileInputStream fis = new FileInputStream(currentClass);
            ClassReader cr = new ClassReader(fis);
            ClassNode cn = new ClassNode();
            cr.accept(cn,0);
            weightedMethodsPerClass.put(cn,computeWMC(cn, currentClass.getPath()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is just a sketch - you need to finish it off!
     * @param cn
     * @param pathToClass
     * @return
     */
    private Double computeWMC(ClassNode cn, String pathToClass) {
        Double wmc = 0D;
        for(Object method : cn.methods) {
            try {
                Graph cfg = CFGExtractor.getCFG(pathToClass, (MethodNode) method);
                System.out.println();
                //TODO code to compute Cyclomatic Complexity from cfg
            } catch (AnalyzerException e) {
                e.printStackTrace();
            }
        }
        //TODO code to combine cyclomatic complexitites for individual methods into WMC.
        return wmc;
    }


    public static void main(String[] args){
        WMCAnalysis analysis = new WMCAnalysis(args[0]);
    }

}
