import java.io.*;
import java.nio.file.*;
import java.util.*;

//This class manages the data
public class LibraryManagerLogic {
    //Using hashmaps and arrayList this provides the data for the code
    private HashMap<String, Book> bookCatalogue = new HashMap<>();
    private HashMap<String, DVD> dvdCatalogue = new HashMap<>();
    private HashMap<String, CD> cdCatalogue = new HashMap<>() ;

    //Arraylist to help in the descending order of the items
    private ArrayList<LoanRecord> loanRecords = new ArrayList<>();

    //This method resets the output
    public void reset()
    {
        bookCatalogue.clear();
        dvdCatalogue.clear();
        cdCatalogue.clear();
        loanRecords.clear();
    }
    //This is the same for the 3 following methods, this uses readAllBytes to read the txt file
    // Using readAllBytes this helps the children class (Book,CD, and DVD) use their parse methods accordingly
    //readAllBytes use the parse method to read the .txt file
    public void loadBooks(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] records = content.split("-------------------------------------------------------------------");

        for (String record : records) {
            if (record.trim().isEmpty()) continue;
            try {
                Book book = new Book(record);
                if (book.getOclcNumber() != null && !book.getOclcNumber().isEmpty()) {
                    bookCatalogue.put(book.getOclcNumber(), book);
                }
            } catch (Exception e) {
                System.err.println("Error parsing book record: " + e.getMessage());
            }
        }}


    public void loadDVDs(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] records = content.split("-------------------------------------------------------------------");

        for (String record : records) {
            if (record.trim().isEmpty()) continue;
            try {
                DVD dvd = new DVD(record);
                if (dvd.getOclcNumber() != null && !dvd.getOclcNumber().isEmpty()) {
                    dvdCatalogue.put(dvd.getOclcNumber(), dvd);
                }
            } catch (Exception e) {
                System.err.println("Error parsing DVD record: " + e.getMessage());
            }
        }
    }


    public void loadCDs(String filePath) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] records = content.split("-------------------------------------------------------------------");

        for (String record : records) {
            if (record.trim().isEmpty()) continue;
            try {
                CD cd = new CD(record);
                if (cd.getOclcNumber() != null && !cd.getOclcNumber().isEmpty()) {
                    cdCatalogue.put(cd.getOclcNumber(), cd);
                }
            } catch (Exception e) {
                System.err.println("Error parsing CD record: " + e.getMessage());
            }
        }
    }

    //Debug for DVD loan
    
    //public void debugDVDMatch()
   // {
    //   System.out.println("DVD catalogue size: " + dvdCatalogue.size());
    //    System.out.println("Loan records size: " + loanRecords.size());
     //   for (LoanRecord lr : loanRecords) {
    //       if (dvdCatalogue.containsKey(lr.getOclcNumber()))
    //           System.out.println("DVD match: " + lr.getOclcNumber());
    //    }
    //    System.out.println("Checked!");
  //  }

    public void loadLoanRecords(String filePath) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(filePath)));
        String[] records = content.split("-------------------------------------------------------------------");

        for (String record : records) {
            if (record.trim().isEmpty()) continue;
            try {
                LoanRecord lr = new LoanRecord(record);
                if (lr.getOclcNumber() != null && !lr.getOclcNumber().isEmpty())
                    loanRecords.add(lr);
            } catch (Exception e) {
                System.err.println("Error parsing loan record: " + e.getMessage());
            }
        }
    }

    //this gets the count of each file
    public int getBookCount()  {
        return bookCatalogue.size();
    }
    public int getDVDCount()   {
        return dvdCatalogue.size();
    }
    public int getCDCount()    {
        return cdCatalogue.size();
    }
    public int getLoanCount()  {
        return loanRecords.size();
    }
    public int getTotalCount() {
        return bookCatalogue.size() +
                dvdCatalogue.size() +
                cdCatalogue.size();
    }

    public boolean isCDCatalogueEmpty() { return cdCatalogue.isEmpty(); }

    // Gets the Genres

    public ArrayList<String> getBookGenres() {
        HashSet<String> genres = new HashSet<>();
        for (Book book : bookCatalogue.values()) {
            genres.add(book.getGenre());
        }
        ArrayList<String> sorted = new ArrayList<>(genres);
        Collections.sort(sorted);
        return sorted;
    }


     // Returns a sorted list of unique DVD genres.

    public ArrayList<String> getDVDGenres() {
        HashSet<String> genres = new HashSet<>();
        for (DVD dvd : dvdCatalogue.values()) {
            genres.add(dvd.getGenre());
        }
        ArrayList<String> sorted = new ArrayList<>(genres);
        Collections.sort(sorted);
        return sorted;
    }


     //Returns a sorted list of unique CD genres.

    public ArrayList<String> getCDGenres() {
        HashSet<String> genres = new HashSet<>();
        for (CD cd : cdCatalogue.values()) {
            genres.add(cd.getGenre());
        }
        ArrayList<String> sorted = new ArrayList<>(genres);
        Collections.sort(sorted);
        return sorted;
    }

     // Returns a map of genre for books.

    public HashMap<String, Integer> getBookGenreCounts() {
        HashMap<String, Integer> counts = new HashMap<>();
        for (Book book : bookCatalogue.values()) {
            String genre = book.getGenre();
            counts.put(genre, counts.getOrDefault(genre, 0) + 1);
        }
        return counts;
    }

     //Returns a map of genre for DVD

    public HashMap<String, Integer> getDVDGenreCounts() {
        HashMap<String, Integer> counts = new HashMap<>();
        for (DVD dvd : dvdCatalogue.values()) {
            String genre = dvd.getGenre();
            counts.put(genre, counts.getOrDefault(genre, 0) + 1);
        }
        return counts;
    }

     //Returns a map of genre for CD
    public HashMap<String, Integer> getCDGenreCounts() {
        HashMap<String, Integer> counts = new HashMap<>();
        for (CD cd : cdCatalogue.values()) {
            String genre = cd.getGenre();
            counts.put(genre, counts.getOrDefault(genre, 0) + 1);
        }
        return counts;
    }

    // search methosd
    //using hashmaps i am able to return the oclc number

    public String searchByOCLC(String oclc) {
        if (bookCatalogue.containsKey(oclc)) return bookCatalogue.get(oclc).toString();
        if (dvdCatalogue.containsKey(oclc)) return dvdCatalogue.get(oclc).toString();
        if (cdCatalogue.containsKey(oclc))  return cdCatalogue.get(oclc).toString();
        return null;
    }


    public ArrayList<String> searchBooksByGenre(String genre) {
        ArrayList<String> results = new ArrayList<>();
        for (Book book : bookCatalogue.values()) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                results.add(book.getTitle());
            }
        }
        return results;
    }

    //Returns a list of DVD titles matching the  genre that is displayed in the txt file

    public ArrayList<String> searchDVDsByGenre(String genre) {
        ArrayList<String> results = new ArrayList<>();
        for (DVD dvd : dvdCatalogue.values()) {
            if (dvd.getGenre().equalsIgnoreCase(genre)) {
                results.add(dvd.getTitle());
            }
        }
        return results;
    }

    //Returns a list of CD titles matching the genre needed

    public ArrayList<String> searchCDsByGenre(String genre) {
        ArrayList<String> results = new ArrayList<>();
        for (CD cd : cdCatalogue.values()) {
            if (cd.getGenre().equalsIgnoreCase(genre)) {
                results.add(cd.getTitle());
            }
        }
        return results;
    }




    // Shared helper - sums the loans totals per OCLC, matches against the given
    // catalogue, then returns the top 10 sorted highest to lowest
    private <T extends LibraryItem> ArrayList<String> getTopTen(HashMap<String, T> catalogue) {

        HashMap<String, Integer> totals = new HashMap<>();
        for (LoanRecord lr : loanRecords) {
            if (catalogue.containsKey(lr.getOclcNumber())) {
                totals.put(lr.getOclcNumber(),
                        totals.getOrDefault(lr.getOclcNumber(), 0) + lr.getOnLoan());
            }
        }

        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(totals.entrySet());
        entries.sort((a, b) -> b.getValue() - a.getValue());


        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < Math.min(10, entries.size()); i++) {
            String oclc  = entries.get(i).getKey();
            int    total = entries.get(i).getValue();
            results.add(catalogue.get(oclc).getTitle() + " — Total on loan: " + total + " Weeks");
        }
        return results;
    }


     // Returns the top 10 loaned books
    public ArrayList<String> getTopTenBooks() {
        return getTopTen(bookCatalogue);
    }


     // Returns the top 10 loaned DVDs
    public ArrayList<String> getTopTenDVDs() {
        return getTopTen(dvdCatalogue);
    }


     //Gets the top 10 most loaned CDs

    public ArrayList<String> getTopTenCDs() {
        return getTopTen(cdCatalogue);
    }



    public String generateCatalogueReport() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== LIBRARY CATALOGUE REPORT ===\n\n");
        sb.append("Total unique library items: ").append(getTotalCount()).append("\n\n");
        sb.append("Books: ").append(getBookCount()).append("\n");
        sb.append("DVDs:  ").append(getDVDCount()).append("\n");
        sb.append("CDs:   ").append(getCDCount()).append("\n\n");

        HashMap<String, Integer> bookGenres = getBookGenreCounts();
        HashMap<String, Integer> dvdGenres  = getDVDGenreCounts();
        HashMap<String, Integer> cdGenres   = getCDGenreCounts();

        sb.append("Unique genres in book catalogue: ").append(bookGenres.size()).append("\n");
        sb.append("Unique genres in DVD catalogue:  ").append(dvdGenres.size()).append("\n");
        sb.append("Unique genres in CD catalogue:   ").append(cdGenres.size()).append("\n\n");

        sb.append("--- Items per Genre (Books) ---\n");
        for (Map.Entry<String, Integer> entry : bookGenres.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("\n--- Items per Genre (DVDs) ---\n");
        for (Map.Entry<String, Integer> entry : dvdGenres.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        sb.append("\n--- Items per Genre (CDs) ---\n");
            for (Map.Entry<String, Integer> entry : cdGenres.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
        return sb.toString();
    }

    public void saveReportToFile(String filePath) throws IOException {
        BufferedWriter save = new BufferedWriter(new FileWriter(filePath));
        save.write(generateCatalogueReport());
    }
    //------------------------------------
    //      ASSIGNMENT 2 Part B JSON conversion
    //-------------------------------------

    public String toJSONBooks()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(" \"books\": [\n");

        //Iterate over all the books and append each one
        int count = 0;
        int total = bookCatalogue.size();
        for (Book book : bookCatalogue.values()) {
            sb.append(book.toJSON());
            count++;

            if (count < total) sb.append(",");
        }
        sb.append("]\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Converts the entire DVD catalouge into a JSON array stirng
     * Each DVD's toJSON method is called and the results are made in a JSON array
     * @return a JSON string representing all DVDs in the catalouge
     */
    public String toJSONDVDs()
    {

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(" \"dvds\": [\n");

        int count = 0;
        int total = dvdCatalogue.size();
        for (DVD dvd: dvdCatalogue.values()) {
            sb.append(dvd.toJSON());
            count++;
            if (count < total) sb.append(", ");
            sb.append("\n");
        }
        sb.append("]\n");  sb.append("}");
        return sb.toString();
    }

    public String toJSONCDs()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append(" \"cds\": [\n");

        int count = 0;
        int total = cdCatalogue.size();
        for (CD cd: cdCatalogue.values()) {
            sb.append(cd.toJSON());
            count++;
            if (count < total) sb.append(", ");
            sb.append("\n");
        }
        sb.append("]\n");  sb.append("}");
        return sb.toString();
    }

    public void saveJSONToFile(String jsonContent, String filePath) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(jsonContent);
        writer.close();
    }
    /// ___________________________________________
    ///  Assignemtn 2 Part C XML Generation by Genre
    /// ___________________________________________

    //This method generates an XML document that classifies the entire library collection by genre
    public String generateXMLByGenre()
    {
        StringBuilder sb = new StringBuilder();

        // XML declaration
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<libraryCollection>\n\n");

        // --- Book Collection ---
        sb.append("  <bookCollection>\n");
        ArrayList<String> bookGenres = getBookGenres(); // sorted alphabetically

        for (String genre : bookGenres)
        {
            // Escape the genre name for use as an XML attribute value
            sb.append("    <genre name=\"").append(escapeXML(genre)).append("\">\n");

            // Add each book in this genre
            for (Book book : bookCatalogue.values())
            {
                if (book.getGenre().equals(genre))
                {
                    sb.append("      <book>\n");
                    sb.append("        <title>").append(escapeXML(book.getTitle())).append("</title>\n");
                    sb.append("        <oclcNumber>").append(escapeXML(book.getOclcNumber())).append("</oclcNumber>\n");
                    sb.append("      </book>\n");
                }
            }
            sb.append("    </genre>\n");
        }
        sb.append("  </bookCollection>\n\n");

        // --- DVD Collection ---
        sb.append("  <dvdCollection>\n");
        ArrayList<String> dvdGenres = getDVDGenres(); // sorted alphabetically

        for (String genre : dvdGenres)
        {
            sb.append("    <genre name=\"").append(escapeXML(genre)).append("\">\n");

            for (DVD dvd : dvdCatalogue.values())
            {
                if (dvd.getGenre().equals(genre))
                {
                    sb.append("      <dvd>\n");
                    sb.append("        <title>").append(escapeXML(dvd.getTitle())).append("</title>\n");
                    sb.append("        <oclcNumber>").append(escapeXML(dvd.getOclcNumber())).append("</oclcNumber>\n");
                    sb.append("      </dvd>\n");
                }
            }
            sb.append("    </genre>\n");
        }
        sb.append("  </dvdCollection>\n\n");

        // --- CD Collection ---
        sb.append("  <cdCollection>\n");
        ArrayList<String> cdGenres = getCDGenres(); // sorted alphabetically

        for (String genre : cdGenres)
        {
            sb.append("    <genre name=\"").append(escapeXML(genre)).append("\">\n");

            for (CD cd : cdCatalogue.values())
            {
                if (cd.getGenre().equals(genre))
                {
                    sb.append("      <cd>\n");
                    sb.append("        <title>").append(escapeXML(cd.getTitle())).append("</title>\n");
                    sb.append("        <oclcNumber>").append(escapeXML(cd.getOclcNumber())).append("</oclcNumber>\n");
                    sb.append("      </cd>\n");
                }
            }
            sb.append("    </genre>\n");
        }
        sb.append("  </cdCollection>\n\n");

        sb.append("</libraryCollection>");
        return sb.toString();
    }

    //Saves an XML string to a file at the given file path

    /**
     *
     * @param xmlContent XML string to write
     * @param filePath destination file path
     * @throws IOException if the file cannot be written
     */
    public void saveXMLToFile(String xmlContent, String filePath) throws IOException
    {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(xmlContent);
        writer.close();
    }

    /**
     * Escapes character that have special meaning in XML to prevent them
     * from breaking the generation of XML documents
     * @param value raw String to escape
     * @return returns the XML version of the String or return an empty string if null
     */
    private String escapeXML(String value)
    {
        if (value == null) return "";
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}