package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MiniMapFrame extends JFrame {
	private int width;
	private int height;
	private MiniMap miniMap;

	private static final long serialVersionUID = 1L;
	
	public MiniMapFrame(int w, int h, JScrollPane pane, Tab tab) {	
		this.width    = w;
        this.height   = h;
        
        this.setTitle("MiniMap");
        this.setSize(this.width, this.height);
        this.setMinimumSize(new Dimension(this.width, this.height));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.miniMap = new MiniMap(pane, tab);
        super.getContentPane().add(miniMap);
        
        this.setVisible(true);
	}
	
	public MiniMap getMiniMap() {
		return this.miniMap;
	}

}
