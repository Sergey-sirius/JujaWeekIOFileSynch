import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileSynch {

    static Map<String,Long> sourceList = new TreeMap<>();
    static Map<String,Long> destList = new TreeMap<>();

    public static void main(String[] args) throws IOException {

        args = new String[]{"D:/!!!!!_source","D:/!!!!!_dest"};

        checkParams(args);

        System.out.println("Source folder : " + args[0]);
        System.out.println("Destination folder : " + args[1]);

        File folderSource = new File(args[0]);
        File folderDestination = new File(args[1]);


        // получаем список файлов приемника
        if(folderDestination.exists()){
            getFile(args[1],destList,args[1]);
        }else{
            if(!folderDestination.mkdir()){
                System.out.println("Anything wrong - " + folderDestination.getCanonicalPath() + " NOT ecreated.");
                System.exit(-1);
            }
        }
        // получаем список файлов источника
        if(folderSource.exists()){
            getFile(args[0],sourceList,args[0]);
        }else{
            System.out.println("FilePath " + folderSource.getCanonicalPath() + " NOT exist");
            System.exit(-1);
        }

        System.out.println("\n1---------------------------\n");
        sourceList.forEach((k, v) -> {
            System.out.println("sourceList file " + k + "; size = " + v);
        });

        System.out.println("\n2---------------------------\n");


        System.out.println("\n4---------------------------\n");

        sourceList.forEach((k, v) -> {
            System.out.println("sourceList file " + k + "; size = " + v);
        });

    }

    private static void checkParams(String[] args) {
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
    }

    public static void deleteFolder(String path){
        System.out.println("deleteFolder:path = " + path);
        File f = new File(path);
        if(f.isFile()){
            f.delete();
            return;
        }
        File[] fileList = f.listFiles();
//        if(fileList.length == 0){
//            f.deleteOnExit();
//            return true;
//        }
        for(File file:fileList){
            deleteFolder(file.getAbsolutePath());
//            if(!file.isFile()){
////                file.deleteOnExit();
//                file.delete();
//            }
            file.delete();
        }
        f.delete();
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
            fileSet.put(f.getAbsolutePath().replace(path, ""), f.isFile() ? f.length() : 0L);
        }
        File[] files = f.listFiles();
        for(File file : files) {
            getFile(file.getAbsolutePath(),fileSet,path);
        }

    }

    public static boolean copyFile(String source, String destination, String fileName){

        return true;
    }

}
