import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlsGetter {

    public static String getContents(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        String content = null;
        int responseCode = con.getResponseCode();
        Scanner scanner = new Scanner(con.getInputStream());
        scanner.useDelimiter("\\Z");
        content = scanner.next();
        scanner.close();

        return content.toString();
    }

    public static ArrayList<String> getPages(String html){
        Pattern pattern = Pattern.compile("\\/watermark.php\\?image=\\/cms\\/uploads\\/book_out_[0-9]*\\/file_[0-9]*_[0-9]*-[0-9]*.jpg");
        Matcher matcher = pattern.matcher(html);

        ArrayList<String> matches = new ArrayList<>();
        while(matcher.find())
        {
            matches.add(matcher.group(0));
        }
        return matches;
    }

    public static void writeToPdf(ArrayList<String> pages, String name){
        int size = pages.size();
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(name+".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        doc.open();
        pages.forEach(page ->{
            double scale = 0.75;
            Image temp = ImageGetter.getSingleImage("http://old.mektep.kz"+page);
            BufferedImage bi = toBufferedImage(temp);
            BufferedImage bufferedImage = Scalr.resize(bi, Scalr.Method.BALANCED, (int)Math.round(bi.getWidth(null)*0.9), (int)Math.round(bi.getHeight(null)*0.9));
            try {
                ImageGetter.counter++;
                System.out.println("Downloading and adding to pdf the page number "+ImageGetter.counter+"/"+size);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                doc.add(com.itextpdf.text.Image.getInstance(baos.toByteArray()));

            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ImageGetter.counter=0;
        doc.close();
    }


    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }
}
