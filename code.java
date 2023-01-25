import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
       Scanner in =new Scanner(System.in);
       
        int ch=0;
        while(true)
        {
            System.out.println("Enter 1 : Encrypt 2:Decrypt 0:exit");
            ch=in.nextInt();
            switch (ch)
            {
                case 1:File_encrypter();
                    break;
                case 2:File_decrypter();
                    break;
                case 0:System.exit(0);
                default:System.out.println("Invalid choice");

            }
        }
        






        
    }
    

    
    private static void File_decrypter() throws Exception {
        JFileChooser filechoose=new JFileChooser();
        System.out.println("Select file for decryption");
        int ch=filechoose.showOpenDialog(null);
        if(ch==JFileChooser.CANCEL_OPTION )
        {   
            System.out.println("Operation canceled by the user" );
            return;
        }
        String openPath=filechoose.getSelectedFile().getAbsolutePath();
        byte [] _byte=Files.readAllBytes(Paths.get(openPath));
        Scanner in=new Scanner(System.in);
        System.out.println("Enter the key length");
        int len=in.nextInt();
        System.out.println("Enter the key");
        int []key=Key_gen(len);
        for(int i=0;i<len;i++)  
            key[i]=in.nextInt();
        System.out.println("Decrypting....");
        int i=0;
        int no_of_ch=_byte.length;
        int k=0;
        while(k<no_of_ch)
        {
            _byte[k]^=key[i];
            k++;
            i=(i+1)%len;
        }
        String Filecontents=new String(_byte);
        System.out.println("Decrptyed file\n"+Filecontents);



    }



    private static void File_encrypter() throws Exception {
        System.out.println("Select file for encryption");
        JFileChooser filechoose=new JFileChooser();
        int ch=filechoose.showOpenDialog(null);
        if(ch==JFileChooser.CANCEL_OPTION )
        {   
            System.out.println("Operatin cancel by the user" );
            return;
        }
        String openPath=filechoose.getSelectedFile().getAbsolutePath();
        byte [] _byte=Files.readAllBytes(Paths.get(openPath));
        Scanner in=new Scanner(System.in);
        System.out.println("Enter key length:");
        //add constraints for min length
        int len=in.nextInt();
        int []key=Key_gen(len);
        System.out.println("Decrytion key :"+Arrays.toString(key));
        int i=0;
        int no_of_ch=_byte.length;
        int k=0;
        while(k<no_of_ch)
        {
            _byte[k]^=key[i];
            k++;
            i=(i+1)%len;
        }
        JFileChooser save=new JFileChooser();
        save.setDialogTitle("Save encrypted file");
         ch=save.showDialog(null, "save");
        if(ch==JFileChooser.CANCEL_OPTION)
            return;
        String save_path=save.getSelectedFile().getAbsolutePath();
        Path Psave_path=Paths.get(save_path);
       // System.out.println(save_path);
        Files.write(Psave_path, _byte);
       // fileContent=new String(_byte);
        System.out.println("File saved at "+save_path);
        
    }


    static int [] Key_gen(int len)
    {
        Random rnd=new Random();
        int range=128;//assume 1 byte
        int _byte[]=new int[len];
        for(int i=0;i<len;i++)
        {
            _byte[i]=rnd.nextInt(range);
        }
       

        return _byte;
    }
}
