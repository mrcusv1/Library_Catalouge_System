public class Book extends LibraryItem
{
    private String isbn;
    private String publisher;
    private String summary;
    private String physicalDescription;

    /*
    Constructor for Book with the individual parameters
     */
    public Book(String oclcNumber, String title, String genre, String author, int yearPublished, String isbn, String publisher, String summary, String physicalDescription)
    {
        super(oclcNumber, title, genre, author, yearPublished);
        this.isbn = isbn;
        this.publisher = publisher;
        this.summary = summary;
        this.physicalDescription = physicalDescription;
    }

    /**
     *Constructor that parses a complete book record block from the txt file
     *@param recordBlock contains the complete book record
     */
    public Book(String recordBlock)
    {
        super("", "", "","", 0); //initializing with empty values
        parseRecordBlock(recordBlock);
    }




//Parsing lets the LibraryManager read the txt file and get the info
    private void parseRecordBlock(String recordBlock)
    {
        String [] lines = recordBlock.split("\n");

        for(int i = 0; i < lines.length; i++)
        {
            String line = lines[i].trim();

            //reads and lets the code understand the txt file
            //This is similar to the other classes
            if(line.equals("OCLC Number:") && i+1 < lines.length){
                this.oclcNumber = lines[i+1].trim();
            }
            else if(line.equals("Title:") && i+1 < lines.length){
                this.title = lines[i+1].trim();
            }
            else if(line.equals("Genre:") && i+1 < lines.length){
                this.genre = lines[i+1].trim();
            }
            else if(line.equals("Authors:") && i+1 < lines.length){
                this.author= lines[i+1].trim();
            }
            else if(line.equals("Summary:")&& i+1 < lines.length){
                this.summary = lines[i+1].trim();
            }
            else if(line.equals("Year of publication:")&& i+1 < lines.length){
                try {
                    this.yearPublished = Integer.parseInt(lines[i + 1].trim());
                }
                catch(NumberFormatException e)
                {
                    this.yearPublished = 0; //if parsing fails set to default
                }
            }
        }

    }

    //Getter methods
    public String getIsbn()
    {
        return isbn;
    }
    public String getPublisher()
    {
        return publisher;
    }
    public String getSummary()
    {
        return summary;
    }

    public String getPhysicalDescription()
    {
        return physicalDescription;
    }


    @Override
    public String getItemType()
    {
        return "Book";
    }

    // ASSIGNMENT 2 - PART B: toJSON()

    /**
     * Converts this Book record into a JSON-formatted string.
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
                "  \"oclcNumber\": \""          + escapeJSON(oclcNumber)          + "\",\n" +
                "  \"title\": \""               + escapeJSON(title)               + "\",\n" +
                "  \"genre\": \""               + escapeJSON(genre)               + "\",\n" +
                "  \"author\": \""              + escapeJSON(author)              + "\",\n" +
                "  \"yearPublished\": "         + yearPublished                   + ",\n"  +
                "  \"publisher\": \""           + escapeJSON(publisher)           + "\",\n" +
                "  \"isbn\": \""                + escapeJSON(isbn)                + "\",\n" +
                "  \"summary\": \""             + escapeJSON(summary)             + "\",\n" +
                "  \"physicalDescription\": \"" + escapeJSON(physicalDescription) + "\"\n" +
                "}";
    }

    /**
     * Escapes special characters in a string value so it is safe to embed
     * inside a JSON string literal.
     * Handles: backslashes, double quotes, newlines, carriage returns, tabs.
     *
     * @param value the raw string value to escape
     * @return the escaped string, or an empty string if the value is null
     */
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

    //toString Method

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("---BOOK RECORD---\n");
        sb.append("OCLC Number: ").append(oclcNumber).append("\n");
        sb.append("Title: ").append(title).append("\n");
        sb.append("Author: ").append(author).append("\n");
        sb.append("Genre: ").append(genre).append("\n");
        sb.append("Year Published: ").append(yearPublished).append("\n");
        sb.append("Publisher: ").append(publisher).append("\n");
        sb.append("ISBN: ").append(isbn).append("\n");
        sb.append("Physical Description: ").append(physicalDescription).append("\n");

        return sb.toString();
    }
}
