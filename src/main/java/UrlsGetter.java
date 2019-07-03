import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        Pattern pattern = Pattern.compile("http:\\/\\/expert.atamura.kz\\/storage\\/media\\/[0-9]*\\/pages\\/page-[0-9]*.jpg");
        Matcher matcher = pattern.matcher(html);
        ArrayList<String> matches = new ArrayList<>();
        while(matcher.find())
        {
            matches.add(matcher.group(0));
        }
        return matches;
    }
}
