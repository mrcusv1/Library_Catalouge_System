public class LoanRecord {
    private String oclcNumber;
    private int onLoan;

    //This loads the data in the loan file
    public LoanRecord(String record) {
        String[] lines = record.trim().split("\\r\\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.startsWith("OCLC Number:")) {
                // value may be on the same line or in the next
                String inline = line.replace("OCLC Number:", "").trim();
                oclcNumber = inline.isEmpty() ? lines[i + 1].trim() : inline;
            } else if (line.startsWith("ON LOAN:")) {
                String inline = line.replace("ON LOAN:", "").trim();
                onLoan = Integer.parseInt(inline.isEmpty() ? lines[i + 1].trim() : inline);
            }
        }
    }

    public String getOclcNumber() { return oclcNumber; }
    public int getOnLoan()        { return onLoan; }
}