public abstract class LibraryItem

{
    //Common attributes for all the library items
    protected String oclcNumber;
    protected String title;
    protected String genre;
    protected String author;
    protected int yearPublished;

    /**
     * Constructs for Library Item
     * @param oclcNumber the identifier
     * @param title
     * @param genre
     * @param author
     * @param yearPublished
     */
    public LibraryItem(String oclcNumber, String title, String genre, String author, int yearPublished)
    {
        this.oclcNumber = oclcNumber;
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.yearPublished = yearPublished;
    }

    //Getters

    public String getOclcNumber() {
        return oclcNumber;
        }

        public String getTitle()
        {
            return title;
        }
        public String getGenre()
        {
            return genre;
        }
        public String getAuthor()
        {
            return author;
        }
        public int getYearPublished()
        {
            return yearPublished;
        }

        //This method gets the item type
    //meaning if the item is a Book,CD,DVD call this method and override and initialize with the name of the Class
        public abstract String getItemType();

        //Abstract method for string representation
    @Override
    public abstract String toString();
    }