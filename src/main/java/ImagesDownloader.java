import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ImagesDownloader {

     private static void createDirectory(String path) {
         if (Files.notExists(Paths.get(path))) { // Создание папки, куда будут сохраняться картинки
             try {
                 Files.createDirectory(Paths.get(path));
             } catch (IOException exception) {
                 exception.printStackTrace();
             }
         }
     }

     public static void saveImagesFromSite(String link, String path) {
         Document document = null;

         try {
             document = Jsoup.connect(link).get();
         } catch (IOException exception) {
             exception.printStackTrace();
         }

         Elements images = Objects.requireNonNull(document).getElementsByTag("img");

         String imageSourcePath;
         String imageName;
         URL imageSourceUrl = null;

         createDirectory(path);

         for (Element image : images) {
             imageSourcePath = image.attr("abs:src");

             try {
                 imageSourceUrl = new URL(imageSourcePath);
             } catch (MalformedURLException e) {
                 e.printStackTrace();
             }

             imageName = imageSourcePath.replaceAll("^.*/", "").replaceAll("\\?.*$", "");

             try {
                 InputStream inputStream = Objects.requireNonNull(imageSourceUrl).openStream();
                 Files.copy(inputStream, Paths.get(path).resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
                 inputStream.close();
             } catch (IOException exception) {
                 exception.printStackTrace();
             }

             System.out.printf("Файл %s скопирован в %s/%s%n", imageSourcePath, path, imageName);
         }
     }
}
