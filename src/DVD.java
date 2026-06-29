
public class DVD extends LibraryItem{
    private String isbn;
    private String publisher;
    private String cast;
    private String credits;
    private String plot;
    private String language;
    private String physicalDescription;

    /*
    Constructor for DVD with the parameters
     */
    public DVD(String oclcNumber, String title, String genre,
               String director, int yearPublished, String isbn,
               String publisher, String cast, String credits,
               String plot, String language, String physicalDescription)
    {
        super(oclcNumber, title, genre, director, yearPublished);
        this.isbn = isbn;
        this.publisher = publisher;
        this.credits = credits;
        this.plot = plot;
        this.language = language;
        this.physicalDescription = physicalDescription;
    }

    public DVD(String recordBlock)
    {
        super("", "", "","", 0); //initializing with empty values
        parseRecordBlock(recordBlock);
    }
    //parsing method this reads the txt file

    private void parseRecordBlock(String recordBlock)
    {
        String [] lines = recordBlock.split("\n");

        for(int i = 0; i < lines.length; i++)
        {
            String line = lines[i].trim();

            if (line.equals("OCLC Number:") && i + 1 < lines.length)
            {
                this.oclcNumber = lines[i+ 1].trim();
            }
            else if (line.equals("Title:") && i + 1 < lines.length)
            {
                this.title = lines[i+1];
            }
            else if (line.equals("Cast:") && i + 1 < lines.length)
            {
                this.cast = lines[i+1];
            }
            else if (line.equals("Credits:") && i + 1 < lines.length) {
                // Credits contains the directors info
                String creditsLine = lines[i + 1].trim();
                this.credits = creditsLine;
                // extracts the director/s
                this.author = extractDirector(creditsLine);

            } else if (line.equals("Plot:") && i + 1 < lines.length) {
                this.plot = lines[i + 1].trim();

            } else if (line.equals("Year of release:") && i + 1 < lines.length) {
                try {
                    this.yearPublished = Integer.parseInt(lines[i + 1].trim());
                } catch (NumberFormatException e) {
                    this.yearPublished = 0; // Default if parsing fails
                }

            } else if (line.equals("Language:") && i + 1 < lines.length) {
                this.language = lines[i + 1].trim();

            } else if (line.equals("Publisher:") && i + 1 < lines.length) {
                this.publisher = lines[i + 1].trim();

            } else if (line.equals("Genre:") && i + 1 < lines.length) {
                this.genre = lines[i + 1].trim();

            } else if (line.equals("Physical Description:") && i + 1 < lines.length) {
                this.physicalDescription = lines[i + 1].trim();

            } else if (line.equals("ISBN:") && i + 1 < lines.length) {
                this.isbn = lines[i + 1].trim();
            }
        }
    }

    private String extractDirector(String creditsLine){
        if (creditsLine != null && creditsLine.contains("Director,"))
        {
            int directorStart = creditsLine.indexOf("Director,") + 9; //Skips "Director,"
            int semicolonPos = creditsLine.indexOf(";", directorStart);
            if (semicolonPos != -1) {
                return creditsLine.substring(directorStart, semicolonPos).trim();
            }
            else {
                return creditsLine.substring(directorStart).trim();
            }
            }

        return "Unknown Director";
        }

    ////////////////////////////////Assignment 2 Part B////////////////////////////////////////////////////////


    /**
     *
     * Converts the DVD record into JSON formatted String
     * JSON is treated as a plain String/Text
     *  Special characters inside field values (quotes, backslashes, newlines)
     *  are escaped using the private escJSON helper so the input is valid
     *
     *
     * @return a JSON string representing the DVD record
     */

    public String toJSON()
    {
        return "{\n" +
                "  \"oclcNumber\": \""   + escJSON(oclcNumber)   + "\",\n" +
                "  \"title\": \"" + escJSON(title) + "\",\n" +
                "  \"genre\": \""   + escJSON(genre)   + "\",\n" +
                "  \"director\": \""   + escJSON(author)   + "\",\n" +
                "  \"yearOfRelease\": \""   + yearPublished + "\",\n" +
                "  \"publisher\": \""   + escJSON(publisher)   + "\",\n" +
                "  \"isbn\": \""   + escJSON(isbn)   + "\",\n" +
                "  \"language\": \""   + escJSON(language)   + "\",\n" +
                "  \"cast\": \""   + escJSON(cast)   + "\",\n" +
                "  \"credits\": \""   + escJSON(credits)   + "\",\n" +
                "  \"plot\": \""   + escJSON(plot)   + "\",\n" +
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
    // -------------------------------------------------------------------------
    // ASSIGNMENT 2 - PART C: toXML()
    // -------------------------------------------------------------------------

    /**
     * Converts this DVD record into an XML-formatted string.
     * XML is treated as a plain String as per the assignment note for Part C.
     * @return an XML string representing this DVD record
     */
    public String toXML()
    {
        return "  <dvd>\n" +
                "    <oclcNumber>"          + escapeXML(oclcNumber)          + "</oclcNumber>\n" +
                "    <title>"               + escapeXML(title)               + "</title>\n" +
                "    <genre>"               + escapeXML(genre)               + "</genre>\n" +
                "    <director>"            + escapeXML(author)              + "</director>\n" +
                "    <yearOfRelease>"       + yearPublished                  + "</yearOfRelease>\n" +
                "    <publisher>"           + escapeXML(publisher)           + "</publisher>\n" +
                "    <isbn>"                + escapeXML(isbn)                + "</isbn>\n" +
                "    <language>"            + escapeXML(language)            + "</language>\n" +
                "    <cast>"                + escapeXML(cast)                + "</cast>\n" +
                "    <credits>"             + escapeXML(credits)             + "</credits>\n" +
                "    <plot>"                + escapeXML(plot)                + "</plot>\n" +
                "    <physicalDescription>" + escapeXML(physicalDescription) + "</physicalDescription>\n" +
                "  </dvd>\n";
    }

    /**
     * Escapes characters that have special meaning in XML so they
     * don't break the structure of the generated XML document.
     *
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
    ///
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setPhysicalDescription(String physicalDescription) {
        this.physicalDescription = physicalDescription;
    }

    //Getters

    public String getIsbn()
    {
        return isbn;
    }
    public String getPublisher()
    {
        return publisher;
    }
    public String getCredits()
    {
        return credits;
    }
    public String getPlot()
    {
        return plot;
    }
    public String getLanguage()
    {
        return language;
    }

    public String getPhysicalDescription()
    {
        return physicalDescription;
    }

    public String getDirector()
    {
    return author; //the author holds the director
    }



//Same logic as book
    //Identifier for the class
    @Override
    public String getItemType()
    {
        return "DVD";
    }

    //toString method
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("---DVD RECORD---\n");
    sb.append("OCLC Number: ").append(oclcNumber).append("\n");
    sb.append("Title: ").append(title).append("\n");
    sb.append("Director: ").append(author).append("\n");
    sb.append("Genre: ").append(genre).append("\n");
    sb.append("Year Released: ").append(yearPublished).append("\n");
    sb.append("Publisher: ").append(publisher).append("\n");
    sb.append("ISBN: ").append(isbn).append("\n");
    sb.append("Language: ").append(language).append("\n");
    sb.append("Physical Description: ").append(physicalDescription).append("\n");
    sb.append("\nCast: ").append(cast).append("\n");
    sb.append("\nPlot: ").append(plot).append("\n");
        return sb.toString();
    }

}
