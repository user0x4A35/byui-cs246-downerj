public class Main {
    
    public static void main(String[] args) {
        boolean debug = false;
        boolean unsecure = false;
        String keyFileName = null;
        
        for (int a = 0; a < args.length; a++) {
            String arg = args[a];
            if (arg.equals("-d") || arg.equals("--debug")) {
                debug = true;
            } else if (arg.equals("-S") || args.equals("--unsecure")) {
                unsecure = true;
            } else {
                keyFileName = arg;
            }
        }
        
        if (keyFileName == null) {
            System.err.println("Please provide the key file");
            System.exit(1);
        }
        
        WeatherAPILink link = new WeatherAPILink();
        link.setKeyFileName(keyFileName);
        link.setDebugging(debug);
        link.setUnsecure(unsecure);
        boolean success = link.run();
        in.close();
        
        if (!success) {
            System.err.println("Program terminated");
            System.exit(1);
        }
    }
}