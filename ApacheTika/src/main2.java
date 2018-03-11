import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tika.exception.TikaException;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import javax.activation.MimetypesFileTypeMap;
import java.io.FileInputStream;
import java.io.IOException;

public class main2
{

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private OptimaizeLangDetector langDetector;

    public static void main(String[] args)
    {
        main2 exercise = new main2();
        exercise.run();
    }

    private void run()
    {
        try
        {
            if (!new File("./outputDocuments").exists())
            {
                Files.createDirectory(Paths.get("./outputDocuments"));
            }

            initLangDetector();

            File directory = new File("./documents");
            File[] files = directory.listFiles();
            for (File file : files)
            {
                processFile(file);
            }
        } catch (IOException | SAXException | TikaException e)
        {
            e.printStackTrace();
        }

    }

    private void initLangDetector() throws IOException
    {
        // TODO initialize language detector (langDetector)
        langDetector = new OptimaizeLangDetector();
    }

    private void processFile(File file) throws IOException, SAXException, TikaException
    {
        // TODO: extract content, metadata and language from given file
        // call saveResult method to save the data
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        BodyContentHandler handler = new BodyContentHandler(-1);

        FileInputStream stream = new FileInputStream(file);
        parser.parse(stream, handler, metadata, new ParseContext());

        LanguageResult result = langDetector.loadModels().detect(handler.toString());
        //System.out.println(result.getLanguage());

        String creatorName = metadata.get(TikaCoreProperties.CREATOR);
        Date creationDate = metadata.getDate(TikaCoreProperties.CREATED);
        Date lastModification = metadata.getDate(TikaCoreProperties.MODIFIED);
        //String mimeType = metadata.get(TikaCoreProperties.TYPE);
        String mimeType = new MimetypesFileTypeMap().getContentType(file);

        saveResult(file.getName(), result.getLanguage(), creatorName, creationDate, lastModification, mimeType, handler.toString()); //TODO: fill with proper values
    }

    private void saveResult(String fileName, String language, String creatorName, Date creationDate,
                            Date lastModification, String mimeType, String content)
    {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        int index = fileName.lastIndexOf(".");
        String outName = fileName.substring(0, index) + ".txt";
        try
        {
            PrintWriter printWriter = new PrintWriter("./outputDocuments/" + outName);
            printWriter.write("Name: " + fileName + "\n");
            printWriter.write("Language: " + (language != null ? language : "") + "\n");
            printWriter.write("Creator: " + (creatorName != null ? creatorName : "") + "\n");
            String creationDateStr = creationDate == null ? "" : dateFormat.format(creationDate);
            printWriter.write("Creation date: " + creationDateStr + "\n");
            String lastModificationStr = lastModification == null ? "" : dateFormat.format(lastModification);
            printWriter.write("Last modification: " + lastModificationStr + "\n");
            printWriter.write("MIME type: " + (mimeType != null ? mimeType : "") + "\n");
            printWriter.write("\n");
            printWriter.write(content + "\n");
            printWriter.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

}