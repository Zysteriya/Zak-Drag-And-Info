package tekgui;

// TEKGUI imports
import tekgui.window.TEKLabel;
import tekgui.window.TEKPanel;
import tekgui.text.CleanedHTMLExtractor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

// Java imports
import java.awt.Dimension;
import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.List;
import java.time.Instant;
import java.time.ZoneId;
import java.io.IOException;
/**
 * manage object properties
 *
 * @Mara Doze
 * @10/31/24
 */
public class ObjectUI
{
    private String name;
    private String creationTime;
    private TEKLabel label;
    private File file;
    private List<String> content; // separated for a reason
    private List<String> alteredContent;
    private boolean canSave = false;
    // Collection to store ObjectUI instances with similar content
    private Collection<ObjectUI> similarContent = new HashSet<>();
    public ObjectUI(String name, Point position, Dimension size) //initialize objects
    {
        this.name = name;
        this.label = new TEKLabel(this);
        setPosition(position);
        setSize(size);
        label.setText(TEKPanel.formatObjectDetails(this));
    }
    private String getModifiedTime() { 
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant instant = Instant.ofEpochMilli(file.lastModified());
        LocalDateTime modifiedDate =
            LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return dtf.format(modifiedDate); //obtain timestamp
    }
    public String getName(){return name;}
    public void setName(String name) throws IOException{
        if(file != null){
            throw new IOException("File already exists, it cannot be adjusted.");
        }
        // Will be placed in the selected directory under this name?
        this.name = name; 
    }
    public TEKLabel getLabel(){ return label;}
    public void setLabel(TEKLabel label){this.label = label;}
    public String getCreationTime(){return creationTime;}
    public List<String> getContent(){return alteredContent;}
    public void setContent(){
        canSave = true;
        // later
        // TEKFile.getFrame();
    }
    public boolean canSave(){return canSave;}
    public Point getPosition(){return label.getLocation();}
    public void setPosition(Point position){label.setLocation(position);}
    public Dimension getSize(){return label.getSize();}
    private void setSize(Dimension size){label.setSize(size);}
    public void initFile(File file) throws IOException{
        if(this.file != null){
            throw new IOException(file.getAbsolutePath()+"File already exists, it cannot be adjusted.");
        }
        this.file = file;
        // ....
    }
    public File getFile(){return file;}
    @Override
    public String toString() {
        return "Name: " + name + ", Created: " + creationTime + ", Position: (" + getPosition().x + ", " + getPosition().y + "), Size: (" + getSize().width + " x " + getSize().height + ")";
        //returns the ObjectUI details in string format
    }
    // Method to generate a string that can be saved to a file (for saving object state)
    public String getDataToSave() {
        // Adjust when generating**
        return name + "," + getPosition().x + "," + getPosition().y + "," + getSize().width + "," + getSize().height + "," + creationTime;
    }

    // Static method to create an ObjectUI from saved data
    public static ObjectUI fromData(String data) {
        // Split the saved data string by commas to extract the properties
        String[] parts = data.split(",");
        String name = parts[0];
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int width = Integer.parseInt(parts[3]);
        int height = Integer.parseInt(parts[4]);
        String creationTime = parts[5];

        // Create the ObjectUI with parsed data
        ObjectUI objectUI = new ObjectUI(name, new Point(x, y), new Dimension(width, height));
        // Manually set the creation time
        objectUI.creationTime = creationTime;

        return objectUI;
    }
    // add an ObjectUI to similarContent if it has similar content
    public void addIfSimilarContent(ObjectUI other) {
        if (isSimilarContent(other)) {
            similarContent.add(other);
        }
    }
    // compare content of this ObjectUI with another
    private boolean isSimilarContent(ObjectUI other) {
        return this.getContent().equals(other.getContent());
    }
    // Retrieve similar content objects
    public Collection<ObjectUI> getSimilarContent() {
        return similarContent;
    }
    // Optional method to handle altered content connections
    public void updateContentConnection(ObjectUI altered) {
        if (!similarContent.contains(altered)) {
            similarContent.add(altered);
        }
    }
    // Method to generate HTML representation of this ObjectUI
    public String generateHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<div class='object-ui'>")
        .append("<h2>").append(name).append("</h2>")
        .append("<p><strong>Created:</strong> ").append(creationTime).append("</p>")
        .append("<p><strong>Position:</strong> (").append(getPosition().x).append(", ").append(getPosition().y).append(")</p>")
        .append("<p><strong>Size:</strong> (").append(getSize().width).append(" x ").append(getSize().height).append(")</p>");

        if (!similarContent.isEmpty()) {
            html.append("<h3>Similar Content</h3><ul>");
            for (ObjectUI similar : similarContent) {
                html.append("<li>").append(similar.getName()).append("</li>");
            }
            html.append("</ul>");
        }

        html.append("</div>");
        return html.toString();
    }
}
