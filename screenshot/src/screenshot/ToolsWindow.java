package screenshot;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.JWindow;

//操作窗口
public class ToolsWindow extends JWindow {
	private Screenshot parent;
	// 工具栏
	ImageIcon copyImage = new ImageIcon(getClass().getResource("copy.png"));
	ImageIcon saveBImage = new ImageIcon(getClass().getResource("save.png"));
	ImageIcon cancelImage = new ImageIcon(getClass().getResource("cancel.png"));
//	// 任意线段
//	ImageIcon drawImage = new ImageIcon(getClass().getResource("draw.png"));
//	// 添加文字
//	ImageIcon charImage = new ImageIcon(getClass().getResource("dchar.png"));
//	// 撤销
//	ImageIcon revokeImage = new ImageIcon(getClass().getResource("revoke.png"));
//	// 矩形
//	ImageIcon rectImage = new ImageIcon(getClass().getResource("rect.png"));
//	// 圆角矩形
//	ImageIcon CirRectImage = new ImageIcon(getClass().getResource("cirrect.png"));
//	// 椭圆形
//	ImageIcon ovalImage = new ImageIcon(getClass().getResource("oval.png"));
//	// 圆形
//	ImageIcon circleImage = new ImageIcon(getClass().getResource("circle.png"));
//	// 直线
//	ImageIcon lineImage = new ImageIcon(getClass().getResource("line.png"));
//	// 马赛克
//	ImageIcon mosaicImage = new ImageIcon(getClass().getResource("mosaic.png"));
//	// 分享
//	ImageIcon shareImage = new ImageIcon(getClass().getResource("share.png"));

	public ToolsWindow(Screenshot parent, int x, int y) {
		this.parent = parent;
		this.init();
		this.setLocation(x, y);
		this.pack();
		this.setVisible(false);
		// this.setResizable(false);
	}

	private void init() {
		this.setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar("a");

		// 复制
		JButton copyClipButton = new JButton(copyImage);
		copyClipButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					parent.copyClipImage();
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(copyClipButton);

		// 保存按钮
		JButton saveButton = new JButton(saveBImage);
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					parent.saveImage();
					dispose();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(saveButton);

		// 标记矩形
		if (parent.sm.isRectangleCallout()) {
			// 矩形
			ImageIcon rectImage = new ImageIcon(getClass().getResource("rect.png"));
			// 标记矩形
			JButton rectButton = new JButton(rectImage);
			rectButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(0);
					// dispose();
				}
			});
			toolBar.add(rectButton);
		}

		// 标记圆角矩形
		if (parent.sm.isRoundedRectangleCallout()) {
			// 圆角矩形
			ImageIcon CirRectImage = new ImageIcon(getClass().getResource("cirrect.png"));
			// 标记圆角矩形
			JButton CirRectButton = new JButton(CirRectImage);
			CirRectButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(6);
					// dispose();
				}
			});
			toolBar.add(CirRectButton);
		}

		// 标记椭圆
		if (parent.sm.isOvalCallout()) {
			// 椭圆形
			ImageIcon ovalImage = new ImageIcon(getClass().getResource("oval.png"));
			// 标记椭圆
			JButton ovalButton = new JButton(ovalImage);
			ovalButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(1);
					// dispose();
				}
			});
			toolBar.add(ovalButton);

		}

		// 标记圆形
		if (parent.sm.isCirCallout()) {
			// 圆形
			ImageIcon circleImage = new ImageIcon(getClass().getResource("circle.png"));
			// 标记圆形
			JButton circleButton = new JButton(circleImage);
			circleButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(2);
					// dispose();
				}
			});
			toolBar.add(circleButton);
		}

		// 标记文字
		if (parent.sm.isAddChar()) {
			// 添加文字
			ImageIcon charImage = new ImageIcon(getClass().getResource("dchar.png"));
			// 标记文字
			JButton charButton = new JButton(charImage);
			charButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(7);
					// dispose();
				}
			});
			toolBar.add(charButton);
		}

		// 标记任意线段
		if (parent.sm.isCurveCallout()) {
			// 任意线段
			ImageIcon drawImage = new ImageIcon(getClass().getResource("draw.png"));
			// 标记任意线段
			JButton drawButton = new JButton(drawImage);
			drawButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(3);
					// dispose();
				}
			});
			toolBar.add(drawButton);
		}

		// 标记直线
		if (parent.sm.isLineCallout()) {
			// 直线
			ImageIcon lineImage = new ImageIcon(getClass().getResource("line.png"));
			// 标记直线
			JButton lineButton = new JButton(lineImage);
			lineButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(4);
					// dispose();
				}
			});
			toolBar.add(lineButton);
		}

		// 标记马赛克
		if (parent.sm.isMosaic()) {
			// 马赛克
			ImageIcon mosaicImage = new ImageIcon(getClass().getResource("mosaic.png"));
			// 标记马赛克
			JButton mosaicButton = new JButton(mosaicImage);
			mosaicButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawImage(5);
					// dispose();
				}
			});
			toolBar.add(mosaicButton);

		}

		// 分享
		if (parent.sm.isShare()) {
			// 分享
			ImageIcon shareImage = new ImageIcon(getClass().getResource("share.png"));
			// 分享
			JButton shareButton = new JButton(shareImage);
			shareButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// dispose();
				}
			});
			toolBar.add(shareButton);
		}

		// 回退
		if (parent.sm.isCallBack()) {
			// 撤销
			ImageIcon revokeImage = new ImageIcon(getClass().getResource("revoke.png"));
			// 回退
			JButton revokeButton = new JButton(revokeImage);
			revokeButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.drawBackImage();
					// dispose();
				}
			});
			toolBar.add(revokeButton);
		}

		// 关闭按钮
		JButton closeButton = new JButton(cancelImage);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.cancelSshot();
				dispose();
			}
		});
		toolBar.add(closeButton);
		this.add(toolBar, BorderLayout.NORTH);

		// 置顶
		this.setAlwaysOnTop(true);
	}

}
