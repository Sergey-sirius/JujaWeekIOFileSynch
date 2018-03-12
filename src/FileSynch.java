import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileSynch {

    static Map<String,Long> sourceList = new TreeMap<>();
    static Map<String,Long> destList = new TreeMap<>();

    public static void main(String[] args) throws IOException {

//        File dest222 = new File("D:/!!!!!_dest/222/");
//        File source222 = new File("D:/!!!!!_source/222");
//        System.out.println("dest222.exists() = " + dest222.exists());
//        System.out.println("dest222.length() = " + dest222.length());
//        System.out.println("dest222.getTotalSpace() = " + dest222.getTotalSpace());
//        System.out.println("dest222.canWrite() = " + dest222.canWrite());
//        System.out.println("dest222.canRead() = " + dest222.canRead());
//        System.out.println("source222.exists() = " + source222.exists());
//        System.out.println("source222.length() = " + source222.length());
//        System.out.println("source222.getTotalSpace() = " + source222.getTotalSpace());
//        System.out.println("source222.canWrite() = " + source222.canWrite());
//        System.out.println("source222.canRead() = " + source222.canRead());

//        File testFile = new File("D:/!!!!!!_test");
////        File testFile = new File("D:/!!!!!!_test/1/12_1/13_1/");
//        deleteFolder(testFile.getAbsolutePath());
//        boolean b1 = testFile.delete(); // не работает
//        System.out.println("testFile.exists() = " + testFile.exists());
//        System.out.println("b1 = " + b1);
//        testFile.deleteOnExit();
//        System.out.println("testFile.exists() = " + testFile.exists());
//        testFile.exists();
//        System.out.println("testFile.exists() = " + testFile.exists());
////        System.out.println("testFile.exists() = " + testFile.exists());




        args = new String[]{"D:/!!!!!_source","D:/!!!!!_dest"};

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
        // обрабатывапем файлы
        for (Map.Entry<String, Long> destinationFile : destList.entrySet()) {
            String filePath = destinationFile.getKey();
            File f = new File(args[1] + filePath);
            if(f.isFile()) {
                Long fileSize = destinationFile.getValue();
                if (sourceList.containsKey(filePath) && sourceList.get(filePath).equals(fileSize)) {
                    System.out.println(args[1] + filePath + " : удалить из списка источника - файл без изменений, удалять и копировать не надо.");
                    sourceList.remove(filePath);
                } else if (!sourceList.containsKey(filePath)) {
                    System.out.println(args[1] + filePath + " : удалить файл");

                }
            }
        }

        System.out.println("\n3---------------------------\n");
        // обрабатываем каталоги
        for (Map.Entry<String, Long> destinationFile : destList.entrySet()) {
            String filePath = destinationFile.getKey();
            File directory = new File(args[1] + filePath);
            if(directory.isDirectory()) {
                if (sourceList.containsKey(filePath)) {
                    System.out.println(filePath + " : удалить из списка источника - файл без изменений, удалять и копировать не надо.");
                    sourceList.remove(filePath);
                } else if (!sourceList.containsKey(filePath)) {
                    System.out.println(args[1] + filePath + " : удалить каталог");
//                    deleteFolder(args[1] + directory);
                }
            }
        }

        System.out.println("\n4---------------------------\n");

        sourceList.forEach((k, v) -> {
            System.out.println("sourceList file " + k + "; size = " + v);
        });

//        sourceList.forEach((k, v) -> {
//            copyFile(finalArgs[0],finalArgs[1], k);
//            System.out.println("copy file " + (finalArgs[0] + k) + " into " + finalArgs[1]);
//        });



//        for (Map.Entry<String, Long> file : destList.entrySet()) {
//            String filePath = file.getKey();
//            Long fileSize = file.getValue();
//            File f = new File(finalArgs[1] + filePath);
//            if(!sourceList.containsKey(filePath)){
//                if(f.isFile()){
//                    System.out.println("delete file " + (finalArgs[1] + filePath) + "; " + f.delete());
//                }else{
//                    deleteFolder(finalArgs[1] + filePath);
//                    System.out.println("delete folder " + (finalArgs[1] + filePath) + ";");
//                }
//            }else if(sourceList.containsKey(filePath) &&
//                    sourceList.get(filePath).equals(fileSize) &&
//                    f.isFile()){
//                sourceList.remove(filePath);
//            }else if(sourceList.containsKey(filePath) &&
//                        (sourceList.containsKey(filePath) &&
//                            !sourceList.get(filePath).equals(fileSize) &&
//                            f.isFile())){
//                File f1 = new File(finalArgs[1]+filePath);
//                boolean isDelete = false;
//                if(f1.isFile()){
//                    isDelete = f1.delete();
//                    System.out.println("delete file " + (finalArgs[1] + filePath) + "; " + isDelete);
//                }else{
//                    deleteFolder(finalArgs[1] + filePath);
//                    System.out.println("delete folder " + (finalArgs[1] + filePath) + "; " + isDelete);
//                }
//            }
//        }


/*
        sourceList.forEach((k, v) -> {
            copyFile(finalArgs[0],finalArgs[1], k);
            System.out.println("copy file " + (finalArgs[0] + k) + " into " + finalArgs[1]);
        });
*/
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
