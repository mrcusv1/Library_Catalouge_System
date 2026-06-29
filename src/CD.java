public class CD extends LibraryItem {
    private String isbn;
    private String publisher;
    private String numberOfTracks;
    private String recordLabel;
    private String physicalDescription;

    //constructor of the class
    public CD(String oclcNumber, String title, String genre, String artist,
              int yearPublished, String isbn, String publisher, String numberOfTracks,
              String recordLabel, String physicalDescription) {
        super(oclcNumber, title, genre, artist, yearPublished);
        this.isbn = isbn;
        this.publisher = publisher;
        this.numberOfTracks = numberOfTracks;
        this.recordLabel = recordLabel;
        this.physicalDescription = physicalDescription;
    }


    public CD(String recordBlock) {
        super("", "", "", "", 0); // Initialize with empty values
        parseRecordBlock(recordBlock);
    }

    ///Parse method to get the data from the txt file
    private void parseRecordBlock(String recordBlock) {
        String[] lines = recordBlock.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();


            if (line.equals("OCLC Number:") && i + 1 < lines.length) {
                this.oclcNumber = lines[i + 1].trim();

            } else if (line.equals("Title:") && i + 1 < lines.length) {
                this.title = lines[i + 1].trim();

            } else if (line.equals("Artist:") && i + 1 < lines.length) {
                this.author = lines[i + 1].trim();

            } else if (line.equals("Year of release:") && i + 1 < lines.length) {
                try {
                    this.yearPublished = Integer.parseInt(lines[i + 1].trim());
                } catch (NumberFormatException e) {
                    this.yearPublished = 0; // go back to 0 if the parsing fails
                }

            } else if (line.equals("Publisher:") && i + 1 < lines.length) {
                this.publisher = lines[i + 1].trim();

            } else if (line.equals("Genre:") && i + 1 < lines.length) {
                this.genre = lines[i + 1].trim();

            } else if (line.equals("Physical Description:") && i + 1 < lines.length) {
                this.physicalDescription = lines[i + 1].trim();

            } else if (line.equals("ISBN:") && i + 1 < lines.length) {
                this.isbn = lines[i + 1].trim();

            } else if (line.equals("Number of Tracks:") && i + 1 < lines.length) {
                this.numberOfTracks = lines[i + 1].trim();

            } else if (line.equals("Record Label:") && i + 1 < lines.length) {
                this.recordLabel = lines[i + 1].trim();
            }
        }
    }
////////////////////////////////Assignment 2 Part B////////////////////////////////////////////////////////
///
///
    /**
     * Converts this CD record into a JSON-formatted string.
     * JSON is treated as a plain String
     *
     * Special characters inside field values are escaped using the
     * private escapeJSON() dhelper so the output is valid JSON.
     *
     * @return a JSON string representing this Book record
     */
public String toJSON()
{
    return "{\n" +
            "  \"oclcNumber\": \""   + escJSON(oclcNumber)   + "\",\n" +
            "  \"title\": \"" + escJSON(title) + "\",\n" +
            "  \"genre\": \""   + escJSON(genre)   + "\",\n" +
            "  \"artist\": \""   + escJSON(author)   + "\",\n" +
            "  \"yearOfRelease\": \""   + yearPublished + "\",\n" +
            "  \"publisher\": \""   + escJSON(publisher)   + "\",\n" +
            "  \"isbn\": \""   + escJSON(isbn)   + "\",\n" +
            "  \"physicalDescription\": \""   + escJSON(physicalDescription)   + "\",\n" +
            "}";
}

    //Escapes special characters in a string value so its safe to put inside
    //a JSON string literal.

    /**
     *@param val the raw string value to escape
     *@return the escaped string, or a empty string if the value ends up being null
     */
    private String escJSON(String val)
    {
        if (val == null) return "";
        return val
                .replace("\\", "\\\\") //backslashes have to be escaped first
                .replace("\"", "\\\"") //double quote
                .replace("\n", "\\n") //new line
                .replace("\r", "\\r") // carriage return
                .replace("\t", "\\t"); //tab


    }


    private String escapeJSON(String value)
    {
        if (value == null) return "";
        return value
                .replace("\\", "\\\\")  // backslash must be replaced first
                .replace("\"", "\\\"")  // double quote
                .replace("\n",  "\\n")  // newline
                .replace("\r",  "\\r")  // carriage return
                .replace("\t",  "\\t"); // tab
    }

    // -------------------------------------------------------------------------
    // ASSIGNMENT 2 - PART C: toXML()
    // -------------------------------------------------------------------------

    /**
     * Converts this CD record into an XML-formatted string.
     * XML is treated as a plain String
     * @return an XML string representing this CD record
     */
    public String toXML()
    {
        return "  <cd>\n" +
                "    <oclcNumber>"           + escapeXML(oclcNumber)          + "</oclcNumber>\n" +
                "    <title>"                + escapeXML(title)               + "</title>\n" +
                "    <genre>"                + escapeXML(genre)               + "</genre>\n" +
                "    <artist>"               + escapeXML(author)              + "</artist>\n" +
                "    <yearOfRelease>"        + yearPublished                  + "</yearOfRelease>\n" +
                "    <publisher>"            + escapeXML(publisher)           + "</publisher>\n" +
                "    <isbn>"                 + escapeXML(isbn)                + "</isbn>\n" +
                "    <recordLabel>"          + escapeXML(recordLabel)         + "</recordLabel>\n" +
                "    <numberOfTracks>"       + escapeXML(numberOfTracks)      + "</numberOfTracks>\n" +
                "    <physicalDescription>"  + escapeXML(physicalDescription) + "</physicalDescription>\n" +
                "  </cd>\n";
    }

    /**
     * Escapes characters that have special meaning in XML
     * @param value the raw string value to escape
     * @return the XML-safe escaped string, or an empty string if null
     */
    private String escapeXML(String value)
    {
        if (value == null) return "";
        return value
                .replace("&",  "&amp;")   // ampersand must be replaced first
                .replace("<",  "&lt;")    // less-than
                .replace(">",  "&gt;")    // greater-than
                .replace("\"", "&quot;")  // double quote
                .replace("'",  "&apos;"); // apostrophe
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    //abstract method implementations
    //return identifier for the class
    @Override
    public String getItemType() {
        return "CD";
    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CD RECORD ===\n");
        sb.append("OCLC Number: ").append(oclcNumber).append("\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Artist: ").append(author).append("\n");
        sb.append("Genre: ").append(genre).append("\n");
        sb.append("Year Released: ").append(yearPublished).append("\n");
        sb.append("Publisher: ").append(publisher).append("\n");
        sb.append("ISBN: ").append(isbn).append("\n");
        sb.append("Number of Tracks: ").append(numberOfTracks).append("\n");
        sb.append("Record Label: ").append(recordLabel).append("\n");
        sb.append("Physical Description: ").append(physicalDescription).append("\n");

        return sb.toString();
    }
}