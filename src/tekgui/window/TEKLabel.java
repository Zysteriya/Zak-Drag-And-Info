package tekgui.window;

// TEKGUI imports
import tekgui.ObjectUI;
import tekgui.adapter.TEKLabelAdapter;

// Java imports
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
/**
 * Write a description of class TEKLabel here.
 *
 * @author Mara Doze, Hayden Verstrat, Noah Winn
 * @version Sept. 30, 2024
 */
public class TEKLabel extends JLabel{
    private boolean isSelected = false; // Track selection state
    private void init(){
        addMouseListener(new TEKLabelAdapter());
        // Ensure the label is set to automatically resize vertically based on content
        setVerticalAlignment(TOP);  // Align text to top
        setHorizontalAlignment(LEFT); // Align text to left
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adding a border
        setOpaque(true);
    }
    /**
     * Default constructor, adds a mouseListener, aligns top left, and has a black border.
     */
    public TEKLabel(){
        super();
        init();
    }
    /**
     * Creates a new TEKLabel with the specified text.
     */
    public TEKLabel(String text){
        super(text);
        init();
    }
    /**
     * Creates a new TEKLabel with the specified objectUI.
     */
    public TEKLabel(ObjectUI obj){
        super(TEKPanel.formatObjectDetails(obj));
        setBounds(obj.getPosition().x, obj.getPosition().y, obj.getSize().width, obj.getSize().height);
        init();
    }
    /**
     * Selects the TEKLabel and signifies it to the user by coloring the border and foreground.
     */
    public void select(){
        setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        setForeground(Color.BLUE);
        isSelected = true;
    }
    /**
     * Deselects the TEKLabel and signifies it to the user by decoloring the border and foreground.
     */
    public void deselect(){
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        setForeground(Color.BLACK);
        isSelected = false;
    }
    public boolean isSelected() {
        return isSelected;
    }
}