import java.io.*;

public class FileSynch {

    public static void main(String[] args) throws IOException {
//        args = new String[]{"D:/!!!!!_source","D:/!!!!!_dest"};
        checkParams(args);

        System.out.println("Source folder : " + args[0]);
        System.out.println("Destination folder : " + args[1]);

        File folderSource = new File(args[0]);
        File folderDestination = new File(args[1]);

        // получаем список файлов приемника
        if(folderDestination.exists()){
            checkDestination(args[0],args[1],args[1]);
        }else{
            if(!folderDestination.mkdir()){
                System.out.println("Anything wrong - " + folderDestination.getCanonicalPath() + " NOT ecreated.");
                System.exit(-1);
            }
        }
        // получаем список файлов источника
        if(folderSource.exists()){
            checkSource(args[0],args[1],args[0]);
        }else{
            System.out.println("FilePath " + folderSource.getCanonicalPath() + " NOT exist");
            System.exit(-1);
        }

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

    private static void checkDestination(String folderSource, String folderDestination, String currentPath){

        File f = new File(currentPath);
        File fSource = new File(folderSource + currentPath.replace(folderDestination,""));
        if(f.isFile()) {
            if(!fSource.exists() || !fSource.isFile() || (f.length() != fSource.length())){
                System.out.println("Delete destination file " + currentPath);
                f.delete();
            }
            return;
        }

        File[] files = f.listFiles();
        for(File file : files) {
            checkDestination(folderSource,folderDestination,file.getAbsolutePath());
        }

        if(!fSource.exists() || !fSource.isDirectory()){
            System.out.println("Delete folder " + currentPath);
            f.delete();
        }
    }

    private static void checkSource(String folderSource, String folderDestination, String currentPath){

        File f = new File(currentPath);
        File fDestination = new File(folderDestination + currentPath.replace(folderSource,""));

        if(!fDestination.exists()){
            copyFile(currentPath,folderDestination + currentPath.replace(folderSource,""));
        }

        if(f.isFile()){
            return;
        }

        File[] files = f.listFiles();
        for(File file : files) {
            checkSource(folderSource,folderDestination,file.getAbsolutePath());
        }

    }

    private static boolean copyFile(String sourcePath, String destinationPath){
        File sourceFile = new File(sourcePath);

        if(sourceFile.isFile()){
            System.out.println("Copy file " + sourcePath + " to " + destinationPath);
            try(FileInputStream fin=new FileInputStream(sourcePath);
                FileOutputStream fos=new FileOutputStream(destinationPath))
            {
                byte[] buffer = new byte[fin.available()];
                // считываем буфер
                fin.read(buffer, 0, buffer.length);
                // записываем из буфера в файл
                fos.write(buffer, 0, buffer.length);
            }
            catch(IOException ex){

                System.out.println(ex.getMessage());
            }
        }else{
            System.out.println("Create new folder " + destinationPath);
            File destinationFolder = new File(destinationPath);
            destinationFolder.mkdir();
        }
        return true;
    }
}
