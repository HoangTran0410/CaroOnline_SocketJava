package Docs.FromInternet.UseJCanvas;

//package dk.ruc.madsr.swing;
//----------------------------------------------------------------------
// This class is written by Mads Rosendahl, University of Roskilde, Denmark
// You may uncomment the first line, but otherwise, please do not change the code.
// If you find errors or have any suggestions contact me at madsr@ruc.dk
//
// version 1.2 : 30-10-06
//----------------------------------------------------------------

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.table.*;
import javax.swing.Timer;

//------------------------------------------------------------------------------
//
//   JEventQueue
//
//
//
//
public class JEventQueue implements
        ActionListener, AWTEventListener, KeyListener, DocumentListener, ChangeListener,
        ListSelectionListener, TableModelListener, MouseListener, WindowListener,
        WindowFocusListener, WindowStateListener, FocusListener, MenuKeyListener,
        ComponentListener {
    //------------------------------------------------

    private Queue<EventObject> queue = new LinkedList<EventObject>();

    /**
     * [Internal]
     */
    public synchronized void addEvent(EventObject e) {
        queue.offer(e);
        notify();
    }

    /**
     * [Internal]
     */
    public synchronized void removeEvents(String nm) {
        Object o = null;
        for (EventObject e : queue) {
            String n = getName(e);
            if (nm.equals(n)) {
                o = e;
                break;
            }
        }
        // I'm not allowed to remove them inside the loop
        if (o != null) {
            queue.remove(o);
            removeEvents(nm);
        }
    }

    /**
     *     */
    public synchronized boolean hasEvent() {
        return queue.peek() != null;
    }

    /**
     *     */
    public synchronized EventObject peekEvent() {
        return queue.peek();
    }

    /**
     *     */
    public synchronized EventObject waitEvent() {
        EventObject r = null;
        for (;;) {
            r = queue.poll();
            if (r != null) {
                return r;
            }
            try {
                wait();
            } catch (Exception e) {
                return null;
            }
        }
    }

    //-----------------------------------------------------------
    // action listener
    /**
     * [Internal:ActionListener]
     */
    public void actionPerformed(ActionEvent e) {
        addEvent(e);
    }

    /**
     * [Internal:ActionListener]
     */
    public void changedUpdate(DocumentEvent e) {
        addEvent(new DocumentEventObject(e));
    }

    /**
     * [Internal:ActionListener]
     */
    public void insertUpdate(DocumentEvent e) {
        addEvent(new DocumentEventObject(e));
    }

    /**
     * [Internal:ActionListener]
     */
    public void removeUpdate(DocumentEvent e) {
        addEvent(new DocumentEventObject(e));
    }

    //-----------------------------------------------------------
    // KeyListener
    /**
     * [Internal:KeyListener]
     */
    public void keyReleased(KeyEvent e) {
        addEvent(e);
    }

    /**
     * [Internal:KeyListener]
     */
    public void keyPressed(KeyEvent e) {
        addEvent(e);
    }

    /**
     * [Internal:KeyListener]
     */
    public void keyTyped(KeyEvent e) {
        addEvent(e);
    }

    //-----------------------------------------------------------
    // ListSelectionListener
    /**
     * [Internal:ListSelectionListener]
     */
    public void valueChanged(ListSelectionEvent e) {
        addEvent(e);
    }
    // TableModelListener

    /**
     * [Internal:TableModelListener]
     */
    public void tableChanged(TableModelEvent e) {
        addEvent(e);
    }

    //-----------------------------------------------------------
    // ChangeListener
    /**
     * [Internal:ChangeListener]
     */
    public void stateChanged(ChangeEvent e) {
        Object o = e.getSource();
        if (o instanceof JSlider) {
            if (((JSlider) o).getValueIsAdjusting()) {
                return;
            }
        }
        addEvent(e);
    }

    //-----------------------------------------------------------
    // AWTEventlistener
    /**
     * [Internal:AWTEventlistener]
     */
    public void eventDispatched(AWTEvent e) {
        addEvent(e);
    }

    //-----------------------------------------------------------
    // Mouselistener
    /**
     * [Internal:Mouselistener]
     */
    public void mouseClicked(MouseEvent e) {
        addEvent(e);
    }
    // Invoked when the mouse button has been clicked (pressed and released) on a  
    // component. 

    /**
     * [Internal:Mouselistener]
     */
    public void mouseEntered(MouseEvent e) {
        addEvent(e);
    }
    // Invoked when the mouse enters a component. 

    /**
     * [Internal:Mouselistener]
     */
    public void mouseExited(MouseEvent e) {
        addEvent(e);
    }
    // Invoked when the mouse exits a component. 

    /**
     * [Internal:Mouselistener]
     */
    public void mousePressed(MouseEvent e) {
        addEvent(e);
    }
    // Invoked when a mouse button has been pressed on a component. 

    /**
     * [Internal:Mouselistener]
     */
    public void mouseReleased(MouseEvent e) {
        addEvent(e);
    }
    // Invoked when a mouse button has been released on a component. 
    //

    //-----------------------------------------------------------
    // WindowListener
    /**
     * [Internal:WindowListener]
     */
    public void windowActivated(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when the Window is set to be the active Window. 

    /**
     * [Internal:WindowListener]
     */
    public void windowClosed(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when a window has been closed as the result of calling dispose on the 
    //window. 

    /**
     * [Internal:WindowListener]
     */
    public void windowClosing(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when the user attempts to close the window from the window's system menu. 

    /**
     * [Internal:WindowListener]
     */
    public void windowDeactivated(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when a Window is no longer the active Window. 

    /**
     * [Internal:WindowListener]
     */
    public void windowDeiconified(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when a window is changed from a minimized to a normal state. 

    /**
     * [Internal:WindowListener]
     */
    public void windowIconified(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when a window is changed from a normal to a minimized state. 

    /**
     * [Internal:WindowListener]
     */
    public void windowOpened(WindowEvent e) {
        addEvent(e);
    }
    //Invoked the first time a window is made visible. 
    // WindowFocusListener

    /**
     * [Internal:WindowListener]
     */
    public void windowGainedFocus(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when the Window is set to be the focused Window, which means that the 
    //Window, or one of its subcomponents, will receive keyboard events. 

    /**
     * [Internal:WindowListener]
     */
    public void windowLostFocus(WindowEvent e) {
        addEvent(e);
    }
    //Invoked when the Window is no longer the focused Window, which means that 
    //keyboard events will no longer be delivered to the Window or any of its 
    //subcomponents. 

    //-----------------------------------------------------------
    // WindowStateListener
    /**
     * [Internal:WindowStateListener]
     */
    public void windowStateChanged(WindowEvent e) {
        addEvent(e);
    }

    //-----------------------------------------------------------
    /**
     * [Internal]
     */
    public void componentHidden(ComponentEvent e) {
        addEvent(e);
    }
    //Invoked when the component has been made invisible. 

    /**
     * [Internal]
     */
    public void componentMoved(ComponentEvent e) {
        addEvent(e);
    }
    //Invoked when the component's position changes. 

    /**
     * [Internal]
     */
    public void componentResized(ComponentEvent e) {
        addEvent(e);
    }
    //Invoked when the component's size changes. 

    /**
     * [Internal]
     */
    public void componentShown(ComponentEvent e) {
        addEvent(e);
    }
    //Invoked when the component has been made visible. 

    //-----------------------------------------------------------
    // FocusListener
    /**
     * [Internal:FocusListener]
     */
    public void focusGained(FocusEvent e) {
        addEvent(e);
    }
    //Invoked when a component gains the keyboard focus. 

    /**
     * [Internal:FocusListener]
     */
    public void focusLost(FocusEvent e) {
        addEvent(e);
    }
    //Invoked when a component loses the keyboard focus. 

    //-----------------------------------------------------------
    // MenuKeyListener
    /**
     * [Internal:MenuKeyListener]
     */
    public void menuKeyPressed(MenuKeyEvent e) {
        addEvent(e);
    }
    //Invoked when a key has been pressed. 

    /**
     * [Internal:MenuKeyListener]
     */
    public void menuKeyReleased(MenuKeyEvent e) {
        addEvent(e);
    }
    //Invoked when a key has been released. 

    /**
     * [Internal:MenuKeyListener]
     */
    public void menuKeyTyped(MenuKeyEvent e) {
        addEvent(e);
    }
    //Invoked when a key has been typed. 

    //-----------------------------------------------------------
    /*
   Interface AncestorListener
   void ancestorAdded(AncestorEvent event) 
     Called when the source or one of its ancestors is made visible either by       
     setVisible(true) being called or by its being added to the component hierarchy. 
   void ancestorMoved(AncestorEvent event) 
     Called when either the source or one of its ancestors is moved. 
   void ancestorRemoved(AncestorEvent event) 
     Called when the source or one of its ancestors is made invisible either by 
     setVisible(false) being called or by its being remove from the component          
     hierarchy. 
   Class AncestorEvent
   static int ANCESTOR_ADDED 
     An ancestor-component was added to the hierarchy of visible objects (made  
     visible), and is currently being displayed. 
   static int ANCESTOR_MOVED 
     An ancestor-component changed its position on the screen. 
   static int ANCESTOR_REMOVED 
     An ancestor-component was removed from the hierarchy of visible objects (hidden) 
     and is no longer being displayed. 
     */
 /*
   import java.beans 
   Interface VetoableChangeListener
   void vetoableChange(PropertyChangeEvent evt) 
     This method gets called when a constrained property is changed. 
   Class PropertyChangeEvent
     */
 /*
   Interface ContainerListener
   void componentAdded(ContainerEvent e) 
     Invoked when a component has been added to the container. 
   void componentRemoved(ContainerEvent e) 
     Invoked when a component has been removed from the container. 
   Class ContainerEvent
   static int COMPONENT_ADDED 
     This event indicates that a component was added to the container. 
   static int COMPONENT_REMOVED 
     This event indicates that a component was removed from the container. 
   static int CONTAINER_FIRST 
     The first number in the range of ids used for container events. 
   static int CONTAINER_LAST 
     The last number in the range of ids used for container events. 
     */
 /*
   Interface PropertyChangeListener
   void propertyChange(PropertyChangeEvent evt) 
     This method gets called when a bound property is changed.        
     */
 /*
   Interface ComponentListener
   void componentHidden(ComponentEvent e) 
     Invoked when the component has been made invisible. 
   void componentMoved(ComponentEvent e) 
     Invoked when the component's position changes. 
   void componentResized(ComponentEvent e) 
     Invoked when the component's size changes. 
   void componentShown(ComponentEvent e) 
     Invoked when the component has been made visible. 
   Class ComponentEvent
   static int COMPONENT_FIRST 
     The first number in the range of ids used for component events. 
   static int COMPONENT_HIDDEN 
     This event indicates that the component was rendered invisible. 
   static int COMPONENT_LAST 
     The last number in the range of ids used for component events. 
   static int COMPONENT_MOVED 
     This event indicates that the component's position changed. 
   static int COMPONENT_RESIZED 
     This event indicates that the component's size changed. 
   static int COMPONENT_SHOWN 
     This event indicates that the component was made visible.          
     */
 /*
   Interface FocusListener
   void focusGained(FocusEvent e) 
     Invoked when a component gains the keyboard focus. 
   void focusLost(FocusEvent e) 
     Invoked when a component loses the keyboard focus. 
   class FocusEvent
   static int FOCUS_FIRST 
     The first number in the range of ids used for focus events. 
   static int FOCUS_GAINED 
     This event indicates that the Component is now the focus owner. 
   static int FOCUS_LAST 
     The last number in the range of ids used for focus events. 
   static int FOCUS_LOST 
     This event indicates that the Component is no longer the focus owner. 
     */
 /*
   Interface HierarchyBoundsListener
   void ancestorMoved(HierarchyEvent e) 
     Called when an ancestor of the source is moved. 
   void ancestorResized(HierarchyEvent e) 
     Called when an ancestor of the source is resized. 
   Class HierarchyEvent
   static int ANCESTOR_MOVED 
     The event id indicating an ancestor-Container was moved. 
   static int ANCESTOR_RESIZED 
     The event id indicating an ancestor-Container was resized. 
   static int DISPLAYABILITY_CHANGED 
     Indicates that the HIERARCHY_CHANGED event was generated due to a change in the   
     displayability of the hierarchy. 
   static int HIERARCHY_CHANGED 
     The event id indicating that modification was made to the entire hierarchy tree. 
   static int HIERARCHY_FIRST 
     Marks the first integer id for the range of hierarchy event ids. 
   static int HIERARCHY_LAST 
     Marks the last integer id for the range of ancestor event ids. 
   static int PARENT_CHANGED 
     Indicates that the HIERARCHY_CHANGED event was generated by a reparenting 
     operation. 
   static int SHOWING_CHANGED 
     Indicates that the HIERARCHY_CHANGED event was generated due to a change in the 
     showing state of the hierarchy. 
     */
 /*
   Interface HierarchyListener
   void hierarchyChanged(HierarchyEvent e) 
     Called when the hierarchy has been changed. 
   Class HierarchyEvent
   static int ANCESTOR_MOVED 
     The event id indicating an ancestor-Container was moved. 
   static int ANCESTOR_RESIZED 
     The event id indicating an ancestor-Container was resized. 
   static int DISPLAYABILITY_CHANGED 
     Indicates that the HIERARCHY_CHANGED event was generated due to a change in the 
     displayability of the hierarchy. 
   static int HIERARCHY_CHANGED 
     The event id indicating that modification was made to the entire hierarchy tree. 
   static int HIERARCHY_FIRST 
     Marks the first integer id for the range of hierarchy event ids. 
   static int HIERARCHY_LAST 
     Marks the last integer id for the range of ancestor event ids. 
   static int PARENT_CHANGED 
     Indicates that the HIERARCHY_CHANGED event was generated by a reparenting   
     operation. 
   static int SHOWING_CHANGED 
     Indicates that the HIERARCHY_CHANGED event was generated due to a change in the 
     showing state of the hierarchy.           
     */
 /*
   Interface InputMethodListener
   void caretPositionChanged(InputMethodEvent event) 
     Invoked when the caret within composed text has changed. 
   void inputMethodTextChanged(InputMethodEvent event) 
     Invoked when the text entered through an input method has changed. 
   Class InputMethodEvent
   static int CARET_POSITION_CHANGED 
     The event type indicating a changed insertion point in input method text. 
   static int INPUT_METHOD_FIRST 
     Marks the first integer id for the range of input method event ids. 
   static int INPUT_METHOD_LAST 
     Marks the last integer id for the range of input method event ids. 
   static int INPUT_METHOD_TEXT_CHANGED 
     The event type indicating changed input method text. 
     */
 /*
   Interface KeyListener
   void keyPressed(KeyEvent e) 
     Invoked when a key has been pressed. 
   void keyReleased(KeyEvent e) 
     Invoked when a key has been released. 
   void keyTyped(KeyEvent e) 
     Invoked when a key has been typed. 
   Class KeyEvent

   Interface MouseListener
   Interface MouseMotionListener
     */
 /*
   Interface MouseWheelListener
   void mouseWheelMoved(MouseWheelEvent e) 
     Invoked when the mouse wheel is rotated. 
   Class MouseWheelEvent          
   static int WHEEL_BLOCK_SCROLL 
     Constant representing scrolling by a "block" (like scrolling with page-up, 
     page-down keys) 
   static int WHEEL_UNIT_SCROLL 
     Constant representing scrolling by "units" (like scrolling with the arrow keys) 
     */
    //------------------------------------------------------------
    //
    /**
     * Return a string representation of an event
     */
    public static String eventType(EventObject o) {
        if (o == null) {
            return "null";
        }
        String s = o.getClass().getName();
        if (s.indexOf(".") > 0) {
            return s.substring(s.lastIndexOf(".") + 1);
        }
        return s;
    }
    private static Map<Object, String> names = new HashMap<Object, String>();

    /**
     * Return the name associated with the component that generated the event
     */
    public static String getName(EventObject e) {
        Object o = e.getSource();
        //XAux.pl("source "+o);
        if (o instanceof JComponent) {
            Object v = ((JComponent) o).getClientProperty("name");
            if (v != null) {
                return (String) v;
            }
        } else if (o instanceof Document) {
            Object v = ((Document) o).getProperty("name");
            if (v != null) {
                return (String) v;
            }
        } else if (o instanceof Component) {
            Object v = ((Component) o).getName();
            if (v != null) {
                return (String) v;
            }
        } else if (names.containsKey(o)) {
            return names.get(o);
        }
        //System.out.println("cannot find source "+o);
        String s = o.toString();
        if (s.length() < 20 && s.indexOf("[") < 0) {
            return s;
        }
        return "";
    }

    /**
     * register a name with a component that may generate events
     */
    public static void addName(Object o, String nm) {
        if (o instanceof JComponent) {
            ((JComponent) o).putClientProperty("name", nm);
        } else if (o instanceof Document) {
            ((Document) o).putProperty("name", nm);
        } else if (o instanceof Component) {
            ((Component) o).setName(nm);
        } else {
            names.put(o, nm);
            //System.out.println("Don't know how to add name to "+o);
        }
    }
    //------------------------------------------
    //
    //
    //

    /**
     * listen to events from a component
     */
    public void listenTo(Component jc, String s) {
        addName(jc, s);
        jc.addKeyListener(this);
        //
        if (jc instanceof JMenuItem) {
            ((JMenuItem) jc).addActionListener(this);
        } else if (jc instanceof AbstractButton) {
            ((AbstractButton) jc).addActionListener(this);
        } else if (jc instanceof JComboBox) {
            ((JComboBox) jc).addActionListener(this);
        } else if (jc instanceof JSlider) {
            ((JSlider) jc).addChangeListener(this);
        } else if (jc instanceof JSpinner) {
            ((JSpinner) jc).addChangeListener(this);
        } else if (jc instanceof JTextField) {
            addName(((JTextField) jc).getDocument(), s);
            ((JTextField) jc).addActionListener(this);
            ((JTextField) jc).getDocument().addDocumentListener(this);
        } else if (jc instanceof JTextArea) {
            addName(((JTextArea) jc).getDocument(), s);
            ((JTextArea) jc).getDocument().addDocumentListener(this);
        } else if (jc instanceof JPanel) {
            ((JPanel) jc).addMouseListener(this);
        } else if (jc instanceof JLabel) {
            ((JLabel) jc).addMouseListener(this);
        } else if (jc instanceof JList) {
            ((JList) jc).addListSelectionListener(this);
        } else if (jc instanceof TableModel) {
            ((TableModel) jc).addTableModelListener(this);
        } else if (jc instanceof JFrame) {
            ((JFrame) jc).addWindowFocusListener(this);
            ((JFrame) jc).addWindowStateListener(this);
            ((JFrame) jc).addWindowListener(this);
            ((JFrame) jc).addComponentListener(this);
            Container p = ((JFrame) jc).getContentPane();
            p.addKeyListener(this);
        } else if (jc instanceof JComponent) {
            ((JComponent) jc).addMouseListener(this);
        } else {
            System.out.println("Don't know how to listen to " + jc);
        }
    }
    //------------------------------------------------
    //
    // Access to Window events
    //

    /**
     * Check whether an event is a WindowClosing event
     */
    public static boolean isWindowClosing(EventObject e) {
        return e != null && e instanceof WindowEvent
                && ((WindowEvent) e).getID() == WindowEvent.WINDOW_CLOSING;
    }

    /**
     * Check whether an event is a WindowResizing event
     */
    public static boolean isWindowResizing(EventObject e) {
        return e != null && e instanceof ComponentEvent
                && ((ComponentEvent) e).getID() == ComponentEvent.COMPONENT_RESIZED;
    }
    //------------------------------------------------
    //
    // Access to Mouse events
    //

    /**
     * Check whether an event is a event
     */
    public static boolean isMouseEvent(EventObject e) {
        return e != null && e instanceof MouseEvent;
    }

    /**
     * Check whether an event is a event
     */
    public static boolean isMousePressed(EventObject e) {
        return isMouseEvent(e) && ((MouseEvent) e).getID() == MouseEvent.MOUSE_PRESSED;
    }

    /**
     * Check whether an event is a event
     */
    public static boolean isMouseClicked(EventObject e) {
        return isMouseEvent(e) && ((MouseEvent) e).getID() == MouseEvent.MOUSE_CLICKED;
    }

    /**
     * Check whether an event is a event
     */
    public static boolean isMouseReleased(EventObject e) {
        return isMouseEvent(e) && ((MouseEvent) e).getID() == MouseEvent.MOUSE_RELEASED;
    }

    /**
     * Check whether an event is a event
     */
    public static int getMouseX(EventObject e) {
        if (!isMouseEvent(e)) {
            return 0;
        }
        return ((MouseEvent) e).getX();
    }

    /**
     * Check whether an event is a event
     */
    public static int getMouseY(EventObject e) {
        if (!isMouseEvent(e)) {
            return 0;
        }
        return ((MouseEvent) e).getY();
    }

    /**
     * Check whether an event is a event
     */
    public static int getMouseButton(EventObject e) {
        if (!isMouseEvent(e)) {
            return 0;
        }
        return ((MouseEvent) e).getButton();
    }

    /**
     * Check whether an event is a event
     */
    public static int getMouseClickCount(EventObject e) {
        if (!isMouseEvent(e)) {
            return 0;
        }
        return ((MouseEvent) e).getClickCount();
    }
    //-----------------------------------------------
    //
    // Access to keyboard events
    //

    /**
     * Check whether an event is a event
     */
    public static boolean isKeyEvent(EventObject e) {
        return e != null && e instanceof KeyEvent;
    }

    /**
     * Check whether an event is a event
     */
    public static boolean isKeyPressed(EventObject e) {
        return isKeyEvent(e) && ((KeyEvent) e).getID() == KeyEvent.KEY_PRESSED;
    }

    /**
     * Check whether an event is a event
     */
    public static boolean isKeyReleased(EventObject e) {
        return isKeyEvent(e) && ((KeyEvent) e).getID() == KeyEvent.KEY_RELEASED;
    }

    /**
     * Check whether an event is a event
     */
    public static boolean isKeyTyped(EventObject e) {
        return isKeyEvent(e) && ((KeyEvent) e).getID() == KeyEvent.KEY_TYPED;
    }

    /**
     * Check whether an event is a event
     */
    public static boolean isActionKey(EventObject e) {
        return isKeyPressed(e) && ((KeyEvent) e).isActionKey();
    }

    /**
     * Check whether an event is a event
     */
    public static char getKeyChar(EventObject e) {
        if (!isKeyEvent(e)) {
            return ' ';
        }
        return ((KeyEvent) e).getKeyChar();
    }

    /**
     * Check whether an event is a event
     */
    public static int getKeyCode(EventObject e) {
        if (!isKeyEvent(e)) {
            return 0;
        }
        return ((KeyEvent) e).getKeyCode();
    }

    /**
     * Check whether an event is a event
     */
    public static String getKeyText(EventObject e) {
        String s = "";
        if (isKeyEvent(e) && !isKeyTyped(e)) {
            s = KeyEvent.getKeyText(((KeyEvent) e).getKeyCode());
        }
        if (isKeyTyped(e)) {
            s = Character.toString(((KeyEvent) e).getKeyChar());
        }
        return s;
    }
    //-----------------------------------------------------

    /**
     * Check whether an event is a event
     */
    public static boolean isActionPerformed(EventObject e) {
        return (e instanceof ActionEvent)
                && ((ActionEvent) e).getID() == ActionEvent.ACTION_PERFORMED;
    }
    //-----------------------------------------------------

    /**
     * Get column number from a TableModelEvent
     */
    public static int getColumn(EventObject e) {
        if (e instanceof TableModelEvent) {
            return ((TableModelEvent) e).getColumn();
        }
        return -1;
    }

    /**
     * Get First Row from a TableModelEvent
     */
    public static int getFirstRow(EventObject e) {
        if (e instanceof TableModelEvent) {
            return ((TableModelEvent) e).getFirstRow();
        }
        return -1;
    }

    /**
     * Get Last Row from a TableModelEvent
     */
    public static int getLastRow(EventObject e) {
        if (e instanceof TableModelEvent) {
            return ((TableModelEvent) e).getLastRow();
        }
        return -1;
    }

    //-------------------------------
    //
    //  Timer controls
    //
    private static Map<String, Timer> timers = new HashMap<String, Timer>();

    /**
     * Start a timer
     */
    public void startTimer(int interval, String name) {
        name = name.intern();
        if (timers.containsKey(name)) {
            System.out.println("already has timer " + name);
            return;
        }
        Timer t = new Timer(interval, this);
        t.start();
        timers.put(name, t);
        addName(t, name);
    }

    /**
     * Stop a timer
     */
    public void stopTimer(String name) {
        name = name.intern();
        if (!timers.containsKey(name)) {
            System.out.println("no timer " + name);
            return;
        }
        Timer t = timers.get(name);
        t.stop();
        timers.remove(name);
        removeEvents(name);
    }

    /**
     * Sleep a number of milliseconds
     */
    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }
    //--------------------------------------------------------------
    //
    //  Methods to construct menubars, menus and menuitems
    //

    /**
     * Create a CheckBoxMenuItem and listen to it
     */
    public JCheckBoxMenuItem jcheckboxmenuitem(String s) {
        JCheckBoxMenuItem jm = new JCheckBoxMenuItem(s);
        listenTo(jm, s);
        return jm;
    }

    /**
     * Create a RadioButtonMenuItem and listen to it
     */
    public JRadioButtonMenuItem jradiobuttonmenuitem(String s) {
        JRadioButtonMenuItem jm = new JRadioButtonMenuItem(s);
        listenTo(jm, s);
        return jm;
    }

    /**
     * Create a MenuItem and listen to it
     */
    public JMenuItem jmenuitem(String s) {
        JMenuItem jm = new JMenuItem(s);
        listenTo(jm, s);
        return jm;
    }

    /**
     * Create a MenuItem and listen to it
     */
    public JMenuItem jmenuitem(String s, char c) {
        JMenuItem jm = new JMenuItem(s, c);
        listenTo(jm, s);
        return jm;
    }

    /**
     * Create a MenuItem and listen to it
     */
    public JMenuItem jmenuitem(String s, char c, KeyStroke k) {
        JMenuItem jm = new JMenuItem(s, c);
        jm.setAccelerator(k);
        listenTo(jm, s);
        return jm;
    }

    /**
     * Control-char KeyStroke
     */
    public static KeyStroke control(char c) {
        return KeyStroke.getKeyStroke(c, Toolkit.getDefaultToolkit().
                getMenuShortcutKeyMask(), false);
    }

    /**
     * Control-Shift-char KeyStroke
     */
    public static KeyStroke controlShift(char c) {
        return KeyStroke.getKeyStroke(c, Toolkit.getDefaultToolkit().
                getMenuShortcutKeyMask() | KeyEvent.SHIFT_MASK, false);
    }

    /**
     * Create a Menu and listen to it
     */
    public JMenu jmenu(String s, JComponent... j) {
        JMenu jm = new JMenu(s);
        for (JComponent j1 : j) {
            jm.add(j1);
        }
        listenTo(jm, s);
        return jm;
    }

    /**
     * Create a Menu and listen to it
     */
    public JMenu jmenu(String s, char c, JComponent... j) {
        JMenu jm = new JMenu(s);
        jm.setMnemonic(c);
        //jm.setFocusable(true);
        for (JComponent j1 : j) {
            jm.add(j1);
        }
        listenTo(jm, s);
        return jm;
    }

    /**
     * Create a MenuBar
     */
    public JMenuBar jmenubar(Font f, JMenu... j) {
        JMenuBar jm = new JMenuBar();
        for (JMenu j1 : j) {
            jm.add(j1);
        }
        setFontRecursively(jm, f);
        return jm;
    }

    /**
     * Create a MenuBar
     */
    public JMenuBar jmenubar(JMenu... j) {
        JMenuBar jm = new JMenuBar();
        for (JMenu j1 : j) {
            jm.add(j1);
        }
        return jm;
    }

    /**
     * Set the font of a component and all its subcomponents
     */
    public static void setFontRecursively(Component c, Font f) {
        if (c == null) {
            return;
        }
        c.setFont(f);
        if (!(c instanceof Container)) {
            return;
        }
        Component[] cs = ((Container) c).getComponents();
        for (Component c1 : cs) {
            setFontRecursively(c1, f);
        }
        if (!(c instanceof JMenu)) {
            return;
        }
        cs = ((JMenu) c).getMenuComponents();
        for (Component c1 : cs) {
            setFontRecursively(c1, f);
        }
    }
    //--------------------------------------------------
    // Dialogs

    static EventObject jdialog(JFrame owner, String title, JComponent body, JComponent... ok) {
        JDialog dialog = new JDialog(owner, title);
        dialog.setContentPane(body);
        dialog.pack();
        if (owner != null) {
            dialog.setLocationRelativeTo(owner);
        } else {
            centerLocation(dialog);
        }

        JEventQueue events = new JEventQueue();
        if (ok != null && ok.length != 0) {
            for (int i = 0; i < ok.length; i++) {
                events.listenTo(ok[i], "ok" + i);
            }
        }
        dialog.addWindowListener(events);
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        dialog.setVisible(true);
        EventObject e;
        do {
            e = events.waitEvent();
        } while ((e instanceof WindowEvent) && (((WindowEvent) e).getID() != WindowEvent.WINDOW_CLOSING));
        dialog.dispose();
        return e;
    }

    private static void centerLocation(Component c) {
        try {
            Dimension d1 = c.getSize();
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension d2 = toolkit.getScreenSize();
            //System.out.println(d1+" "+d2);
            int x = Math.max(0, (d1.width - d2.width) / 2);
            int y = Math.max(0, (d1.height - d2.height) / 2);
            c.setLocation(new Point(x, y));
        } catch (Exception e) {
        }
    }
}

//--------------------------------------------------------
//   
// DocumentEventObject
// source compatible with DocumentEvent, but inherited from EventObject
//
class DocumentEventObject extends EventObject implements DocumentEvent {

    DocumentEvent de;

    DocumentEventObject(DocumentEvent de1) {
        super(de1);
        de = de1;
    }

    public Object getSource() {
        return de.getDocument();
    }
    public static final long serialVersionUID = 42L;
    // from DocumentEvent

    public DocumentEvent.ElementChange getChange(Element elem) {
        return de.getChange(elem);
    }
    //Gets the change information for the given element. 

    public Document getDocument() {
        return de.getDocument();
    }
    //Gets the document that sourced the change event. 

    public int getLength() {
        return de.getLength();
    }
    //Returns the length of the change. 

    public int getOffset() {
        return de.getOffset();
    }
    //Returns the offset within the document of the start of the change. 

    public DocumentEvent.EventType getType() {
        return de.getType();
    }
    //Gets the type of event. 
}
