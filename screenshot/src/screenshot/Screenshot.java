package screenshot;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

//继承jwindow，无边框，与jframe同等级
public class Screenshot extends JFrame {
	// 注意使用jframe，使用jwindows也可以但是无法显示文本框，不能添加文字，故此更改
	public JFrame jf;
	public JFrame jw1;
	private int startx, starty, endx, endy;
	// 标记范围
	private int startx2, starty2, endx2, endy2;
	// 最大截图标记，保存的最大范围
	public int xx, yy, ww, hh;
	public int xr, yr;
	// 第一次取到的屏幕
	private BufferedImage image = null;
	// 缓存加深颜色的主屏幕
	private BufferedImage tempImage = null;
	// 需要保存的图片
	private BufferedImage saveImage = null;
	// 是否开始画笔
	public boolean isdraw;
	// 缓存而已
	public BufferedImage tempImage2 = null;
	// 用于使用画笔的缓存
	public BufferedImage tempImage3 = null;

	// 提供还原功能的list
	public List<BufferedImage> biList = new ArrayList<BufferedImage>();
	// 工具栏的坐标
	public List<int[]> toolsLocationList = new ArrayList<int[]>();
	// 是否全屏
	public boolean isFullScreen = true;
	// 全屏参数
	public int maxW, maxH;

	// 剪切板
	Clipboard clipboard;
	// 工具类
	// public ToolsWindow tools = new ToolsWindow(Screenshot.this, 0, 0);
	public ToolsWindow tools;
	public SetupMsg sm;
	// 放大镜
	public ImgMagnifier mf = new ImgMagnifier();
	// 按钮功能标志
	public static int drawFlag = -1;
	public DrawUtils du = new DrawUtils();
	public SshotUtils su = new SshotUtils();

	// 鼠标单双击
	public static boolean clickFlag = false;
	public int clickNum = 0;
	// 按压时间
	public long mousePressedTime;
	// 鼠标松开时间
	public long mouseReleasedTime;
	// 鼠标右键菜单
	public RightClickMenu rcm;
	// 鼠标点击左键标志
	public boolean isClickLeft;
	// 鼠标点击右键标志
	public boolean isClickRight;
	// 右击菜单显示标志
	public boolean isShowRightMenu;
	// 右击菜单启用标志
	public boolean isCanUseRM;
	// 开始添加文字的标志
	public boolean isStartAddChar;

	// 右键菜单
	public JPopupMenu jpm;
	public JMenuItem jmi[];
	// 添加文字的文本框
	public JTextField tt1;
	public JPanel jp1;
	public JFrame jf2;
	public int jfx, jfy;

	// 画笔
	public Graphics2D g2;

	// 普通初始化
	public Screenshot(boolean bl) throws AWTException {
		init(bl);
	}

	public Screenshot() throws AWTException {
		// init(bl);
	}

	// 无障碍初始化
	public Screenshot(JFrame jf, Boolean bl, SetupMsg sm) throws AWTException {
		this.setUndecorated(true);
		this.jf = jf;
		this.sm = sm;
		this.jw1 = this;
		tools = new ToolsWindow(Screenshot.this, 0, 0);
		init(bl);
		// 初始化右击菜单
		initMouseRC();

	}

