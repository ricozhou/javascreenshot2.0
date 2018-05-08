package screenshot;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImgMagnifier extends JFrame {
	// 容器
	private Container container = getContentPane();

	// 尺寸
	private int magnifierSize = 100;
	// 放大镜面板
	public MagnifierPanel magnifierPanel = new MagnifierPanel(magnifierSize);

	// 创建一个放大镜窗体
	public ImgMagnifier() {
		// 窗体边缘
		setUndecorated(true);
		setResizable(false);
		container.add(magnifierPanel);
		// 实时刷新
		updateSize(magnifierSize);
		// 圆形窗口
		this.setShape(new Ellipse2D.Double(0, 0, 200, 200));
		this.setVisible(false);
		this.setAlwaysOnTop(true);
	}

	// 更新放大镜窗体
	public void updateSize(int magnifierSize) {
		magnifierPanel.setMagnifierSize(magnifierSize + 100);
		setSize(magnifierSize + 100, magnifierSize + 100);
		// 更新所有子控件
		validate();
	}
}

// 放大镜面板
class MagnifierPanel extends JPanel {
	private Image screenImage;
	// 放大镜大小
	private int magnifierSize;

	private int locationX;

	private int locationY;

	private Robot robot;

	// 构造函数
	public MagnifierPanel(int magnifierSize) {
		Point p = MouseInfo.getPointerInfo().getLocation();
		try {
			robot = new Robot();
		} catch (AWTException e) {
		}
		// 截屏幕（也可以实例化的时候传一个过来）
		screenImage = robot.createScreenCapture(new Rectangle(0, 0, Toolkit.getDefaultToolkit().getScreenSize().width,
				Toolkit.getDefaultToolkit().getScreenSize().height));

		// 添加光标
		if (ScreenShotMainGUI.ssmm.isGetCursor()) {
			ImageIcon cursorImage = new ImageIcon(getClass().getResource("cursor.png"));
			screenImage.getGraphics().drawImage(cursorImage.getImage(), p.x, p.y, null);
		}
		this.magnifierSize = magnifierSize;
	}

	// 放大镜坐标
	public void setMagnifierLocation(int locationX, int locationY) {
		this.locationX = locationX;
		this.locationY = locationY;
		// 重画
		repaint();
	}

	public void setMagnifierSize(int magnifierSize) {
		this.magnifierSize = magnifierSize;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent((Graphics2D) g);
		// 关键处理代码（网上找的）
		g.drawImage(screenImage, // 要画的图片
				0, // 目标矩形的第一个角的x坐标
				0, // 目标矩形的第一个角的y坐标
				magnifierSize, // 目标矩形的第二个角的x坐标
				magnifierSize, // 目标矩形的第二个角的y坐标
				locationX + (magnifierSize / 4), // 源矩形的第一个角的x坐标
				locationY + (magnifierSize / 4), // 源矩形的第一个角的y坐标
				locationX + (magnifierSize / 4 * 3), // 源矩形的第二个角的x坐标
				locationY + (magnifierSize / 4 * 3), // 源矩形的第二个角的y坐标
				this);
		g.drawLine(100, 0, 100, 200);
		g.drawLine(0, 100, 200, 100);
	}
}
