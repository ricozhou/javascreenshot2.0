package screenshot;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class ScreenShotMainGUI extends JFrame implements ActionListener {
	// 定义热键标识，用于在设置多个热键时，在事件处理中区分用户按下的热键
	// 截图
	public static final int SSHOT_KEY_MARK = 0;
	// 退出程序
	public static final int EXIT_KEY_MARK = 3;
	// 退出当前截图
	public static final int EXIT_CURR_KEY_MARK = 4;
	// 撤销至上一步
	public static final int BACK_KEY_MARK = 5;
	// 保存
	public static final int SAVE_KEY_MARK = 1;
	// 复制到剪切板
	// public static final int COPY_KEY_MARK = 4;
	// 复制到剪切板并关闭截图
	public static final int CUT_KEY_MARK = 2;
	public JPanel jp1;
	public JButton button1, button2;
	public JFrame jf;
	public Screenshot accessibleScreenshot;
	public SshotUtils su = new SshotUtils();
	public static boolean fileExit = false;
	// 是否是刚启动设置(保证第一次启动设置的不同)
	public static boolean isJustSatrt = true;
	// 保证只能启动一个设置
	// public static boolean isOnlyOneSetup = true;
	// 是否打开了设置窗口
	public static boolean isOpenSetupJF = false;
	// 是否已经开始截图
	public static boolean isStartedSshot = false;
	// public SetupMsg sm;
	public SetupParams sp;
	public static String hotKey;
	// 所有的按键存入map
	public static Map<String, String> keyMap = new HashMap<String, String>();
	public static Map<String, String> keyMap2 = new HashMap<String, String>();

	// 用于控制实时设置生效
	public static SetupParams sspp = new SetupParams();
	public static SetupMsg ssmm = new SetupMsg();

	public ScreenShotMainGUI() {
		// 初始化
		this.jf = this;
		// 初始化主题
		initTheme();
		// 初始化配置
		initPro();
		// 初始化是否已启动
		initFirst();
		// 初始化界面
		init();
	}

	// 初始化主题
	private void initTheme() {
		// 国人牛逼主题，值得学习
		// 初始化字体
		InitGlobalFont(new Font("微软雅黑", Font.PLAIN, 13));
		// 设置本属性将改变窗口边框样式定义
		// 系统默认样式 osLookAndFeelDecorated
		// 强立体半透明 translucencyAppleLike
		// 弱立体感半透明 translucencySmallShadow
		// 普通不透明 generalNoTranslucencyShadow
		BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
		// 设置主题为BeautyEye
		try {
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 隐藏“设置”按钮
		UIManager.put("RootPane.setupButtonVisible", false);
		// 开启/关闭窗口在不活动时的半透明效果
		// 设置此开关量为false即表示关闭之，BeautyEye LNF中默认是true
		BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
		// 设置BeantuEye外观下JTabbedPane的左缩进
		// 改变InsetsUIResource参数的值即可实现
		UIManager.put("TabbedPane.tabAreaInsets", new javax.swing.plaf.InsetsUIResource(3, 20, 2, 20));
		//

		// 切换主题，此主题在圆形窗口有标题栏

		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 初始化，检查是否已启动，是否最小化托盘
	private void initFirst() {
		// 是否已启动，启动则退出
		if (!su.checkSocket(ssmm.getPort() != 0 ? ssmm.getPort() : 12345)) {
			// JOptionPane.showMessageDialog(null, "程序已启动！请勿重复启动！", "提示消息",
			// JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

	}

	// 设置最小化
	public void setJFMinSize() {
		// 是否最小化托盘
		try {
			// 若不支持托盘或者初始化失败,则提示
			if (!minimizeTray()) {
				JOptionPane.showMessageDialog(null, "系统不支持托盘！", "提示消息", JOptionPane.WARNING_MESSAGE);
				return;
			}
			dispose();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	// 初始化配置
	public void initPro() {
		// 初始化按键对照表
		su.initKeyMap();
		// 原理：首先判断exe所在目录是否存在隐藏的配置文件screentshotsetup.properties，
		// 如果存在，则读取此配置文件，如果不存在则生成一个默认的隐藏的配置文件
		// 实例化一个参数对象
		// 这个是用于设置具体参数
		// sm = new SetupMsg();
		// 这个是用于显示具体参数
		sp = new SetupParams();
		File f = new File("screentshotsetup.properties");
		// 判断是否存在文件
		fileExit = su.isFileExit(f);
		if (fileExit) {
			// 获取默认配置信息
			try {
				// 获取显示参数
				sp = su.getDefaultMsg(f);
				// ScreenShotMainGUI.sspp = sp;
				// 转化为使用参数
				ssmm = su.trunSm(sp);
			} catch (Exception e1) {
				ssmm = new SetupMsg();
				e1.printStackTrace();
			}
		}
	}

	// 主方法
	public static void main(String[] args) {
		// 线程启动截图主程序
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ScreenShotMainGUI();
			}
		});
	}

	// font
	private static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}

	// 初始化
	private void init() {
		jp1 = new JPanel();
		jp1.setLayout(null);

		button1 = new JButton("截图");
		button1.addActionListener(this);
		// 国人主题
		// button1.setBounds(1, 0, 100, 48);
		button1.setBounds(0, 0, 117, 46);
		jp1.add(button1);
		button1.setEnabled(true);

		ImageIcon sshotSet = new ImageIcon(getClass().getResource("setup.png"));
		button2 = new JButton(sshotSet);
		button2.addActionListener(this);
		// 国人主题
		// button2.setBounds(102, 0, 49, 47);
		button2.setBounds(117, 0, 47, 47);
		jp1.add(button2);
		button2.setEnabled(true);

		this.add(jp1);
		// 国人主题
		// this.setSize(162, 86);
		this.setSize(170, 74);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon i = new ImageIcon(getClass().getResource("screenshot.png"));
		this.setIconImage(i.getImage());

		// 取出sm中list，进行处理并返回第二个参数和第三个参数
		int[] sskm = su.proParamList(ssmm.getSshkList(), SSHOT_KEY_MARK);
		// int[] skm = su.proParamList(sm.getShkList(), SAVE_KEY_MARK);
		// int[] ckm = su.proParamList(sm.getChkList(), CUT_KEY_MARK);
		int[] ekm = su.proParamList(ssmm.getEhkList(), EXIT_KEY_MARK);
		// int[] eckm = su.proParamList(sm.getCchkList(), EXIT_CURR_KEY_MARK);
		// int[] bkm = su.proParamList(sm.getBhkList(), BACK_KEY_MARK);

		// 第一步：注册热键，第一个参数表示该热键的标识，第二个参数表示组合键，如果没有则为0，第三个参数为定义的主要热键
		// 截图
		JIntellitype.getInstance().registerHotKey(SSHOT_KEY_MARK, sskm[0], sskm[1]);
		// 保存
		// JIntellitype.getInstance().registerHotKey(SAVE_KEY_MARK, skm[0],
		// skm[1]);
		// 复制到剪切板并关闭截图
		// JIntellitype.getInstance().registerHotKey(CUT_KEY_MARK, ckm[0],
		// ckm[1]);
		// 退出程序
		JIntellitype.getInstance().registerHotKey(EXIT_KEY_MARK, ekm[0], ekm[1]);
		// 取消当前截图
		// JIntellitype.getInstance().registerHotKey(EXIT_CURR_KEY_MARK,
		// eckm[0], eckm[1]);
		// 撤销
		// JIntellitype.getInstance().registerHotKey(BACK_KEY_MARK, bkm[0],
		// bkm[1]);

		// JIntellitype.getInstance()
		// 第二步：添加热键监听器
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
			public void onHotKey(int markCode) {
				switch (markCode) {
				case SSHOT_KEY_MARK:
					if (ScreenShotMainGUI.isOpenSetupJF == true) {
						// 设置界面打开时，禁止截图程序
						su.proKeyBeforeSshot(SSHOT_KEY_MARK, ssmm);
						return;
					}
					startScreenShot();
					break;
				case EXIT_KEY_MARK:
					if (ScreenShotMainGUI.isOpenSetupJF == true) {
						// 设置界面打开时，禁止截图程序
						su.proKeyBeforeSshot(EXIT_KEY_MARK, ssmm);
						return;
					}
					System.exit(0);
					break;
				case EXIT_CURR_KEY_MARK:
					if (ScreenShotMainGUI.isOpenSetupJF == true) {
						// 设置界面打开时，禁止截图程序
						su.proKeyBeforeSshot(EXIT_CURR_KEY_MARK, ssmm);
						return;
					}
					exitCurrScreenShot();
					break;
				case SAVE_KEY_MARK:
					if (ScreenShotMainGUI.isOpenSetupJF == true) {
						// 设置界面打开时，禁止截图程序
						su.proKeyBeforeSshot(SAVE_KEY_MARK, ssmm);
						return;
					}
					saveCurrSshot();
					break;
				case CUT_KEY_MARK:
					if (ScreenShotMainGUI.isOpenSetupJF == true) {
						// 设置界面打开时，禁止截图程序
						su.proKeyBeforeSshot(CUT_KEY_MARK, ssmm);
						return;
					}
					cutCurrSshot();
					break;
				case BACK_KEY_MARK:
					if (ScreenShotMainGUI.isOpenSetupJF == true) {
						// 设置界面打开时，禁止截图程序
						su.proKeyBeforeSshot(BACK_KEY_MARK, ssmm);
						return;
					}
					backSshot();
					break;
				}
			}
		});

		// 监听界面关闭事件
		this.addWindowListener(new WindowAdapter() {
			// 当最小化时
			public void windowIconified(WindowEvent e) {
				setJFMinSize();
			}
		});

		// 是否最小化托盘
		if (ssmm.isStartMinSize()) {
			setJFMinSize();
		}

		// 置顶
		// this.setAlwaysOnTop(true);

	}

	// 撤销
	protected void backSshot() {
		if (accessibleScreenshot != null && ScreenShotMainGUI.isStartedSshot && ssmm.isCallBack()) {
			try {
				accessibleScreenshot.drawBackImage();
				// exitCurrScreenShot();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "撤销失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// 复制
	protected void cutCurrSshot() {
		if (accessibleScreenshot != null && ScreenShotMainGUI.isStartedSshot) {
			try {
				accessibleScreenshot.copyClipImage();
				exitCurrScreenShot();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "复制失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// 保存当前截图
	protected void saveCurrSshot() {
		if (accessibleScreenshot != null && ScreenShotMainGUI.isStartedSshot) {
			try {
				accessibleScreenshot.saveImage();
				exitCurrScreenShot();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "保存失败！", "提示消息", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// 退出当前截图
	public void exitCurrScreenShot() {
		if (accessibleScreenshot != null && ScreenShotMainGUI.isStartedSshot) {
			accessibleScreenshot.cancelSshot();
			if (accessibleScreenshot.tools != null) {
				accessibleScreenshot.tools.dispose();
			}
		}
	}

	// 最小化系统托盘
	protected boolean minimizeTray() throws AWTException {
		MinimizeTrayJPanel mtj = new MinimizeTrayJPanel(this);
		return mtj.init();
	}

	// 开始截图
	public void startScreenShot() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 启动截图前，保存，复制，撤销，退出当前截图快捷键需要注册，截图停止这几个快捷键取消注册，主要目的是尽量避免热键冲突，可以使用常用的热键
					su.registrationHotkey(ssmm);
					// 先判断是否存在当前截图
					// 有则取消
					exitCurrScreenShot();
					accessibleScreenshot = new Screenshot(jf, false, ssmm);
					ScreenShotMainGUI.isStartedSshot = true;
					accessibleScreenshot.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 监听截图按钮
		if (e.getSource().equals(button1)) {
			startScreenShot();
		}
		// 监听设置按钮
		if (e.getSource().equals(button2)) {
			// false表示已打开，true才可以打开
			if (!ScreenShotMainGUI.isOpenSetupJF) {
				ScreenShotMainGUI.isOpenSetupJF = true;
				SetupJF sjp = new SetupJF();
			}
		}
	}

}
