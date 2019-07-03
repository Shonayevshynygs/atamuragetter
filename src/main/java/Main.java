import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
        Scanner sc =new Scanner(System.in);
        System.out.println("Insert the book url");
        System.out.println("In this format http://expert.atamura.kz/ru/books/534");
        String url = sc.next();
        String contents = UrlsGetter.getContents(url);
        ArrayList<String> s = UrlsGetter.getPages(contents);
        s.forEach(s1 -> {
            try {
                System.out.println("Downloading page number "+ImageGetter.counter);
                ImageGetter.getImage(s1, ImageGetter.counter);
                ImageGetter.increment();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }


}
