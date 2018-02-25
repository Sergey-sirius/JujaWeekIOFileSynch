import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileSynch {

    static Map<String,Long> sourceList = new TreeMap<>();
    static Map<String,Long> destList = new TreeMap<>();

//    Map sortedMap = new TreeMap(new Comparator() {
//
//        @Override
//        public int compare(K k1, K k2) {
//            return k1.compareTo(k2);
//        }
//
//    });
//sortedMap.putAll(Map);

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

        File folderSource = new File(args[0]);
        File folderDestination = new File(args[1]);

        // получаем список файлов источника
        if(folderSource.exists()){
            getFile(args[0],sourceList,args[0]);
        }else{
            System.out.println("FilePath " + folderDestination + " NOT exist");
            System.exit(-1);
        }

        // получаем список файлов приемника
        if(folderDestination.exists()){
            getFile(args[1],destList,args[1]);
            destList.forEach((k,v) -> {
                // если файлы есть в обоих каталогах и размеры одинаковые
                if(sourceList.containsKey(k) && sourceList.get(k) == v){
                    sourceList.remove(k);
//                    destList.remove(k);
                }else
                // если файлы есть в обоих каталогах и размеры разные
                // то удаляем файл из папки-приемника
                if(!sourceList.containsKey(k) || (sourceList.containsKey(k) && sourceList.get(k) != v)){
                    File f = new File(finalArgs[1]+k);
                    boolean isDelete = false;
                    if(f.isFile()){
                        isDelete = f.delete();
//                        destList.remove(k);
                        System.out.println("delete file " + (finalArgs[1] + k) + "; " + isDelete);
                    }
                }
            });
            destList.forEach((k,v) -> {
                // удаляем пустые катологи в приемнике
                if(!sourceList.containsKey(k) || (sourceList.containsKey(k) && sourceList.get(k) != v)){
                    File f = new File(finalArgs[1]+k);
                    System.out.println("Folder : " + finalArgs[1]+k +"; f.length() = " + f.length());
                    System.out.println("Folder : " + finalArgs[1]+k +"; f.listFiles().length = " + f.listFiles().length);
                    if(f.isDirectory() && f.listFiles().length == 0 ){
//                        destList.remove(k);
                        f.deleteOnExit();
                        System.out.println("delete folder " + (finalArgs[1] + k) + "; ");
                    }
                }
            });
        }else{
            folderDestination.mkdir();
        }
        sourceList.forEach((k, v) -> {
            copyFile(finalArgs[0],finalArgs[1], k);
//            System.out.println("copy file " + (finalArgs[0] + k) + " into " + finalArgs[1]);
        });
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

    public static boolean copyFile(String source, String destination, String fileName){

        return true;
    }

}
