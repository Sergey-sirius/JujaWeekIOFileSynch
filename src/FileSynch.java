import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileSynch {

    static Map<String,Long> sourceList = new HashMap<>();
    static Map<String,Long> destList = new HashMap<>();

    public static void main(String[] args) throws IOException {
        args = new String[]{"D:/!!!_0///","D:/dest"};

        if(args == null || args.length != 2){
            System.err.println("Not enough arguments.");
            System.exit(-1);
        }

        args[0] = args[0].trim().replace("/","\\");
        args[1] = args[1].trim().replace("/","\\");

        while(args[0].lastIndexOf("\\") == args[0].length() - 1){
            args[0] = args[0].substring(0,args[0].length()-1);
        }

        while(args[1].lastIndexOf("\\") == args[1].length() - 1){
            args[1] = args[1].substring(0,args[1].length()-1);
        }

        args[0] = args[0].lastIndexOf("\\") == args[0].length() + 1 ? args[0] : args[0] + "\\";
        args[1] = args[1].lastIndexOf("\\") == args[1].length() + 1 ? args[1] : args[1] + "\\";

        String[] finalArgs = args;

        System.out.println("Source folder : " + args[0]);
        System.out.println("Destination folder : " + args[1]);

        File fileSource = new File(args[0]);
        File fileDestination = new File(args[1]);

        if(fileSource.exists()){
            getFile(args[0],sourceList,args[0]);
//            sourceList.forEach((k, v) -> {
//                System.out.println("fileSource = " + k + "; size = " + v);
//            });
        }else{
            System.out.println("FilePath " + fileDestination + " NOT exist");
            System.exit(-1);
        }

        if(fileDestination.exists()){
            getFile(args[1],destList,args[1]);
//            destList.forEach((k, v) -> {
//                System.out.println("fileDest = " + k + "; size = " + v);
//            });
            destList.forEach((k,v) -> {
                if(!sourceList.containsKey(k)){
                    File f = new File(k);
                    f.delete();
                    destList.remove(k);
                }
                if(sourceList.containsKey(k) && sourceList.get(k) != v){
                    File f = new File(k);
                    f.delete();
//                    copyFile(,k);
                }
            });
        }else{
            fileDestination.mkdir();
            sourceList.forEach((k, v) -> {
                copyFile(finalArgs[0] + k,finalArgs[1]); System.out.println("fileSource = " + k + "; size = " + v);
            });
        }
    }

    public static void getFile(String folder, Map fileSet, String path) throws IOException {
        File f = new File(folder);
        if(!f.exists()){
            System.out.println("There is incorrect path");
        }
        if(f.isFile()) {
            fileSet.put(f.getAbsolutePath().replace(path,"") ,f.length());
            return;
        }
        if(!f.getAbsolutePath().equals(path.substring(0,path.length()-1))) {
            fileSet.put(f.getAbsolutePath().replace(path, ""), f.length());
        }
        File[] files = f.listFiles();
        for(File file : files) {
            getFile(file.getAbsolutePath(),fileSet,path);
        }
    }

    public static boolean copyFile(String source, String destination){

        return true;
    }

}
