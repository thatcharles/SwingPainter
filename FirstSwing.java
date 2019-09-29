/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstswing;
import java.awt.Button;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.out;
import java.util.ArrayList;
import javax.swing.*; 
/**
 *
 * @author charleschung
 */

public class FirstSwing extends JFrame{
    
    JLabel statusLabel = new JLabel("Your status will display here.",JLabel.CENTER); 
    ArrayList<Canvas> canvases = new ArrayList<Canvas>();
    public int currentCanvas = 0;
    
    JPanel ContentArea;
    JScrollPane contentScrollPane;
    
    JRadioButton Select = new JRadioButton("Select tool");
    JRadioButton Line = new JRadioButton("Line tool");
    JRadioButton Rectangle = new JRadioButton("Rectangle tool");
    JRadioButton Oval = new JRadioButton("Oval tool");
    JRadioButton Pen = new JRadioButton("Pen tool");
    JRadioButton Text = new JRadioButton("Text tool");
    JPanel MenuPanel = new JPanel(new BorderLayout());
    JMenuBar MenuBar = new JMenuBar();
    JMenu File = new JMenu("File");
    JMenu View = new JMenu("View");
    JMenu Edit = new JMenu("Edit");

    JMenuItem New= new JMenuItem("New");
    JMenuItem Delete= new JMenuItem("Delete");
    JMenuItem Quit= new JMenuItem("Quit");
    JMenuItem Next= new JMenuItem("Next");
    JMenuItem Previous= new JMenuItem("Previous");
   
    public FirstSwing(String title) 
    { 
        super(title);
        this.setSize(700, 300);
        this.setLocation(100, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Canvas initCanvas = new Canvas("12345");
        canvases.add(initCanvas);
        
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        
        ContentArea = new JPanel(new BorderLayout());
        Canvas c = canvases.get(currentCanvas);
        contentScrollPane = new JScrollPane(); 
        contentScrollPane.getViewport().add(c);
        
        ContentArea.add(contentScrollPane,BorderLayout.CENTER);
        
        mainContainer.add(myGetMenuBar(),BorderLayout.NORTH);
        mainContainer.add(ContentArea,BorderLayout.CENTER);
        mainContainer.add(myGetToolPalette(),BorderLayout.WEST);
        mainContainer.add(myGetStatusBar(),BorderLayout.SOUTH);

        if(canvases.size() == 1){
            Next.setEnabled(false);
            Previous.setEnabled(false);
            Delete.setEnabled(false);
        }
    } 
    
    class CustomActionListener implements ActionListener{
        
        private String str;

        public CustomActionListener(String str) {
            this.str = str;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            statusLabel.setText(this.str + " Button Clicked.");
            if (e.getSource() == Line){
                Canvas c = canvases.get(currentCanvas);
                out.println(c.str);
                c.setTool("line");
            }
            else if (e.getSource() == Rectangle){
                Canvas c = canvases.get(currentCanvas);
                out.println(c.str);
                c.setTool("rect");
            }
            else if (e.getSource() == Oval){
                Canvas c = canvases.get(currentCanvas);
                out.println(c.str);
                c.setTool("oval");
            }
            else if (e.getSource() == Pen){
                Canvas c = canvases.get(currentCanvas);
                out.println(c.str);
                c.setTool("stroke");
            }
        }
    }
    
    class AddCanvasListener implements ActionListener{
        
        private String str;
        public AddCanvasListener(String str) {
            this.str = str;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(str=="new"){
                Canvas canvas = new Canvas("New Canvas");
                canvases.add(canvas);
                currentCanvas = canvases.size()-1;
                Next.setEnabled(false);
                Previous.setEnabled(true);
                Canvas current = canvases.get(currentCanvas);
                contentScrollPane.getViewport().removeAll();
                contentScrollPane.getViewport().add(current);
                for(Component c : contentScrollPane.getComponents()){
                    if(c instanceof Canvas ){
                        out.println(((Canvas) c).str);
                    }
                }
                Delete.setEnabled(true);
            }
            else if(str=="delete"){
                canvases.remove(currentCanvas);
                if(canvases.size() == 1){
                    Next.setEnabled(false);
                    Previous.setEnabled(false);
                    Delete.setEnabled(false);
                }
                if(currentCanvas == canvases.size()){
                    currentCanvas--;
                }
                contentScrollPane.getViewport().removeAll();
                Canvas current = canvases.get(currentCanvas);
                contentScrollPane.getViewport().add(current);
                
                for(Component c : contentScrollPane.getComponents()){
                    if(c instanceof Canvas ){
                        out.println(((Canvas) c).str);
                    }
                }
            }
            repaint();
        }
    }
    
    class canvasControlListener implements ActionListener{

        private String str;
        public canvasControlListener(String str) {
            this.str = str;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(this.str == "next"){
                currentCanvas ++;
                contentScrollPane.getViewport().removeAll();
                Canvas current = canvases.get(currentCanvas);
                contentScrollPane.getViewport().add(current);
            }
            else if(this.str == "prev"){
                currentCanvas --;
                contentScrollPane.getViewport().removeAll();
                Canvas current = canvases.get(currentCanvas);
                contentScrollPane.getViewport().add(current);
            }
            if(currentCanvas == canvases.size()-1 && currentCanvas == 0){
                Next.setEnabled(false);
                Previous.setEnabled(false);
            }
            else if(currentCanvas == 0){
                Next.setEnabled(true);
                Previous.setEnabled(false);
            }
            else if(currentCanvas == canvases.size()-1){
                Next.setEnabled(false);
                Previous.setEnabled(true);
            }
            else{
                Next.setEnabled(true);
                Previous.setEnabled(true);
            }
            repaint();
        }
    }
    
    private JPanel myGetToolPalette(){
        JPanel toolPalette = new JPanel(new GridLayout(3, 2));
        toolPalette.setPreferredSize(new Dimension(200, 600));
        toolPalette.setMaximumSize(new Dimension(200, 600));
        
        Select.addActionListener(new CustomActionListener("Select"));
        Line.addActionListener(new CustomActionListener("Line"));
        Rectangle.addActionListener(new CustomActionListener("Rectangle"));
        Oval.addActionListener(new CustomActionListener("Oval"));
        Pen.addActionListener(new CustomActionListener("Pen"));
        Text.addActionListener(new CustomActionListener("Text"));
        
        ButtonGroup group = new ButtonGroup();
        group.add(Select);
        group.add(Line);
        group.add(Rectangle);
        group.add(Oval);
        group.add(Pen);
        group.add(Text);
        
        toolPalette.add(Select);
        toolPalette.add(Line);
        toolPalette.add(Rectangle);
        toolPalette.add(Oval);
        toolPalette.add(Pen);
        toolPalette.add(Text);
        
        return toolPalette;
    }
    
    private JPanel myGetStatusBar(){
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel);
        return statusPanel;
    }
    
    private JPanel myGetMenuBar(){
        
        
        New.addActionListener(new AddCanvasListener("new"));
        Delete.addActionListener(new AddCanvasListener("delete"));
        // Quit.addActionListener(new CustomActionListener("Quit"));
        Next.addActionListener(new canvasControlListener("next"));
        Previous.addActionListener(new canvasControlListener("prev"));
        
        
        Quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        File.add(New);
        File.add(Delete);
        File.add(Quit);
        View.add(Next);
        View.add(Previous);
        
        MenuBar.add(File);
        MenuBar.add(View);
        MenuBar.add(Edit);
        
        MenuPanel.add(MenuBar);
        
        return MenuPanel;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FirstSwing myLayout = new FirstSwing("My first Layout");
        myLayout.setVisible(true);
    }
    
}
