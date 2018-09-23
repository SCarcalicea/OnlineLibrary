package booksService;

public class Book {

    private String color = "";
    private String title = "";
    private String publisher = "";
    private String author = "";
    public String filePath = "";
    private String owner = "";
    private String category = "";
    
    
    public Book() {
        
    }

    public String getColor() {
        if (color.isEmpty()) {
            return "yellowgreen";
        }
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFilePath() {
        StringBuilder temp = new StringBuilder("");
        temp.append(filePath.substring(filePath.indexOf("web/")+4));
        return temp.toString();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOwner() {
        if (owner.isEmpty()) {
            return "Available";
        }
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    /**
     * This method will return a string representation of the values that 
     * are going to be inserted into the data base. Like in the example below
     * 
     * @return - ('testing2','testing2','testing2','testing2','testing2','testing2')
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("('");
        String space = "','";
        String end = "')";
        buf.append(this.filePath).append(space)
                .append(this.getTitle()).append(space)
                .append(this.getPublisher()).append(space)
                .append(this.getAuthor()).append(space)
                .append(this.getOwner()).append(space)
                .append(this.getCategory()).append(space)
                .append(this.getColor()).append(end);
        
        return buf.toString();
    } 
}
