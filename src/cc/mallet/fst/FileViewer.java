package cc.mallet.fst;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FileViewer extends Frame implements ActionListener {
  String directory; // Default directory

  TextArea textarea; // Display text file here

  public FileViewer() {
    this(null, null);
  }

  public FileViewer(String filename) {
    this(null, filename);
  }

//	Constructor
  
  public FileViewer(String directory, String filename) {
    super(); 

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });

    // Create a TextArea to display the contents of the file
    
    textarea = new TextArea("", 24, 80);
    textarea.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
    textarea.setEditable(false);
    this.add("Center", textarea);

    // Create a bottom panel to hold a couple of buttons
    
    Panel p = new Panel();
    p.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    this.add(p, "South");

    // Create the buttons and arrange to handle button clicks
    
    Font font = new Font("SansSerif", Font.BOLD, 14);
    Button openfile = new Button("Open File");
    Button close = new Button("Close");
    openfile.addActionListener(this);
    openfile.setActionCommand("open");
    openfile.setFont(font);
    close.addActionListener(this);
    close.setActionCommand("close");
    close.setFont(font);
    p.add(openfile);
    p.add(close);

    this.pack();

    // Figure out the directory, from filename or current dir, if necessary
    if (directory == null) {
      File f;
      if ((filename != null) && (f = new File(filename)).isAbsolute()) {
        directory = f.getParent();
        filename = f.getName();
      } else
        directory = System.getProperty("user.dir");
    }

    this.directory = directory; // Remember directory
    setFile(directory, filename); // Load and display the file
  }

  /**
   * Load and display the specified file from the specified directory
   */
  public void setFile(String directory, String filename) {
    if ((filename == null) || (filename.length() == 0))
      return;
    File f;
    FileReader in = null;
    
    // Read and display the file contents. 
    
    try {
      f = new File(directory, filename); 
      in = new FileReader(f); 
      char[] buffer = new char[4096]; // Read 4K characters at a time
      int len; // How many chars read each time
      textarea.setText(""); 
      while ((len = in.read(buffer)) != -1) { 
        String s = new String(buffer, 0, len); 
        textarea.append(s); 
      }
      this.setTitle("Topic words file viewer: " + filename); // Title
      textarea.setCaretPosition(0); // Go to start of file
    }
    catch (IOException e) {
      textarea.setText(e.getClass().getName() + ": " + e.getMessage());
      this.setTitle("FileViewer: " + filename + ": I/O Exception");
    }

    // Close input stream
    
    finally {
      try {
        if (in != null)
          in.close();
      } catch (IOException e) {
      }
    }
  }

  
  public void actionPerformed(ActionEvent e) {
    String cmd = e.getActionCommand();
    if (cmd.equals("open")) { 

    	// Instantiate file dialog box to prompt for a new file to display
    	
      FileDialog f = new FileDialog(this, "Open File", FileDialog.LOAD);
      f.setDirectory(directory); // Default directory

      f.show();

      directory = f.getDirectory(); // Remember new default directory
      setFile(directory, f.getFile()); 
      f.dispose(); 
    } else if (cmd.equals("close"))
      this.dispose(); // then close the window
  }
}