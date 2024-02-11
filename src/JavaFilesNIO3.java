//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.*;
import java.nio.file.*;
import java.util.*;

public class JavaFilesNIO3 {
    public static void main(String[] args) throws Exception {
        HashSet <Path> set = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Directory to be searched:\t");
        String dir = sc.next();
        System.out.print("Enter the KEYWORD to be searched:\t");
        String key = sc.next();
        Path p = Paths.get(dir);
        Stream <Path> pstr = Files.walk(p);
        pstr.forEach(
                pt -> { try{
                    if(!Files.isDirectory(pt)) {
                        Stream <String > str = Files.lines(pt);
                        str.forEach(s -> {if (s.contains(key)) set.add(pt);} );
                    }
                }
                catch(Exception e) {System.out.println(e); }
                }
        );

        for(Path p11: set) System.out.println(p11);
    }
}

//             Stream<String> st =Files.lines(p);
//             st.forEach(x->{
//                 if(x.contains(check)) System.out.println(p);
//                 else{
//
//                 }
//             });
