import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cut {
    public static void main(String [] args) throws Exception {

        Scanner in = new Scanner(System.in);
        String command = in.nextLine();

        String [] parsedCommand = command.split(" ");

        boolean is_c = false;
        boolean is_w = false;
        boolean need_o = false;
        boolean need_i = false;
        String type = null;
        File ofile = null;
        File file = null;

        try{

            for (int i = 0;i < parsedCommand.length; i++) {
                switch (parsedCommand[i]){
                    case "-c":
                        is_c = true;
                        break;
                    case "-w":
                        is_w = true;
                        break;
                    case "-o":
                        i += 1;
                        need_o = true;
                        ofile = new File(parsedCommand[i]);
                        break;
                    case "range":
                        i += 1;
                        type = parsedCommand[i];
                        break;

                    default:
                        if (parsedCommand[i+1].equals("range")){
                            need_i = true;
                            file = new File(parsedCommand[i]);
                        }
                                break;
                }
            }
            if (is_c && is_w) throw new Exception("Impossible to use");
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        int N = 0;
        int K = 0;

        if(type.charAt(0) == '-')
        {
            N = 0;
            K = Integer.parseInt(type.replace("-",""));
        }
        else if (type.charAt(type.length() - 1)== '-')
        {
            N = Integer.parseInt(type.split("-")[0]);
            K = Integer.parseInt(type.split("-")[1]);
        }

        ArrayList<String> out = new ArrayList<>();
        String outstring = "";
        String input;
        Scanner sc = null;
        String [] words;

        if (need_i) sc = new Scanner(file);
        else sc = new Scanner(System.in);

        while (true)
        {
            input = sc.nextLine();
            if (input.equals("exit"))
                break;
            if (is_c)
                out.add(input.substring(Math.min(N, input.length()), Math.min(K, input.length())));
            else {
                words = input.split(" ");
                for (int i = Math.min(N, words.length - 1); i <= Math.min(K, words.length -1); i++)
                    outstring += words[i]+" ";
                out.add(outstring);
                outstring = "";
            }
            if (!sc.hasNextLine())
                break;
        }

        if (need_o) {
            final FileWriter writer = new FileWriter(ofile);
            out.forEach(o -> {
                try {
                    writer.write(o+'\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.close();
        }
        else out.forEach(o -> System.out.println(o));
    }
}