	// 初始化右击菜单
	private void initMouseRC() {
		jpm = new JPopupMenu();
		// 取出设置中右击菜单功能放入集合
		Map<Integer, String> jpmMap = su.getJpmMenu();
		if (jpmMap != null && jpmMap.size() > 0) {
			// 可用
			isCanUseRM = true;
			for (Integer key : jpmMap.keySet()) {
				String value = jpmMap.get(key);
				JMenuItem jmi = new JMenuItem(value);
				jpm.add(jmi);
				jmi.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (key == 1) {
								// 取消当前截图
								cancelSshot();
							} else if (key == 2) {
								// 保存当前截图
								saveImage();
							} else if (key == 3) {
								// 复制当前截图
								copyClipImage();
							} else if (key == 4) {
								// 撤销当前一步
								drawBackImage();
							} else if (key == 5) {
								// 分享

							} else if (key == 6) {
								// 退出程序
								System.exit(0);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		} else {
			// 不可用
			isCanUseRM = false;
		}
		// if (sm.isAddChar()) {
		// 添加显示一个文本框
		jf2 = new JFrame();
		jf2.setUndecorated(true);
		// jp1 = new JPanel();
		tt1 = new JTextField();
		tt1.setBounds(0, 0, 200, 25);
		// tt1.setColumns(100);
		// tt1.setVisible(false);
		tt1.setEditable(true);
		// jp1 = new JPanel();
		// jp1.setLayout(null);
		// jp1.add(tt1);
		jf2.add(tt1);
		jf2.setSize(200, 25);
		jf2.setVisible(false);
		// tt1.setBounds(e.getX(), e.getY(),200,25);

		// }

	}

	private void init(boolean bl) throws AWTException {
		// 若无障碍则隐藏主体jframe（最小化）
		if (bl == true) {
			jf.setExtendedState(JFrame.ICONIFIED);
		}
		this.setVisible(true);
		// 获取屏幕尺寸
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		// 初始化最大屏幕
		xx = 0;
		yy = 0;
		ww = d.width;
		maxW = d.width;
		hh = d.height;
		maxH = d.height;
		this.setBounds(0, 0, d.width, d.height);
		// 截取最大屏幕
		Point p = MouseInfo.getPointerInfo().getLocation();
		Robot robot = new Robot();
		image = robot.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
		// 添加光标
		if (sm.isGetCursor()) {
			ImageIcon cursorImage = new ImageIcon(getClass().getResource("cursor.png"));
			image.getGraphics().drawImage(cursorImage.getImage(), p.x, p.y, null);
		}


//System.out.println(jw1.getDefaultCursor());
		// 初始化就给全屏
		saveImage = image;

		// 将图片全屏显示浮于上方

		// 监听鼠标点击松开
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					// 如果开始添加文字则鼠标事件暂停
					if (isStartAddChar) {
						return;
					}
					System.out.println(e.getComponent().getCursor().getName());

					// 点击了左键
					isClickLeft = true;
					// 左键
					// 按下的时候记下时间，以便于判断是否执行了拖拽
					mousePressedTime = System.currentTimeMillis();
					// 记录鼠标点击行为 坐标
					if (isdraw) {
						// 记录画框的坐标
						startx2 = e.getX();
						starty2 = e.getY();
						xr = e.getX();
						yr = e.getY();
					} else {
						// 截图的坐标
						startx = e.getX();
						starty = e.getY();
						// tools.setVisible(false);
						// 置顶取消
						// tools.setAlwaysOnTop(false);
					}
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					// 中键点击
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					// 右键点击
					// 点击了右键
					isClickRight = true;
					// 如果开始添加文字则鼠标事件暂停
					if (isStartAddChar) {
						tt1.setText("");
					}
					tools.setVisible(true);

				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					// 如果开始添加文字则鼠标事件暂停
					if (isStartAddChar) {
						return;
					}
					// 松开了左键
					isClickLeft = false;
					// 左键
					// 记录松开时间
					mouseReleasedTime = System.currentTimeMillis();
					// 判断按压时间和松开时间间隔
					if ((mouseReleasedTime - mousePressedTime) < 150) {
						// 小于50说明是单击或者双击，不是拖拽
					} else {
						if (isdraw) {
							// 将缓存图片加入list以供还原
							if (biList.size() > 9) {
								// 如果已经存在10张了则删除第一张
								biList.remove(0);
								toolsLocationList.remove(0);
							}
							biList.add(tempImage3);
							// 同时把工具栏的坐标也保存下
							toolsLocationList.add(new int[] { xx + ww, yy + hh });
							// 如果是画框则每次都保存最新的图片
							tempImage2 = tempImage3;
							saveImage = tempImage2.getSubimage(xx, yy, ww, hh);
						} else {
							if (isFullScreen) {
								// 全屏暂时不支持画图等功能
								return;
							}
							// 判断边界
							int[] coo = getToolsCoo(xx + ww, yy + hh);
							tools.setLocation(coo[0], coo[1]);

							// 如果打开了放大镜功能
							if (sm.isShowMagnifier()) {
								// 松开鼠标即停止放大镜
								mf.setVisible(false);
								mf.dispose();
							}

							tools.setVisible(true);
							// 置顶
							tools.setAlwaysOnTop(true);
							tools.toFront();
							biList.add(tempImage2);
							// 同时把工具栏的坐标也保存下
							toolsLocationList.add(new int[] { xx + ww, yy + hh });
						}
					}
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					// 中键点击
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					// 右键点击
					// 松开了右键
					isClickRight = false;
				}

			}

			// 单双击
			// 较难控制
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					// 如果可用且打开了右击菜单则左击取消
					if (isCanUseRM && isShowRightMenu) {
						jw1.remove(jpm);
						isShowRightMenu = false;
						// 取消之后不再做任何操作
						return;
					}

					// 如果开始添加文字则鼠标事件暂停
					if (isStartAddChar) {
						if (jf2.isVisible()) {
							// jf2.setVisible(false);
							// 判断是否在框外，是的话取消窗口
							if (!(e.getX() > jfx && e.getX() < (jfx + 200) && e.getY() > jfy
									&& e.getY() < (jfy + 25))) {
								if (!"".equals(tt1.getText().trim())) {
									// 先创建个缓存图片
									tempImage3 = (BufferedImage) createImage(Screenshot.this.getWidth(),
											Screenshot.this.getHeight());
									// 获取缓存的画笔
									g2 = (Graphics2D) tempImage3.getGraphics();
									// 将之前截好并显示的图加入进来
									g2.drawImage(tempImage2, 0, 0, null);
									// 此处以后不再写死
									Font font = new Font(null, Font.BOLD, 15);
									g2.setFont(font);

									g2.setColor(sm.getgColor() != null ? sm.getgColor() : Color.RED);
									// 加粗
									g2.setStroke(new BasicStroke(sm.getgSize() != 0 ? sm.getgSize() : 1.0f));
									g2.drawString(tt1.getText().trim(), jfx + 1, jfy + 3);
									tt1.setText("");
									// 画完框将此缓存图片显示在整个屏幕
									Screenshot.this.getGraphics().drawImage(tempImage3, 0, 0, Screenshot.this);

									// 将缓存图片加入list以供还原
									if (biList.size() > 9) {
										// 如果已经存在10张了则删除第一张
										biList.remove(0);
										toolsLocationList.remove(0);
									}
									biList.add(tempImage3);
									// 同时把工具栏的坐标也保存下
									toolsLocationList.add(new int[] { xx + ww, yy + hh });
									// 如果是画框则每次都保存最新的图片
									tempImage2 = tempImage3;
									saveImage = tempImage2.getSubimage(xx, yy, ww, hh);
								}
								tools.setVisible(true);
								jf2.setVisible(false);
								isStartAddChar = false;
							}
						} else {
							// 记录文本框位置
							jfx = e.getX();
							jfy = e.getY();
							jf2.setVisible(true);
							jf2.setLocation(e.getX(), e.getY() - 12);
							jf2.setAlwaysOnTop(true);
						}
					} else {
						// 左键点击
						clickFlag = false;
						// 如果鼠标在规定的时间内已经被单击过一次，则说明这次是双击了，执行双击事件
						if (clickNum == 1) {
							// 双击
							try {
								// 执行需要时间，所以先改变状态
								clickFlag = true;
								clickNum = 0;
								// 根据设置的功能进行设置
								startMouseDCFeature();
							} catch (Exception e1) {
								e1.printStackTrace();
							}

							return;
						}

						// 定时器
						java.util.Timer timer = new java.util.Timer();

						timer.schedule(new java.util.TimerTask() {
							// 记录定时器执行的次数
							int n = 0;

							@Override
							public void run() {
								// 如果双击事件已经执行完毕，取消单击事件
								if (clickFlag) {
									clickNum = 0;
									n = 0;
									this.cancel();
									return;
								}
								// 如果规定的时间内未执行鼠标双击事件则执行鼠标单击事件
								if (n == 1) {
									// 单击
									// 根据设置的功能进行设置
									try {
										clickFlag = true;
										clickNum = 0;
										n = 0;
										startMouseSCFeature();
									} catch (Exception e) {
										e.printStackTrace();
									}
									this.cancel();
									return;
								}
								n++;
								clickNum++;
							}
						}, new java.util.Date(), 300);
					}

				} else if (e.getButton() == MouseEvent.BUTTON2) {
					// 中键点击

				} else if (e.getButton() == MouseEvent.BUTTON3) {
					// 开启添加文字时右键也停止
					if (isStartAddChar) {
						return;
					}
					// 如果可用
					if (isCanUseRM) {

						// 显示右击菜单
						jw1.add(jpm);
						jpm.show(jw1, e.getX(), e.getY());
						isShowRightMenu = true;
						tools.setVisible(true);
						// tools.setAlwaysOnTop(true);
					}
				}
			}
		});

		// 监听鼠标拖动
		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// 如果左键被点击了
				if (isClickLeft) {
					// 如果开始添加文字则鼠标事件暂停
					if (isStartAddChar) {
						return;
					}
					// 左键
					// 一旦拖拽就不再是全屏
					isFullScreen = false;
					// 是否标记重点(画框)
					if (isdraw) {
						// 鼠标拖动时，记录画框坐标
						endx2 = e.getX();
						endy2 = e.getY();
						// 先创建个缓存图片
						tempImage3 = (BufferedImage) createImage(Screenshot.this.getWidth(),
								Screenshot.this.getHeight());
						// 获取缓存的画笔
						g2 = (Graphics2D) tempImage3.getGraphics();
						// 将之前截好并显示的图加入进来
						g2.drawImage(tempImage2, 0, 0, null);
						// 最小值
						int x2 = Math.min(startx2, endx2);
						int y2 = Math.min(starty2, endy2);

						// 最大值
						int xx2 = Math.max(startx2, endx2);
						int yy2 = Math.max(starty2, endy2);

						int width2 = Math.abs(endx2 - startx2) + 1;
						int height2 = Math.abs(endy2 - starty2) + 1;
						g2.setColor(sm.getgColor() != null ? sm.getgColor() : Color.RED);
						// 加粗
						g2.setStroke(new BasicStroke(sm.getgSize() != 0 ? sm.getgSize() : 1.0f));

						if (Screenshot.drawFlag == 0) {
							// 在此基础上画框
							g2.drawRect(x2 - 1, y2 - 1, width2 + 1, height2 + 1);
						} else if (Screenshot.drawFlag == 1) {
							// 画椭圆
							g2.drawOval(x2 - 1, y2 - 1, width2 + 1, height2 + 1);
						} else if (Screenshot.drawFlag == 2) {
							// 画圆
							int d = (int) Math
									.sqrt((double) ((width2 + 1) * (width2 + 1) + (height2 + 1) * (height2 + 1)));
							g2.drawOval((xx2 + x2) / 2 - d / 2, (yy2 + y2) / 2 - d / 2, d, d);
						} else if (Screenshot.drawFlag == 3) {
							// 任意线段
							du.drawCurveLine(xr, yr, endx2, endy2, g2);
							// 画曲线是连续的，需要实施替换缓存图片
							tempImage2 = tempImage3;

						} else if (Screenshot.drawFlag == 4) {
							// 直线
							du.drawStrLine(startx2, starty2, endx2, endy2, g2);
						} else if (Screenshot.drawFlag == 5) {
							// 马赛克
							du.markImageByMosaic(tempImage3, 4, startx2, starty2, endx2, endy2);
						} else if (Screenshot.drawFlag == 6) {
							// 圆角矩形
							g2.drawRoundRect(x2 - 1, y2 - 1, width2 + 1, height2 + 1, 10, 10);
						}

						// 画完框将此缓存图片显示在整个屏幕
						Screenshot.this.getGraphics().drawImage(tempImage3, 0, 0, Screenshot.this);
						xr = endx2;
						yr = endy2;
					} else {
						// 拖动选择最大截图框
						tools.setVisible(false);
						// 置顶取消
						tools.setAlwaysOnTop(false);
						// 鼠标拖动时，记录坐标
						endx = e.getX();
						endy = e.getY();
						int x = Math.min(startx, endx);
						int y = Math.min(starty, endy);
						int width = Math.abs(endx - startx) + 1;
						int height = Math.abs(endy - starty) + 1;
						// 创建缓存图片
						tempImage2 = (BufferedImage) createImage(Screenshot.this.getWidth(),
								Screenshot.this.getHeight());

						// 获取画布画笔
						Graphics g = tempImage2.getGraphics();
						// 将加深颜色的整个屏幕加入
						g.drawImage(tempImage, 0, 0, null);

						g.setColor(Color.BLUE);
						// 画截图的范围
						g.drawRect(x - 1, y - 1, width + 1, height + 1);
						// 如果打开显示像素功能则画上去
						if (sm.isShowPixels) {
							// 根据坐标画上去
							// 边界判断
							int py = y;
							if (py < 10) {
								py = y + hh + 16;
							}
							g.drawString(width + " x " + height, x + 3, py - 3);
						}
						// 如果打开了显示坐标轴功能
						if (sm.isShowAxis) {
							// 横向
							g.drawLine(0, y + height, maxW, y + height);
							// 纵向
							g.drawLine(x + width, 0, x + width, maxH);
						}

						// 使用刚开始没有任何变化的屏幕根据坐标截取范围
						saveImage = image.getSubimage(x, y, width, height);
						// 加入（即截取的部分颜色又显示正常）
						g.drawImage(saveImage, x, y, null);
						// 显示在屏幕上
						Screenshot.this.getGraphics().drawImage(tempImage2, 0, 0, Screenshot.this);
						// 记录最后的截图范围坐标
						xx = x;
						yy = y;
						ww = width;
						hh = height;

						// 如果打开了放大镜功能
						if (sm.isShowMagnifier()) {
							// 显示放大镜
							mf.setVisible(true);
							mf.magnifierPanel.setMagnifierLocation(endx - 100, endy - 100);
							mf.setLocation(endx - 25, endy - 25);
						}
					}
				} else if (isClickRight) {
					// 如果右键被点击了

				}

			}
		});

		// 置顶
		jw1.setAlwaysOnTop(true);
	}

	// 单击事件触发
	protected void startMouseSCFeature() throws Exception {
		int featureFlag = ScreenShotMainGUI.ssmm.getMouseSCFeature();
		if (featureFlag == 0) {
			// 不设
			return;
		} else if (featureFlag == 1) {
			// 取消当前截图
			cancelSshot();
		} else if (featureFlag == 2) {
			// 保存当前截图
			saveImage();
		} else if (featureFlag == 3) {
			// 复制当前截图
			copyClipImage();
		} else if (featureFlag == 4) {
			// 撤销当前一步
			drawBackImage();
		} else if (featureFlag == 5) {
			// 退出程序
			System.exit(0);
		}
	}

	// 双击事件触发
	protected void startMouseDCFeature() throws Exception {
		int featureFlag = ScreenShotMainGUI.ssmm.getMouseDCFeature();
		if (featureFlag == 0) {
			// 不设
			return;
		} else if (featureFlag == 1) {
			// 取消当前截图
			cancelSshot();
		} else if (featureFlag == 2) {
			// 保存当前截图
			saveImage();
		} else if (featureFlag == 3) {
			// 复制当前截图
			copyClipImage();
		} else if (featureFlag == 4) {
			// 撤销当前一步
			drawBackImage();
		} else if (featureFlag == 5) {
			// 退出程序
			System.exit(0);
		}
	}

	// 绘制图片
	@Override
	public void paint(Graphics g) {
		// 绘制一个全屏幕加深的图片
		RescaleOp ro = new RescaleOp(0.7f, 0, null);
		tempImage = ro.filter(image, null);
		g.drawImage(tempImage, 0, 0, this);
	}

	// 保存图片
	public void saveImage() throws IOException {
		boolean isCus;
		// 如果文件不存在，其默认值是false，需要手动改成自定义保存
		if (!ScreenShotMainGUI.fileExit) {
			isCus = true;
		} else {
			isCus = sm.isCustomizeSave();
		}

		String path = "C:\\";
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = sdf.format(new Date());
		String imgFormat = sm.getImgFormat() != null ? sm.getImgFormat() : "png";
		if (isCus) {
			// 取消置顶
			jw1.setAlwaysOnTop(false);
			tools.setAlwaysOnTop(false);

			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("保存");

			// 文件过滤器，用户过滤可选择文件
			// FileNameExtensionFilter filter = new
			// FileNameExtensionFilter("JPG", "jpg");
			// jfc.setFileFilter(filter);

			// 初始化一个默认文件
			File filePath = FileSystemView.getFileSystemView().getHomeDirectory();
			File defaultFile = new File(filePath + File.separator + fileName + "." + imgFormat);
			jfc.setSelectedFile(defaultFile);

			int flag = jfc.showSaveDialog(jf);
			if (flag == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				path = file.getPath();
				// 检查文件后缀，防止用户忘记输入后缀或者输入不正确的后缀
				if (!(path.endsWith(".png") || path.endsWith(".PNG"))
						&& !(path.endsWith(".jpg") || path.endsWith(".JPG"))
						&& !(path.endsWith(".bmp") || path.endsWith(".BMP"))
						&& !(path.endsWith(".jpeg") || path.endsWith(".JPEG"))
						&& !(path.endsWith(".gif") || path.endsWith(".GIF"))) {
					path += ".png";
				}
			} else {
				ScreenShotMainGUI.isStartedSshot = false;
				// 热键注册部分取消
				su.cancelRegistrationHotkey();
				dispose();
				tools.dispose();
				// if (sm.isAddChar()) {
				jf2.dispose();
				// }
				return;
			}
		} else {
			// 直接默认保存
			File f = new File(sm.getCustomSavePath());
			if (!f.exists()) {
				// 不存在则创建
				try {
					f.mkdirs();
					path = f.getAbsolutePath() + "\\" + fileName + "." + imgFormat;
				} catch (Exception e) {
					e.printStackTrace();
					// 创建失败则指定c盘根目录
					path = "C:\\" + fileName + "." + imgFormat;
				}
			} else {
				path = sm.getCustomSavePath() + "\\" + fileName + "." + imgFormat;
			}
		}
		// 写入文件
		// 点击一下表示截全屏
		if (saveImage == null) {
			saveImage = image;
		}
		ImageIO.write(saveImage, imgFormat, new File(path));
		ScreenShotMainGUI.isStartedSshot = false;
		// 热键注册部分取消
		su.cancelRegistrationHotkey();
		dispose();
		tools.dispose();
		jf2.dispose();
	}

	// 复制到剪切板
	public void copyClipImage() throws IOException {
		// 获得系统剪贴板
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// 这步不太懂
		Transferable trans = new Transferable() {
			@Override
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor)) {
					return saveImage;
				}
				throw new UnsupportedFlavorException(flavor);
			}

			@Override
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}
		};
		// 添加到剪切板
		clipboard.setContents(trans, null);
		cancelSshot();
	}

	// 窗口隐藏
	public void cancelSshot() {
		// 隐藏操作窗口
		ScreenShotMainGUI.isStartedSshot = false;
		// 热键注册部分取消
		su.cancelRegistrationHotkey();
		dispose();
		tools.dispose();
		// tt1.setText("");
		// jf2.setVisible(false);
		jf2.dispose();
	}

	// 功能
	public void drawImage(int drawFlag) {
		isdraw = true;
		// 0矩形，1椭圆，2圆，3线段，4直线，5马赛克,6圆角矩形,7添加文字
		Screenshot.drawFlag = drawFlag;
		if (Screenshot.drawFlag == 7) {
			isStartAddChar = true;
		} else {
			isStartAddChar = false;
		}
	}

	// 后退
	public void drawBackImage() {
		if (biList != null && biList.size() > 0) {
			// 删除最后一个
			biList.remove(biList.size() - 1);
			if (biList != null && biList.size() > 0) {
				// 把前一个替换上
				tempImage3 = biList.get(biList.size() - 1);
				tempImage2 = tempImage3;
				saveImage = tempImage2.getSubimage(xx, yy, ww, hh);
				// 画完框将此缓存图片显示在整个屏幕
				Screenshot.this.getGraphics().drawImage(tempImage3, 0, 0, Screenshot.this);
			}
		}

		if (toolsLocationList != null && toolsLocationList.size() > 0) {
			// 删除最后一个
			toolsLocationList.remove(toolsLocationList.size() - 1);
			if (toolsLocationList != null && toolsLocationList.size() > 0) {
				// 判断边界
				int[] coo = getToolsCoo(toolsLocationList.get(toolsLocationList.size() - 1)[0],
						toolsLocationList.get(toolsLocationList.size() - 1)[1]);
				tools.setLocation(coo[0], coo[1]);
			}
		}
	}

	// 判断工具栏边界,返回工具栏坐标
	public int[] getToolsCoo(int x, int y) {
		int[] coo = new int[2];
		// 添加工具栏边界判断
		// 超过x
		if ((x + tools.getWidth()) > maxW) {
			// 超过y
			if ((y + tools.getHeight()) > maxH) {
				coo[0] = maxW - tools.getWidth() - 2;
				coo[1] = maxH - tools.getHeight() + 4;
			} else {
				// 没超过y
				coo[0] = maxW - tools.getWidth();
				coo[1] = y + 4;
			}

		} else {
			// 没超过x但是超过y
			if ((y + tools.getHeight()) > maxH) {
				coo[0] = x + 4;
				coo[1] = maxH - tools.getHeight();
			} else {
				coo[0] = x;
				coo[1] = y;
			}
		}
		return coo;
	}

	// 简单处理画笔
	public Graphics2D getPerG(Graphics2D g) {

		return g;
	}

}
