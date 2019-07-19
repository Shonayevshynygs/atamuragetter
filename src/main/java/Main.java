import com.itextpdf.text.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, DocumentException {
        Scanner sc =new Scanner(System.in);
        ArrayList<BookDetails> books = new ArrayList<>();
        String url = "";
        while (!url.equals("0")){
            System.out.println("Insert the book url");
            url = sc.next();
            System.out.println("Insert name of pdf");
            String name = sc.next();
            if (!url.equals("0")){
                books.add(new BookDetails(url, name));
            }

        }

        books.forEach(book ->{
            String contents = null;
            try {
                contents = UrlsGetter.getContents(book.getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> pages = UrlsGetter.getPages(contents);
            UrlsGetter.writeToPdf(pages, book.getName());
        });


        //TODO multithreading download
    }


}
