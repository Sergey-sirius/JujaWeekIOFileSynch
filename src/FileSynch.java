import java.io.File;

public class FileSynch {
    public static void main(String[] args) {
        if(args == null || args.length != 2){
            System.err.println("Not enough arguments.");
            System.exit(-1);
        }
        System.out.println("Source folder : " + args[0]);
        System.out.println("Destination folder : " + args[1]);

        File fileSource = new File(args[0]);
        File fileDestination = new File(args[1]);

        if(fileSource.exists()){
            System.out.println("FilePath " + fileDestination + " exist");
        }else{
            System.out.println("FilePath " + fileDestination + " NOT exist");
        }

    }
}
