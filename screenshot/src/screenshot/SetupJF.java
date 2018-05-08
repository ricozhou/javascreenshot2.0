package screenshot;

import java.awt.AWTException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class SetupJF extends JFrame implements ActionListener {
	public JPanel jp1, jp2;
	public JButton button1, button2, button3, button4, button5, button6, button7;
	public JLabel jlb1, jlb2, jlb3, jlb4, jlb5, jlb6, jlb7, jlb8, jlb9, jlb10, jlb11, jlb12, jlb13, jlb14, jlb141,
			jlb15, jlb16, jlb17, jlb18, jlb19, jlb20, jlb21, jlb22, jlb23;
	public JRadioButton jrb1, jrb2;
	public ButtonGroup bg1;
	public JFrame jf;
	public JTextField tt1, tt2, tt3, tt4, tt5, tt6, tt61;
	private JComboBox jcb1, jcb2, jcb3, jcb4, jcb5, jcb6;
	public JCheckBox jcbox1, jcbox2, jcbox3, jcbox4, jcbox5, jcbox6, jcbox7, jcbox8, jcbox9, jcbox10, jcbox11, jcbox12,
			jcbox13, jcbox14, jcbox15, jcbox16, jcbox17, jcbox18, jcbox19, jcbox20, jcbox21, jcbox22, jcbox23, jcbox24;
	public List<JCheckBox> ljList = new ArrayList<JCheckBox>();
	public SetupParams sp;
	// 光标在的时候标志
	public boolean isOnFocus = false;
	public static boolean fileExit2 = false;
	// 将按键存入set集合
	public static Set<String> keyList = new HashSet<String>();
	public SshotUtils su = new SshotUtils();
	public SetupMsg sm;
	public SetupUtils sus = new SetupUtils();
	public JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);

	public SetupJF() {
		this.jf = this;
		initPro();
		// 初始化
		init();
	}

	// 初始化配置
	public void initPro() {
		// 初始化按键对照表
		// 原理：首先判断exe所在目录是否存在隐藏的配置文件screentshotsetup.properties，
		// 如果存在，则读取此配置文件，如果不存在则生成一个默认的隐藏的配置文件
		// 实例化一个参数对象
		// 这个是用于设置具体参数
		sm = new SetupMsg();
		// 这个是用于显示具体参数
		sp = new SetupParams();
		File f = new File("screentshotsetup.properties");
		// 判断是否存在文件
		fileExit2 = su.isFileExit(f);
		if (fileExit2) {
			// 获取默认配置信息
			try {
				// 获取显示参数
				sp = su.getDefaultMsg(f);
				// 转化为使用参数
				sm = su.trunSm(sp);
			} catch (Exception e1) {
				sm = new SetupMsg();
				e1.printStackTrace();
			}
		}
	}

	private void init() {
		// 初始化面板
		jp1 = new JPanel();
		jp1.setLayout(null);
		jp2 = new JPanel();
		jp2.setLayout(null);

		// 基本设置
		jlb1 = new JLabel("基本设置：");
		jlb1.setBounds(10, 10, 80, 25);
		jp1.add(jlb1);

		jlb3 = new JLabel("保存设置：");
		jlb3.setBounds(37, 40, 80, 25);
		jp1.add(jlb3);

		tt1 = new JTextField();
		tt1.setBounds(107, 70, 252, 25);
		jp1.add(tt1);
		tt1.setEditable(false);

		jrb1 = new JRadioButton("自定义保存");
		jrb2 = new JRadioButton("默认路径保存");
		// 如果文件不存在，其默认值是false，需要手动改成自定义保存
		if (!ScreenShotMainGUI.fileExit) {
			jrb1.setSelected(true);
			jrb2.setSelected(false);
			tt1.setVisible(false);
			tt1.setText("");
		} else {
			jrb1.setSelected(sp.isCustomizeSave());
			jrb2.setSelected(!sp.isCustomizeSave());
		}
		if (jrb2.isSelected()) {
			tt1.setVisible(true);
			tt1.setText(sp.getCustomSavePath() != null ? sp.getCustomSavePath() : "C://");
		} else {
			tt1.setVisible(false);
			tt1.setText("");
		}

		jrb1.setBounds(100, 40, 100, 25);
		jrb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb1.isSelected()) {
					tt1.setVisible(false);
					tt1.setText("");
				} else {

				}
			}
		});
		jp1.add(jrb1);

		jrb2.setBounds(250, 40, 130, 25);
		jrb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jrb2.isSelected()) {
					if (!ScreenShotMainGUI.fileExit) {
						// 打开文件夹
						// 接收文件
						JFileChooser chooser = new JFileChooser();
						// 设定只能选择到文件夹
						chooser.setFileSelectionMode(1);
						chooser.setDialogTitle("选择默认保存路径");
						// 默认文件名称还有放在当前目录下
						int s = chooser.showDialog(jf, "保存");
						if (s == 1) {
							if (!tt1.isVisible()) {
								jrb1.setSelected(true);
								jrb2.setSelected(false);
								tt1.setVisible(false);
								tt1.setText("");
							} else {

							}
							return;
						} else {
							// 保存路径
							String saveFilePath = chooser.getSelectedFile().toString();
							tt1.setText(saveFilePath);
							tt1.setVisible(true);
						}
					} else {
						if (sp.isCustomizeSave) {
							// 打开文件夹
							// 接收文件
							JFileChooser chooser = new JFileChooser();
							// 设定只能选择到文件夹
							chooser.setFileSelectionMode(1);
							chooser.setDialogTitle("选择默认保存路径");
							// 默认文件名称还有放在当前目录下
							int s = chooser.showDialog(jf, "保存");
							if (s == 1) {
								if (!tt1.isVisible()) {
									jrb1.setSelected(true);
									jrb2.setSelected(false);
									tt1.setVisible(false);
									tt1.setText("");
								} else {

								}
								return;
							} else {
								// 保存路径
								String saveFilePath = chooser.getSelectedFile().toString();
								tt1.setText(saveFilePath);
								tt1.setVisible(true);
							}
						} else {
							if (ScreenShotMainGUI.isJustSatrt == true) {
								// 如果是首次启动
								tt1.setVisible(true);
								tt1.setText(sp.getCustomSavePath() != null ? sp.getCustomSavePath() : "C://");
								// 改为不是首次启动
								ScreenShotMainGUI.isJustSatrt = false;
							} else {
								// 打开文件夹
								// 接收文件
								JFileChooser chooser = new JFileChooser();
								// 设定只能选择到文件夹
								chooser.setFileSelectionMode(1);
								chooser.setDialogTitle("选择默认保存路径");
								// 默认文件名称还有放在当前目录下
								int s = chooser.showDialog(jf, "保存");
								if (s == 1) {
									if (!tt1.isVisible()) {
										jrb1.setSelected(true);
										jrb2.setSelected(false);
										tt1.setVisible(false);
										tt1.setText("");
									} else {

									}
									return;
								} else {
									// 保存路径
									String saveFilePath = chooser.getSelectedFile().toString();
									tt1.setText(saveFilePath);
									tt1.setVisible(true);
								}
							}
						}

					}

				} else {
					tt1.setVisible(false);
					tt1.setText("");
				}
			}
		});
		jp1.add(jrb2);
		bg1 = new ButtonGroup();
		bg1.add(jrb1);
		bg1.add(jrb2);

		jlb4 = new JLabel("画笔设置：");
		jlb4.setBounds(37, 100, 80, 25);
		jp1.add(jlb4);

		jlb5 = new JLabel("颜色：");
		jlb5.setBounds(108, 100, 50, 25);
		jp1.add(jlb5);

		String[] color = { "红色", "蓝色", "黑色", "黄色", "橙色", "绿色" };
		jcb1 = new JComboBox(color);
		// 设置默认显示值
		jcb1.setSelectedIndex(sp.getgColorP());
		jcb1.setBounds(153, 100, 70, 25);
		jp1.add(jcb1);

		jlb6 = new JLabel("粗   细：");
		jlb6.setBounds(230, 100, 55, 25);
		jp1.add(jlb6);

		String[] gSize = { "1", "2", "3", "4", "5" };
		jcb2 = new JComboBox(gSize);
		// 设置默认显示值
		jcb2.setSelectedIndex(sp.getgSizeP());
		jcb2.setBounds(288, 100, 70, 25);
		jp1.add(jcb2);

		jlb7 = new JLabel("图片设置：");
		jlb7.setBounds(37, 130, 80, 25);
		jp1.add(jlb7);

		jlb8 = new JLabel("格式：");
		jlb8.setBounds(108, 130, 50, 25);
		jp1.add(jlb8);

		String[] format = { ".png", ".jpg", ".bmp", ".jpeg", ".gif" };
		jcb3 = new JComboBox(format);
		// 设置默认显示值
		jcb3.setSelectedIndex(sp.getImgFormatP());
		jcb3.setBounds(153, 130, 70, 25);
		jp1.add(jcb3);

		jlb9 = new JLabel("清晰度：");
		jlb9.setBounds(230, 130, 60, 25);
		jp1.add(jlb9);

		String[] definition = { "一般", "高清", "超清" };
		jcb4 = new JComboBox(definition);
		// 设置默认显示值
		jcb4.setSelectedIndex(sp.getImgSharpness());
		jcb4.setBounds(288, 130, 70, 25);
		jp1.add(jcb4);

		jlb15 = new JLabel("其他设置：");
		jlb15.setBounds(37, 160, 80, 25);
		jp1.add(jlb15);

		jcbox1 = new JCheckBox("开机自启动", false);
		jcbox1.setBounds(102, 160, 100, 25);
		jp1.add(jcbox1);
		jcbox1.setSelected(sp.isSelfStart());

		jcbox2 = new JCheckBox(" 启动最小化", false);
		jcbox2.setBounds(256, 160, 110, 25);
		jp1.add(jcbox2);
		jcbox2.setSelected(sp.isStartMinSize());

		// jcbox3 = new JCheckBox(" 注册成服务", false);
		// jcbox3.setBounds(108, 160, 50, 25);
		// jp1.add(jcbox3);

		// 快捷设置
		jlb2 = new JLabel("快捷设置：");
		jlb2.setBounds(10, 200, 80, 25);
		jp1.add(jlb2);

		jlb10 = new JLabel("截图设置：");
		jlb10.setBounds(37, 230, 80, 25);
		jp1.add(jlb10);

		tt2 = new JTextField();
		tt2.setBounds(107, 230, 252, 25);
		tt2.setText(sp.getsShotHotKey() != null ? sp.getsShotHotKey() : "Ctrl + Alt + A");
		jp1.add(tt2);
		// 文本框加入键盘监听
		tt2.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyPressedCode = e.getKeyCode();
				sus.keyPressedUtils(keyPressedCode, tt2);
			}
		});

		tt2.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int keyReleased = e.getKeyCode();
				keyReleasedUtils(keyReleased, tt2);
			}
		});

		// tt2.setEditable(false);

		jlb11 = new JLabel("保存设置：");
		jlb11.setBounds(37, 260, 80, 25);
		jp1.add(jlb11);

		tt3 = new JTextField();
		tt3.setText(sp.getSaveHotKey() != null ? sp.getSaveHotKey() : "Ctrl + S");
		tt3.setBounds(107, 260, 252, 25);
		jp1.add(tt3);
		// 文本框加入键盘监听
		tt3.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyPressedCode = e.getKeyCode();
				sus.keyPressedUtils(keyPressedCode, tt3);
			}
		});

		tt3.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int keyReleased = e.getKeyCode();
				keyReleasedUtils(keyReleased, tt3);
			}
		});

		jlb12 = new JLabel("复制设置：");
		jlb12.setBounds(37, 290, 80, 25);
		jp1.add(jlb12);

		tt4 = new JTextField();
		tt4.setText(sp.getCopyHotKey() != null ? sp.getCopyHotKey() : "Ctrl + C");
		tt4.setBounds(107, 290, 252, 25);
		jp1.add(tt4);
		// 文本框加入键盘监听
		tt4.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyPressedCode = e.getKeyCode();
				sus.keyPressedUtils(keyPressedCode, tt4);
			}
		});

		tt4.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int keyReleased = e.getKeyCode();
				keyReleasedUtils(keyReleased, tt4);
			}
		});

		jlb13 = new JLabel("退出设置：");
		jlb13.setBounds(37, 320, 80, 25);
		jp1.add(jlb13);

		tt5 = new JTextField();
		tt5.setText(sp.getExitHotKey() != null ? sp.getExitHotKey() : "Ctrl + Alt + Q");
		tt5.setBounds(107, 320, 252, 25);
		jp1.add(tt5);
		// 文本框加入键盘监听
		tt5.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyPressedCode = e.getKeyCode();
				sus.keyPressedUtils(keyPressedCode, tt5);
			}
		});

		tt5.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int keyReleased = e.getKeyCode();
				keyReleasedUtils(keyReleased, tt5);
			}
		});

		jlb14 = new JLabel("取消设置：");
		jlb14.setBounds(37, 350, 80, 25);
		jp1.add(jlb14);

		tt6 = new JTextField();
		tt6.setText(sp.getCancelHotKey() != null ? sp.getCancelHotKey() : "Ctrl + Q");
		tt6.setBounds(107, 350, 252, 25);
		jp1.add(tt6);
		// 文本框加入键盘监听
		tt6.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyPressedCode = e.getKeyCode();
				sus.keyPressedUtils(keyPressedCode, tt6);
			}
		});

		tt6.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int keyReleased = e.getKeyCode();
				keyReleasedUtils(keyReleased, tt6);
			}
		});

		jlb141 = new JLabel("撤销设置：");
		jlb141.setBounds(37, 380, 80, 25);
		jp1.add(jlb141);

		tt61 = new JTextField();
		tt61.setText(sp.getBackHotKey() != null ? sp.getBackHotKey() : "Ctrl + Z");
		tt61.setBounds(107, 380, 252, 25);
		jp1.add(tt61);
		// 文本框加入键盘监听
		tt61.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyPressedCode = e.getKeyCode();
				sus.keyPressedUtils(keyPressedCode, tt61);
			}
		});

		tt61.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int keyReleased = e.getKeyCode();
				keyReleasedUtils(keyReleased, tt61);
			}
		});

		button1 = new JButton("确定");
		button1.setBounds(50, 430, 60, 25);
		jp1.add(button1);
		button1.addActionListener(this);

		button2 = new JButton("关于");
		button2.setBounds(170, 430, 60, 25);
		jp1.add(button2);
		button2.addActionListener(this);

		button3 = new JButton("取消");
		button3.setBounds(290, 430, 60, 25);
		jp1.add(button3);
		button3.addActionListener(this);

		// button4 = new JButton("应用");
		// button4.setBounds(290, 430, 60, 25);
		// jp1.add(button4);
		// button4.addActionListener(this);

		// 高级设置

		// 功能设置
		jlb16 = new JLabel("功能设置：");
		jlb16.setBounds(10, 10, 80, 25);
		jp2.add(jlb16);

		jlb17 = new JLabel("功能添加：");
		jlb17.setBounds(37, 40, 80, 25);
		jp2.add(jlb17);

		// 截图带光标
		jcbox3 = new JCheckBox("截图带光标", false);
		jcbox3.setBounds(102, 40, 100, 25);
		jp2.add(jcbox3);
		ljList.add(jcbox3);
		jcbox3.setSelected(sp.isGetCursor());

		// 添加放大镜
		jcbox4 = new JCheckBox("显示放大镜", false);
		jcbox4.setBounds(256, 40, 110, 25);
		jp2.add(jcbox4);
		ljList.add(jcbox4);
		jcbox4.setSelected(sp.isShowMagnifier());

		// 显示像素
		jcbox5 = new JCheckBox("显示像素", false);
		jcbox5.setBounds(102, 70, 100, 25);
		jp2.add(jcbox5);
		ljList.add(jcbox5);
		jcbox5.setSelected(sp.isShowPixels());

		// 添加文字
		jcbox6 = new JCheckBox("添加文字", false);
		jcbox6.setBounds(256, 70, 110, 25);
		jp2.add(jcbox6);
		ljList.add(jcbox6);
		jcbox6.setSelected(sp.isAddChar());

		// 椭圆标注
		jcbox7 = new JCheckBox("椭圆标注", false);
		jcbox7.setBounds(102, 100, 100, 25);
		jp2.add(jcbox7);
		ljList.add(jcbox7);
		jcbox7.setSelected(sp.isOvalCallout());

		// 马赛克
		jcbox8 = new JCheckBox("马赛克功能", false);
		jcbox8.setBounds(256, 100, 110, 25);
		jp2.add(jcbox8);
		ljList.add(jcbox8);
		jcbox8.setSelected(sp.isMosaic());

		// 撤销功能
		jcbox9 = new JCheckBox("撤销功能", false);
		jcbox9.setBounds(102, 130, 100, 25);
		jp2.add(jcbox9);
		ljList.add(jcbox9);
		jcbox9.setSelected(sp.isCallBack());

		// 画直线
		jcbox10 = new JCheckBox("画直线", false);
		jcbox10.setBounds(256, 130, 110, 25);
		jp2.add(jcbox10);
		ljList.add(jcbox10);
		jcbox10.setSelected(sp.isLineCallout());

		// 圆形标注
		jcbox11 = new JCheckBox("圆形标注", false);
		jcbox11.setBounds(102, 160, 100, 25);
		jp2.add(jcbox11);
		ljList.add(jcbox11);
		jcbox11.setSelected(sp.isCirCallout());

		// 分享
		jcbox12 = new JCheckBox("分享功能", false);
		jcbox12.setBounds(256, 160, 110, 25);
		jp2.add(jcbox12);
		ljList.add(jcbox12);
		jcbox12.setSelected(sp.isShare());

		// 圆角矩形标注
		jcbox13 = new JCheckBox("圆角矩形标注", false);
		jcbox13.setBounds(102, 190, 130, 25);
		jp2.add(jcbox13);
		ljList.add(jcbox13);
		jcbox13.setSelected(sp.isRoundedRectangleCallout());

		// 矩形标注
		jcbox14 = new JCheckBox("矩形标注", false);
		jcbox14.setBounds(256, 190, 110, 25);
		jp2.add(jcbox14);
		ljList.add(jcbox14);
		jcbox14.setSelected(sp.isRectangleCallout());

		// 画曲线
		jcbox15 = new JCheckBox("画曲线", false);
		jcbox15.setBounds(102, 220, 100, 25);
		jp2.add(jcbox15);
		ljList.add(jcbox15);
		jcbox15.setSelected(sp.isCurveCallout());

		// 显示坐标轴
		jcbox16 = new JCheckBox("显示坐标轴", false);
		jcbox16.setBounds(256, 220, 110, 25);
		jp2.add(jcbox16);
		ljList.add(jcbox16);
		jcbox16.setSelected(sp.isShowAxis());

		// 是否全选
		jcbox17 = new JCheckBox("全选", false);
		jcbox17.setBounds(256, 250, 110, 25);
		jp2.add(jcbox17);
		// jcbox8.setSelected(sp.isStartMinSize());
		// 如果都选了则全选按钮也选中，其他情况都不选
		if (allSelected()) {
			jcbox17.setSelected(true);
		} else {
			jcbox17.setSelected(false);
		}

		jcbox17.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 监听是否全选
				if (jcbox17.isSelected()) {
					// 如果全选
					for (JCheckBox jcb : ljList) {
						jcb.setSelected(true);
					}
				} else {
					for (JCheckBox jcb : ljList) {
						jcb.setSelected(false);
					}
				}
			}
		});

		// 其他设置
		jlb18 = new JLabel("其他设置：");
		jlb18.setBounds(10, 290, 80, 25);
		jp2.add(jlb18);

		jlb19 = new JLabel("鼠标左击：");
		jlb19.setBounds(37, 320, 80, 25);
		jp2.add(jlb19);

		jlb20 = new JLabel("单击：");
		jlb20.setBounds(108, 320, 50, 25);
		jp2.add(jlb20);

		String[] leftClick = { "不设", "取消", "保存", "复制", "撤销", "退出" };
		jcb5 = new JComboBox(leftClick);
		// 设置默认显示值
		jcb5.setSelectedIndex(sp.getMouseSCFeature());
		jcb5.setBounds(153, 320, 70, 25);
		jp2.add(jcb5);

		jlb21 = new JLabel("双击：");
		jlb21.setBounds(240, 320, 55, 25);
		jp2.add(jlb21);

		String[] doubleClick = { "不设", "取消", "保存", "复制", "撤销", "退出" };
		jcb6 = new JComboBox(doubleClick);
		// 设置默认显示值
		jcb6.setSelectedIndex(sp.getMouseDCFeature());
		jcb6.setBounds(288, 320, 70, 25);
		jp2.add(jcb6);

		jlb22 = new JLabel("右击菜单：");
		jlb22.setBounds(37, 350, 80, 25);
		jp2.add(jlb22);

		// 取消
		jcbox19 = new JCheckBox("取消", false);
		jcbox19.setBounds(102, 350, 60, 25);
		jp2.add(jcbox19);
		jcbox19.setSelected(sp.isAddRCCancel());

		// 保存
		jcbox20 = new JCheckBox("保存", false);
		jcbox20.setBounds(179, 350, 60, 25);
		jp2.add(jcbox20);
		jcbox20.setSelected(sp.isAddRCSave());

		// 复制
		jcbox21 = new JCheckBox("复制", false);
		jcbox21.setBounds(256, 350, 60, 25);
		jp2.add(jcbox21);
		jcbox21.setSelected(sp.isAddRCCopy());

		// 撤销
		jcbox22 = new JCheckBox("撤销", false);
		jcbox22.setBounds(102, 380, 60, 25);
		jp2.add(jcbox22);
		jcbox22.setSelected(sp.isAddRCCallBack());

		// 分享
		jcbox23 = new JCheckBox("分享", false);
		jcbox23.setBounds(179, 380, 60, 25);
		jp2.add(jcbox23);
		jcbox23.setSelected(sp.isAddRCShare());

		// 退出
		jcbox24 = new JCheckBox("退出", false);
		jcbox24.setBounds(256, 380, 60, 25);
		jp2.add(jcbox24);
		jcbox24.setSelected(sp.isAddRCExit());

		jlb23 = new JLabel("注册服务：");
		jlb23.setBounds(37, 410, 80, 25);
		jp2.add(jlb23);

		// 是否注册成服务
		jcbox18 = new JCheckBox("是否注册成服务", false);
		jcbox18.setBounds(102, 410, 150, 25);
		jp2.add(jcbox18);
		jcbox18.setSelected(sp.isRegistrationService());

		// button5 = new JButton("确定");
		// button5.setBounds(50, 430, 60, 25);
		// jp2.add(button5);
		// button5.addActionListener(this);
		//
		// button6 = new JButton("取消");
		// button6.setBounds(170, 430, 60, 25);
		// jp2.add(button6);
		// button6.addActionListener(this);
		//
		// button7 = new JButton("应用");
		// button7.setBounds(290, 430, 60, 25);
		// jp2.add(button7);
		// button7.addActionListener(this);

		jtp.add("基础设置", jp1);
		jtp.add("高级设置", jp2);
		this.add(jtp);
		this.setTitle("截图设置");
		this.setSize(400, 550);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("setup.png"));
		this.setIconImage(imageIcon.getImage());

		// 监听界面关闭事件
		this.addWindowListener(new WindowAdapter() {
			// 当关闭时
			public void windowClosing(WindowEvent e) {
				// 更改状态
				ScreenShotMainGUI.isOpenSetupJF = false;
			}
		});

	}

	// 是否全选了已经
	private boolean allSelected() {
		for (JCheckBox jcb : ljList) {
			if (!jcb.isSelected()) {
				// 只要有一个没选就false
				return false;
			}
		}
		return true;
	}

	// 键盘松开事件监听
	protected void keyReleasedUtils(int keyReleased, JTextField tt) {

		// 每次判断list个数，大于1则是组合键，小于等于1是单个按键
		if (keyList != null && keyList.size() > 1) {
			// 组合键
			// 将按键挨个取出判断是什么键
			// for (String key : keyList) {
			// System.out.println(key);
			// }
			// 判断是否只是包含控制键
			if (sus.onlyInSCA(keyList) || !sus.onlyOneNumOrAlp(keyList)) {
				tt.setText("");
				JOptionPane.showMessageDialog(null, "必须包含且只包含一个字母键或者数字键！如：Shift+Ctrl+A！", "提示消息",
						JOptionPane.WARNING_MESSAGE);
			} else {
				// 识别好之后显示在界面
				tt.setText(sus.getShowKey(keyList));
			}
			keyList.clear();
		} else if (keyList.size() == 1) {
			tt.setText("");
			// 只按住了一个按键
			// 不允许单个按键作为快捷键，防止热键冲突
			JOptionPane.showMessageDialog(null, "请设置组合键！如：Shift+Ctrl+A！", "提示消息", JOptionPane.WARNING_MESSAGE);
			// 松开按键后将松开的按键从list删除
			sus.deleteKey(keyReleased);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 监听确定保存按钮
		if (e.getSource().equals(button1)) {
			// 获取所有的参数
			SetupParams spp = getParams();
			// 检查参数
			int flag = checkParams(spp);
			if (flag == 0) {
				// 判断是否开机自启动，如果是则将快捷文件写入启动文件夹，不是的话则删除启动文件夹
				// 程序自动先创建一个快捷方式

				// 获取本exe程序的名字
				String appName = sus.getExeAppName();
				String lnk = appName + ".lnk";
				// 如果开机自启动
				if (spp.isSelfStart()) {
					// 如果同级目录没有快捷方式
					File f = new File(lnk);
					if (!f.exists()) {
						// 不存在则创建
						if (!sus.createLnk(appName)) {
							// 创建失败则继续
							spp.setSelfStart(false);
							jcbox1.setSelected(false);
							JOptionPane.showMessageDialog(null, "创建快捷方式失败！请手动创建并重试！", "提示消息",
									JOptionPane.WARNING_MESSAGE);
						} else {
							// 创建成功则设置
							if (!sus.setAutoStart(spp.isSelfStart(), lnk)) {
								spp.setSelfStart(false);
								jcbox1.setSelected(false);
								JOptionPane.showMessageDialog(null, "设置开机自启动失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
							}
						}
					} else {
						// 存在则设置
						if (!sus.setAutoStart(spp.isSelfStart(), lnk)) {
							spp.setSelfStart(false);
							jcbox1.setSelected(false);
							JOptionPane.showMessageDialog(null, "设置开机自启动失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
						}
					}

				} else {
					// 不启动则取消
					if (!sus.setAutoStart(spp.isSelfStart(), lnk)) {
						spp.setSelfStart(true);
						jcbox1.setSelected(true);
						JOptionPane.showMessageDialog(null, "取消开机自启动失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
					}
				}

				// 直接写入文件
				// 存入配置文件
				try {
					if (su.setupMsgToPro(spp)) {
						// 保存成功之后再附给静态变量
						// ScreenShotMainGUI.sspp = spp;
						// 转化为使用参数
						ScreenShotMainGUI.ssmm = su.trunSm(spp);
						// 重新注册热键（主要是截图和退出热键）
						// 需要先注册部分快捷键，方便不需要重启即可生效
						// 开始截图和退出程序需要立即注册
						// 先解除原有注册
						su.cancelOneRegistrationHotkey(ScreenShotMainGUI.SSHOT_KEY_MARK);
						su.cancelOneRegistrationHotkey(ScreenShotMainGUI.EXIT_KEY_MARK);
						// 重新注册
						int[] sskm = su.proParamList(ScreenShotMainGUI.ssmm.getSshkList(),
								ScreenShotMainGUI.SSHOT_KEY_MARK);
						int[] ekm = su.proParamList(ScreenShotMainGUI.ssmm.getEhkList(),
								ScreenShotMainGUI.EXIT_KEY_MARK);
						su.registrationOneHotkey(ScreenShotMainGUI.SSHOT_KEY_MARK, sskm);
						su.registrationOneHotkey(ScreenShotMainGUI.EXIT_KEY_MARK, ekm);

						JOptionPane.showMessageDialog(null, "保存设置成功！", "提示消息", JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "保存设置失败！请重试！", "提示消息", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "保存设置失败！请重试！", "提示消息", JOptionPane.WARNING_MESSAGE);
					e1.printStackTrace();
				}

				// 关闭设置界面
				ScreenShotMainGUI.isOpenSetupJF = false;
				dispose();
			} else if (flag == 1) {
				JOptionPane.showMessageDialog(null, "请设置快捷键！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		}

		// 监听关于按钮
		if (e.getSource().equals(button2)) {
			String about = " 1. 默认快捷键：截图：Ctrl+Alt+A，保存：Ctrl+S，复制：Ctrl+C，退出：Ctrl+Alt+Q，取消：Ctrl+Q，撤销：Ctrl+Z。\r\n 2. 设置快捷键使用组合键，最少两个键且不息包含字母键或数字键，如：Shift+Ctrl+A，避免热键冲突。\r\n 3. 清晰度设置尚未实现。\r\n 4. 若默认保存路径不存在，则自动保存在C盘根目录。\r\n 5. 本程序依赖Java，若没有Java环境则复制jre文件夹并改名为“jre”，复制到此程序同级目录即可。";
			JOptionPane.showMessageDialog(null, about, "提示消息", JOptionPane.WARNING_MESSAGE);
		}

		// 监听取消按钮
		if (e.getSource().equals(button3)) {
			ScreenShotMainGUI.isOpenSetupJF = false;
			dispose();
		}
	}

	// 获取参数
	private SetupParams getParams() {
		SetupParams spp = new SetupParams();
		// 获取基本设置
		// 保存设置
		spp.setCustomizeSave(jrb1.isSelected());
		spp.setCustomSavePath(tt1.getText().replaceAll("\\\\", "\\\\\\\\"));

		// 画笔设置
		// 颜色
		spp.setgColorP(jcb1.getSelectedIndex());
		// 粗细
		spp.setgSizeP(jcb2.getSelectedIndex());

		// 图片设置
		// 格式
		spp.setImgFormatP(jcb3.getSelectedIndex());
		// 清晰度
		spp.setImgSharpness(jcb4.getSelectedIndex());
		// 是否自启动
		spp.setSelfStart(jcbox1.isSelected());
		// 是否启动最小化
		spp.setStartMinSize(jcbox2.isSelected());

		// 快捷设置
		// 截图
		spp.setsShotHotKey(tt2.getText().trim());
		// 保存
		spp.setSaveHotKey(tt3.getText().trim());
		// 复制
		spp.setCopyHotKey(tt4.getText().trim());
		// 退出
		spp.setExitHotKey(tt5.getText().trim());
		// 取消
		spp.setCancelHotKey(tt6.getText().trim());
		// 撤销
		spp.setBackHotKey(tt61.getText().trim());
		// 端口
		spp.setPort(String.valueOf(sm.getPort()));

		// 高级设置
		spp.setGetCursor(jcbox3.isSelected());
		spp.setShowMagnifier(jcbox4.isSelected());
		spp.setShowPixels(jcbox5.isSelected());
		spp.setAddChar(jcbox6.isSelected());
		spp.setOvalCallout(jcbox7.isSelected());
		spp.setMosaic(jcbox8.isSelected());
		spp.setCallBack(jcbox9.isSelected());
		spp.setLineCallout(jcbox10.isSelected());
		spp.setCirCallout(jcbox11.isSelected());
		spp.setShare(jcbox12.isSelected());
		spp.setRoundedRectangleCallout(jcbox13.isSelected());
		spp.setRectangleCallout(jcbox14.isSelected());
		spp.setCurveCallout(jcbox15.isSelected());
		spp.setShowAxis(jcbox16.isSelected());
		// 注册服务
		spp.setRegistrationService(jcbox18.isSelected());

		// 鼠标功能设置
		// 左击
		spp.setMouseSCFeature(jcb5.getSelectedIndex());
		spp.setMouseDCFeature(jcb6.getSelectedIndex());

		// 右击
		spp.setAddRCCancel(jcbox19.isSelected());
		spp.setAddRCSave(jcbox20.isSelected());
		spp.setAddRCCopy(jcbox21.isSelected());
		spp.setAddRCCallBack(jcbox22.isSelected());
		spp.setAddRCShare(jcbox23.isSelected());
		spp.setAddRCExit(jcbox24.isSelected());

		return spp;
	}

	// 校验参数
	private int checkParams(SetupParams spp) {
		if ("".equals(spp.getsShotHotKey()) || "".equals(spp.getSaveHotKey()) || "".equals(spp.getCopyHotKey())
				|| "".equals(spp.getExitHotKey()) || "".equals(spp.getCancelHotKey())
				|| "".equals(spp.getBackHotKey())) {
			return 1;

		}
		return 0;
	}

}
