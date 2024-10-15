package tekgui.window;

// TEKGUI imports
import tekgui.helper.MenuBuilder;
import tekgui.adapter.TEKActionAdapter;

// Java imports
import javax.swing.JPopupMenu;
import javax.swing.JComponent;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
/**
 * Prepared the popupmenu for common features to be added later.
 *
 * @author Noah Winn
 * @version Oct. 13, 2024
 */
public class TEKPopupMenu extends JPopupMenu{
    private static JComponent attachedComponent;
    private static TEKActionAdapter action = new TEKActionAdapter();
    
    public TEKPopupMenu(){
        super();
        MenuBuilder.addMenuItem("Edit", this, null, "Pops up the display menu.", action);
        addSeparator();
        MenuBuilder.addMenuItem("Cut", this, null, "Used to cut a selected object.", action);
        MenuBuilder.addMenuItem("Copy", this, null, "Used to copy a selected object.", action);
        MenuBuilder.addMenuItem("Paste", this, null, "Used to paste in an object.", action);
        addSeparator();
        MenuBuilder.addMenuItem("Undo", this, null, "Used to undo the action enacted upon an object.", action);
        MenuBuilder.addMenuItem("Redo", this, null, "Used to redo the action enacted upon an object.", action);
        addSeparator();
        MenuBuilder.addMenuItem("Reset Zoom", this, null, "Used to reset the zoom on the screen.", action);
        setVisible(false);
    }
    public void activate(MouseEvent e){
        setAttached((JComponent)e.getSource());
        getComponent(0).setEnabled((attachedComponent instanceof TEKLabel) ? true : false); // Edit
        getComponent(2).setEnabled(true); // Cut, check if possible (1+ selected or current)
        getComponent(3).setEnabled(true); // Copy, check if possible (1+ selected or current)
        getComponent(4).setEnabled(true); // Paste, check if possible
        getComponent(6).setEnabled(true); // Undo, check if possible (1+ action)
        getComponent(7).setEnabled(true); // Redo, check if possible (1+ action)
        getComponent(9).setEnabled(true); // Reset Zoom, check if possible (zoom != regular)
        show(e.getComponent(), e.getX(), e.getY());
        setVisible(true);
    }
    public void deactivate(){
        setAttached(null);
        setVisible(false);
    }
    public JComponent getAttached(){return attachedComponent;}
    public void setAttached(JComponent attachedComponent){
        this.attachedComponent = attachedComponent;
    }
    /**
     * Activates TEKEditFrame and imports the held attachment.  
     * Hides the TEKPopupMenu.
     */
    public void transferAttached(){
        if(attachedComponent == null){return;}
        if(!(attachedComponent instanceof TEKLabel)){return;}
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                new TEKEditView(TEKPanel.getObjectFromLabel((TEKLabel)attachedComponent));
                deactivate();
            }
        });
    }
}