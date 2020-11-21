# Images-Downloader
This app download all images from the site

## Usage

1. Place ImagesDownloader.java in your project

### Code example 
```java
import lib.ImagesDownloader;

public class Example {
    public static void main(String[] args) {
    	// This code saves all images from the site "lenta.ru" to the "images" directory in your project
        ImagesDownloader.saveImagesFromSite("https://lenta.ru/", "images");
    }
}
```
