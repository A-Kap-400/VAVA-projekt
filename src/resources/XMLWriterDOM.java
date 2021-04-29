package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import users.Kniha;

/**
 *
 * @author Akos Kappel
 */
public class XMLWriterDOM {

    private static Document doc;
    public static final String LOG_DIR_NAME = "logs";
    public static final String OUTPUT_DIR_NAME = "output";
    public static final String BORROW_HISTORY = "borrowed_books_history.xml";
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(XMLWriterDOM.class);

    public static void main(String[] args) {
        initMainDirectories();
        saveToXML(new Kniha(123, "zaner123", "nazov123", "autor123", new Date(), "komu123"), BORROW_HISTORY);
    }

    /**
     * Inicializacia potrebnych priecinkov.
     */
    public static void initMainDirectories() {
        File logDirectory = new File(LOG_DIR_NAME);
        if (!logDirectory.exists()) {
            logDirectory.mkdir();
            LOGGER.info("Bol vytvorený nový priečinok s názvom logs.");
        }

        File outputDirectory = new File(OUTPUT_DIR_NAME);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdir();
            LOGGER.info("Bol vytvorený nový priečinok s názvom output.");
        }
    }

    /**
     * Ulozenie novej knihy do XML suboru.
     *
     * @param k kniha na ulozenie
     * @param xmlFileName nazov XML suboru
     */
    public static void saveToXML(Kniha k, String xmlFileName) {
        try {
            String fileName = OUTPUT_DIR_NAME + "\\" + xmlFileName;
            File xmlFile = new File(fileName);

            DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = DBFactory.newDocumentBuilder();

            Element root;

            if (!xmlFile.exists()) {
                doc = db.newDocument();
                root = doc.createElement("books");
                doc.appendChild(root);
            } else {
                doc = db.parse(xmlFile);
                root = doc.getDocumentElement();
            }

            // Pridanie noveho zaznamu
            Element e = createElement(k);
            root.appendChild(e);

            // Ulozenie udajov
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty(OutputKeys.INDENT, "yes");

            System.out.println(fileName);
            System.out.println(doc != null ? doc : "DOC == NULL");
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xmlFile)));
            removeEmptyLines(fileName);

        } catch (ParserConfigurationException pce) {
            LOGGER.error("ParserConfigurationException");
        } catch (IOException ioe) {
            LOGGER.error("IOException");
        } catch (TransformerException te) {
            LOGGER.error("TransformerException");
            te.printStackTrace();
        } catch (SAXException ex) {
            LOGGER.error("SAXException");
        }
    }

    /**
     * Odstranenie prazdnych riadkov zo suboru.
     *
     * @param fileName nazov suboru
     */
    private static void removeEmptyLines(String fileName) {
        Scanner file;
        PrintWriter writer;

        try {

            file = new Scanner(new File(fileName));
            writer = new PrintWriter("temp.xml");

            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.trim().isEmpty()) {
                    writer.write(line);
                    writer.write("\n");
                }
            }

            file.close();
            writer.close();

        } catch (FileNotFoundException ex) {
            LOGGER.warn("Súbor sa nepodarilo nájsť.");
        }

        try {

            file = new Scanner(new File("temp.xml"));
            writer = new PrintWriter(fileName);

            while (file.hasNext()) {
                String line = file.nextLine();
                if (!line.trim().isEmpty()) {
                    writer.write(line);
                    writer.write("\n");
                }
            }

            file.close();
            writer.close();
            Files.deleteIfExists(Path.of("temp.xml"));

        } catch (FileNotFoundException ex) {
            LOGGER.warn("Súbor sa nepodarilo prekopírovať.");
        } catch (IOException ex) {
            LOGGER.warn("Súbor sa nepodarilo vymazať.");
        }
    }

    /**
     * Metoda na exportovanie zoznamu knih do XML suboru.
     *
     * @param exportedBooks zoznam knih
     * @param xmlFileName nazov XML suboru
     */
    public static void exportToXML(ArrayList<Kniha> exportedBooks, String xmlFileName) {
        try {
            File xmlFile = new File(OUTPUT_DIR_NAME + "\\" + xmlFileName);
            DocumentBuilderFactory DBFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = DBFactory.newDocumentBuilder();
            doc = db.newDocument();

            Element root = doc.createElement("library");
            Element meta = doc.createElement("meta");
            Element books = doc.createElement("books");

            // Metadata
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Element createdAtElement = doc.createElement("created_at");
            createdAtElement.appendChild(doc.createTextNode(sdf.format(new Date())));
            meta.appendChild(createdAtElement);

            Element bookCountElement = doc.createElement("book_count");
            bookCountElement.appendChild(doc.createTextNode(String.valueOf(exportedBooks.size())));
            meta.appendChild(bookCountElement);

            // Books
            for (Kniha k : exportedBooks) {
                Element e = createElement(k);
                books.appendChild(e);
            }

            // Root
            root.appendChild(meta);
            root.appendChild(books);
            doc.appendChild(root);

            // Ulozenie udajov
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.xml");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(xmlFile)));

        } catch (ParserConfigurationException pce) {
            LOGGER.error("ParserConfigurationException");
        } catch (IOException ioe) {
            LOGGER.error("IOException");
        } catch (TransformerException te) {
            LOGGER.error("TransformerException");
        }
    }

    /**
     * Metoda na konvertovanie knihy na XML element.
     *
     * @param k kniha na konvertovanie
     * @return kniha v XML tvare
     */
    private static Element createElement(Kniha k) {
        Element e = doc.createElement("book");
        e.setAttribute("id", String.valueOf(k.getIdKniha()));

        Element titleElement = doc.createElement("title");
        titleElement.appendChild(doc.createTextNode(k.getNazov()));
        e.appendChild(titleElement);

        Element genreElement = doc.createElement("genre");
        genreElement.appendChild(doc.createTextNode(k.getZaner()));
        e.appendChild(genreElement);

        Element authorElement = doc.createElement("author");
        authorElement.appendChild(doc.createTextNode(k.getAutor()));
        e.appendChild(authorElement);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        Element returnDateElement = doc.createElement("return_date");
        returnDateElement.appendChild(doc.createTextNode(k.getPozicaneDo() != null ? sdf.format(k.getPozicaneDo()) : "-"));
        e.appendChild(returnDateElement);

        Element borrowedToElement = doc.createElement("borrowed_to");
        borrowedToElement.appendChild(doc.createTextNode(k.getPozicaneKomu() != null ? k.getPozicaneKomu().getMeno() : "-"));
        e.appendChild(borrowedToElement);

        return e;
    }

}
