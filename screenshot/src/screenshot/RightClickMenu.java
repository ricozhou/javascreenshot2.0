package screenshot;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

public class RightClickMenu extends JPopupMenu {
	public Screenshot screenshot;
	public JWindow jw1;
	public int x, y;
	public JPopupMenu jpm = new JPopupMenu();

	public RightClickMenu(Screenshot screenshot) {
		this.screenshot = screenshot;
		this.x = x;
		this.y = y;
		init();
	}

	public void init() {
		System.out.println(4);
		JMenuItem jmi[] = new JMenuItem[3];
		for (int i = 0; i < jmi.length; i++) {
			jmi[i] = new JMenuItem(i + "");
			jpm.add(jmi[i]);
		}
		
//		this.addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				System.out.println(5);
//				if (e.getButton() == MouseEvent.BUTTON3) {
//					// 右键点击
//					System.out.println(333);
//					//显示右击菜单
//					jpm.show(jw1, e.getX(), e.getY());;
//				}
//			}
//		});
	}

}
