public class FileSynch {
    public static void main(String[] args) {
        if(args == null || args.length != 2){
            System.err.println("Not enough arguments.");
            System.exit(-1);
        }
        System.out.println("args-1 : " + args[0]);
        System.out.println("args-2 : " + args[1]);
    }
}
