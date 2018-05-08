package screenshot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.melloware.jintellitype.JIntellitype;

public class SshotUtils {
	// 通过实例化占用一个serverSocket端口来判断是否已经启动
	private static ServerSocket serverSocket = null;
	// 占用一个端口号
	// private static final int serverPort = 12345;

	// 检查是否被占用,被占用说明已经启动则返回
	public boolean checkSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	// 注册热键
	public void registrationHotkey(SetupMsg sm) {
		int[] skm = proParamList(sm.getShkList(), ScreenShotMainGUI.SAVE_KEY_MARK);
		int[] ckm = proParamList(sm.getChkList(), ScreenShotMainGUI.CUT_KEY_MARK);
		int[] eckm = proParamList(sm.getCchkList(), ScreenShotMainGUI.EXIT_CURR_KEY_MARK);
		int[] bkm = proParamList(sm.getBhkList(), ScreenShotMainGUI.BACK_KEY_MARK);
		// 保存
		JIntellitype.getInstance().registerHotKey(ScreenShotMainGUI.SAVE_KEY_MARK, skm[0], skm[1]);
		// 复制到剪切板并关闭截图
		JIntellitype.getInstance().registerHotKey(ScreenShotMainGUI.CUT_KEY_MARK, ckm[0], ckm[1]);
		// 取消当前截图
		JIntellitype.getInstance().registerHotKey(ScreenShotMainGUI.EXIT_CURR_KEY_MARK, eckm[0], eckm[1]);
		// 撤销
		JIntellitype.getInstance().registerHotKey(ScreenShotMainGUI.BACK_KEY_MARK, bkm[0], bkm[1]);
	}

	// 取消注册热键
	public void cancelRegistrationHotkey() {
		JIntellitype.getInstance().unregisterHotKey(ScreenShotMainGUI.SAVE_KEY_MARK);
		JIntellitype.getInstance().unregisterHotKey(ScreenShotMainGUI.CUT_KEY_MARK);
		JIntellitype.getInstance().unregisterHotKey(ScreenShotMainGUI.EXIT_CURR_KEY_MARK);
		JIntellitype.getInstance().unregisterHotKey(ScreenShotMainGUI.BACK_KEY_MARK);
	}

	// 取消某一个热键注册
	public void cancelOneRegistrationHotkey(int f) {
		JIntellitype.getInstance().unregisterHotKey(f);
	}

	// 某一个热键注册
	public void registrationOneHotkey(int f, int[] km) {
		JIntellitype.getInstance().registerHotKey(f, km[0], km[1]);
	}

	public boolean isFileExit(File file) {
		if (file.exists()) {
			return true;
		}
		return false;
	}

	// 从配置文件中获取参数
	public SetupParams getDefaultMsg(File f) throws Exception {
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(f);
		BufferedReader bf = new BufferedReader(new InputStreamReader(fis, "utf-8"));
		pro.load(bf);
		SetupParams sp = new SetupParams();
		// 基本设置
		sp.setCustomizeSave("0".equals(pro.getProperty("iscustomizesave")) ? true : false);
		sp.setCustomSavePath(pro.getProperty("customsavepath"));
		sp.setgColorP(Integer.valueOf(pro.getProperty("gcolorp")));
		sp.setgSizeP(Integer.valueOf(pro.getProperty("gsizep")));
		sp.setImgFormatP(Integer.valueOf(pro.getProperty("imgformatp")));
		sp.setImgSharpness(Integer.valueOf(pro.getProperty("imgsharpness")));
		sp.setSelfStart("0".equals(pro.getProperty("isselfstart")) ? true : false);
		sp.setStartMinSize("0".equals(pro.getProperty("isstartminsize")) ? true : false);

		// 快捷键设置
		sp.setsShotHotKey(pro.getProperty("sshothotkey"));
		sp.setSaveHotKey(pro.getProperty("savehotkey"));
		sp.setCopyHotKey(pro.getProperty("copyhotkey"));
		sp.setExitHotKey(pro.getProperty("exithotkey"));
		sp.setCancelHotKey(pro.getProperty("cancelhotkey"));
		sp.setBackHotKey(pro.getProperty("cancelhotkey"));
		sp.setBackHotKey(pro.getProperty("backhotkey"));

		// 获取占用端口
		sp.setPort(pro.getProperty("port"));

		// 高级设置
		sp.setGetCursor("0".equals(pro.getProperty("isgetcursor")) ? true : false);
		sp.setShowMagnifier("0".equals(pro.getProperty("isshowmagnifier")) ? true : false);
		sp.setShowPixels("0".equals(pro.getProperty("isshowpixels")) ? true : false);
		sp.setAddChar("0".equals(pro.getProperty("isaddchar")) ? true : false);
		sp.setOvalCallout("0".equals(pro.getProperty("isovalcallout")) ? true : false);
		sp.setMosaic("0".equals(pro.getProperty("ismosaic")) ? true : false);
		sp.setCallBack("0".equals(pro.getProperty("iscallback")) ? true : false);
		sp.setLineCallout("0".equals(pro.getProperty("islinecallout")) ? true : false);
		sp.setCirCallout("0".equals(pro.getProperty("iscircallout")) ? true : false);
		sp.setShare("0".equals(pro.getProperty("isshare")) ? true : false);
		sp.setRoundedRectangleCallout("0".equals(pro.getProperty("isroundedrectanglecallout")) ? true : false);
		sp.setRectangleCallout("0".equals(pro.getProperty("isrectanglecallout")) ? true : false);
		sp.setCurveCallout("0".equals(pro.getProperty("iscurvecallout")) ? true : false);
		sp.setShowAxis("0".equals(pro.getProperty("isshowaxis")) ? true : false);
		// 注册
		sp.setRegistrationService("0".equals(pro.getProperty("isregistrationservice")) ? true : false);

		// 鼠标事件
		sp.setMouseSCFeature(Integer.valueOf(pro.getProperty("mousescfeature")));
		sp.setMouseDCFeature(Integer.valueOf(pro.getProperty("mousedcfeature")));
		// 右击
		sp.setAddRCCancel("0".equals(pro.getProperty("isaddrccancel")) ? true : false);
		sp.setAddRCSave("0".equals(pro.getProperty("isaddrcsave")) ? true : false);
		sp.setAddRCCopy("0".equals(pro.getProperty("isaddrccopy")) ? true : false);
		sp.setAddRCCallBack("0".equals(pro.getProperty("isaddrccallback")) ? true : false);
		sp.setAddRCShare("0".equals(pro.getProperty("isaddrcshare")) ? true : false);
		sp.setAddRCExit("0".equals(pro.getProperty("isaddrcexit")) ? true : false);

		fis.close();
		bf.close();
		return sp;
	}

	// 将参数转换
	public SetupMsg trunSm(SetupParams sp) {
		SetupMsg sm = new SetupMsg();
		// 是否自定义保存
		sm.setCustomizeSave(sp.isCustomizeSave());
		// 保存路径
		sm.setCustomSavePath(sp.getCustomSavePath());
		// 画笔颜色
		Color c = Color.RED;
		int cnum = sp.getgColorP();
		if (cnum == 0) {
			// 红
			c = Color.RED;
		} else if (cnum == 1) {
			// 蓝
			c = Color.BLUE;
		} else if (cnum == 2) {
			// 黑
			c = Color.BLACK;
		} else if (cnum == 3) {
			// 黄
			c = Color.YELLOW;
		} else if (cnum == 4) {
			// 橙
			c = Color.ORANGE;
		} else if (cnum == 5) {
			// 绿
			c = Color.GREEN;
		}
		sm.setgColor(c);

		// 画笔粗细
		float gp = 1;
		int gpp = sp.getgSizeP();
		if (gpp == 0) {
			gp = 1;
		} else if (gpp == 1) {
			gp = 2;
		} else if (gpp == 2) {
			gp = 3;
		} else if (gpp == 3) {
			gp = 4;
		} else if (gpp == 4) {
			gp = 5;
		}
		sm.setgSize(gp);

		// 图片格式
		String gf = "png";
		int gff = sp.getImgFormatP();
		if (gff == 0) {
			gf = "png";
		} else if (gff == 1) {
			gf = "jpg";
		} else if (gff == 2) {
			gf = "bmp";
		} else if (gff == 3) {
			gf = "jpeg";
		} else if (gff == 4) {
			gf = "gif";
		}
		sm.setImgFormat(gf);

		// 图片清晰度
		sm.setImgSharpness(sp.getImgSharpness());
		// 是否自启动
		sm.setSelfStart(sp.isSelfStart());
		// 是否最小化
		sm.setStartMinSize(sp.isStartMinSize());
		// 快捷键参数转换
		sm.setsShotHotKey(sp.getsShotHotKey());
		sm.setSaveHotKey(sp.getSaveHotKey());
		sm.setCopyHotKey(sp.getCopyHotKey());
		sm.setExitHotKey(sp.getExitHotKey());
		sm.setCancelHotKey(sp.getCancelHotKey());
		sm.setBackHotKey(sp.getBackHotKey());

		// 并且以list格式存入
		sm.setSshkList(returnKeyList(sp.getsShotHotKey()));
		sm.setShkList(returnKeyList(sp.getSaveHotKey()));
		sm.setChkList(returnKeyList(sp.getCopyHotKey()));
		sm.setEhkList(returnKeyList(sp.getExitHotKey()));
		sm.setCchkList(returnKeyList(sp.getCancelHotKey()));
		sm.setBhkList(returnKeyList(sp.getBackHotKey()));

		// 端口
		int port = 0;
		if (sp.getPort() != null && !"".equals(sp.getPort())) {
			port = Integer.valueOf(sp.getPort());
		}
		sm.setPort(port);

		// 高级设置转换
		sm.setGetCursor(sp.isGetCursor());
		sm.setShowMagnifier(sp.isShowMagnifier());
		sm.setShowPixels(sp.isShowPixels());
		sm.setAddChar(sp.isAddChar());
		sm.setOvalCallout(sp.isOvalCallout());
		sm.setMosaic(sp.isMosaic());
		sm.setCallBack(sp.isCallBack());
		sm.setLineCallout(sp.isLineCallout());
		sm.setCirCallout(sp.isCirCallout());
		sm.setShare(sp.isShare());
		sm.setRoundedRectangleCallout(sp.isRoundedRectangleCallout());
		sm.setRectangleCallout(sp.isRectangleCallout());
		sm.setCurveCallout(sp.isCurveCallout());
		sm.setShowAxis(sp.isShowAxis());
		// 注册
		sm.setRegistrationService(sp.isRegistrationService());

		// 鼠标功能设置转换
		sm.setMouseSCFeature(sp.getMouseSCFeature());
		sm.setMouseDCFeature(sp.getMouseDCFeature());
		// 右击
		sm.setAddRCCancel(sp.isAddRCCancel());
		sm.setAddRCSave(sp.isAddRCSave());
		sm.setAddRCCopy(sp.isAddRCCopy());
		sm.setAddRCCallBack(sp.isAddRCCallBack());
		sm.setAddRCShare(sp.isAddRCShare());
		sm.setAddRCExit(sp.isAddRCExit());

		// 返回
		return sm;
	}

	// string to list
	private List<String> returnKeyList(String hk) {
		List<String> list = new ArrayList<String>();
		// 拆分成数组
		String[] sa = hk.replaceAll(" ", "").split("\\+");
		// 遍历数组根据value从map中取出key,加入集合
		// 先找控制键
		for (String s : sa) {
			// 从map中取出key
			for (Map.Entry<String, String> entry : ScreenShotMainGUI.keyMap2.entrySet()) {
				// 如果找到则加入
				if (s.equals(entry.getValue())) {
					list.add(entry.getKey());
					// 都是唯一对应，找到直接退出
					break;
				}
			}
		}

		// 再找其他键
		for (String s : sa) {
			// 从map中取出key
			for (Map.Entry<String, String> entry : ScreenShotMainGUI.keyMap.entrySet()) {
				// 如果找到则加入
				if (s.equals(entry.getValue())) {
					list.add(entry.getKey());
					// 都是唯一对应，找到直接退出
					break;
				}
			}
		}

		return list;
	}

	// 保存设置
	public boolean setupMsgToPro(SetupParams spp) {
		File f;
		String content = "";
		f = new File("screentshotsetup.properties");
		content = "iscustomizesave=" + (spp.isCustomizeSave() ? "0" : "1") + "\r\n" + "customsavepath="
				+ spp.getCustomSavePath() + "\r\n" + "gcolorp=" + spp.getgColorP() + "\r\n" + "gsizep="
				+ spp.getgSizeP() + "\r\n" + "imgformatp=" + spp.getImgFormatP() + "\r\n" + "imgsharpness="
				+ spp.getImgSharpness() + "\r\n" + "isselfstart=" + (spp.isSelfStart() ? "0" : "1") + "\r\n"
				+ "isstartminsize=" + (spp.isStartMinSize() ? "0" : "1") + "\r\n" + "sshothotkey="
				+ spp.getsShotHotKey() + "\r\n" + "savehotkey=" + spp.getSaveHotKey() + "\r\n" + "copyhotkey="
				+ spp.getCopyHotKey() + "\r\n" + "exithotkey=" + spp.getExitHotKey() + "\r\n" + "cancelhotkey="
				+ spp.getCancelHotKey() + "\r\n" + "backhotkey=" + spp.getBackHotKey() + "\r\n" + "port="
				+ spp.getPort() + "\r\n" + "isgetcursor=" + (spp.isGetCursor() ? "0" : "1") + "\r\n"
				+ "isshowmagnifier=" + (spp.isShowMagnifier() ? "0" : "1") + "\r\n" + "isshowpixels="
				+ (spp.isShowPixels() ? "0" : "1") + "\r\n" + "isaddchar=" + (spp.isAddChar() ? "0" : "1") + "\r\n"
				+ "isovalcallout=" + (spp.isOvalCallout() ? "0" : "1") + "\r\n" + "ismosaic="
				+ (spp.isMosaic() ? "0" : "1") + "\r\n" + "iscallback=" + (spp.isCallBack() ? "0" : "1") + "\r\n"
				+ "islinecallout=" + (spp.isLineCallout() ? "0" : "1") + "\r\n" + "iscircallout="
				+ (spp.isCirCallout() ? "0" : "1") + "\r\n" + "isshare=" + (spp.isShare() ? "0" : "1") + "\r\n"
				+ "isroundedrectanglecallout=" + (spp.isRoundedRectangleCallout() ? "0" : "1") + "\r\n"
				+ "isrectanglecallout=" + (spp.isRectangleCallout() ? "0" : "1") + "\r\n" + "iscurvecallout="
				+ (spp.isCurveCallout() ? "0" : "1") + "\r\n" + "isshowaxis=" + (spp.isShowAxis() ? "0" : "1") + "\r\n"
				+ "isregistrationservice="
				+ (spp.isRegistrationService() ? "0"
						: "1" + "\r\n" + "mousescfeature=" + spp.getMouseSCFeature() + "\r\n" + "mousedcfeature="
								+ spp.getMouseDCFeature() + "\r\n" + "isaddrccancel="
								+ (spp.isAddRCCancel() ? "0" : "1") + "\r\n" + "isaddrcsave="
								+ (spp.isAddRCSave() ? "0" : "1") + "\r\n" + "isaddrccopy="
								+ (spp.isAddRCCopy() ? "0" : "1") + "\r\n" + "isaddrccallback="
								+ (spp.isAddRCCallBack() ? "0" : "1") + "\r\n" + "isaddrcshare="
								+ (spp.isAddRCShare() ? "0" : "1") + "\r\n" + "isaddrcexit="
								+ (spp.isAddRCExit() ? "0" : "1"));
		// 先删除隐藏文件再重新创建，隐藏文件不支持修改
		if (f.exists()) {
			f.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);// 创建文件输出流对象
			// 设置文件的隐藏属性
			String set = "attrib +H " + f.getAbsolutePath();
			Runtime.getRuntime().exec(set);
			// 将字符串写入到文件中
			fos.write(content.getBytes());
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	// 为了防止设置热键时已经设好的热键不能设置了，特此处理
	public void proKeyBeforeSshot(int keyMark, SetupMsg sm) {
		List<String> list = new ArrayList<String>();
		if (keyMark == 0) {
			list = sm.getSshkList();
			if (list == null || list.size() <= 0) {
				list.add("16");
				list.add("17");
				list.add("65");
			}
		} else if (keyMark == 1) {
			list = sm.getShkList();
			if (list == null || list.size() <= 0) {
				list.add("16");
				list.add("17");
				list.add("83");
			}
		} else if (keyMark == 2) {
			list = sm.getChkList();
			if (list == null || list.size() <= 0) {
				list.add("16");
				list.add("17");
				list.add("90");
			}
		} else if (keyMark == 3) {
			list = sm.getEhkList();
			if (list == null || list.size() <= 0) {
				list.add("17");
				list.add("18");
				list.add("81");
			}
		} else if (keyMark == 4) {
			list = sm.getCchkList();
			if (list == null || list.size() <= 0) {
				list.add("17");
				list.add("81");
			}
		} else if (keyMark == 5) {
			list = sm.getBhkList();
			if (list == null || list.size() <= 0) {
				list.add("17");
				list.add("90");
			}
		}
		// 先记录按键
		// hotKey = "aa";
		SetupJF.keyList.clear();
		for (String s : list) {
			SetupJF.keyList.add(s);
		}
	}

	// 将list转为参数
	public int[] proParamList(List<String> hkList, int f) {
		int[] params = new int[2];
		if (hkList != null && hkList.size() > 0) {
			for (String s : hkList) {
				if (Integer.valueOf(s) == 16) {
					// shift
					params[0] = params[0] + JIntellitype.MOD_SHIFT;
				} else if (Integer.valueOf(s) == 17) {
					params[0] = params[0] + JIntellitype.MOD_CONTROL;
				} else if (Integer.valueOf(s) == 18) {
					params[0] = params[0] + JIntellitype.MOD_ALT;
				} else {
					params[1] = params[1] + Integer.valueOf(s);
				}
			}
		} else {
			if (f == 0) {
				params[0] = JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT;
				params[1] = 65;
			} else if (f == 1) {
				params[0] = JIntellitype.MOD_CONTROL;
				params[1] = 83;
			} else if (f == 2) {
				params[0] = JIntellitype.MOD_CONTROL;
				params[1] = 67;
			} else if (f == 3) {
				params[0] = JIntellitype.MOD_CONTROL + JIntellitype.MOD_ALT;
				params[1] = 81;
			} else if (f == 4) {
				params[0] = JIntellitype.MOD_CONTROL;
				params[1] = 81;
			} else if (f == 5) {
				params[0] = JIntellitype.MOD_CONTROL;
				params[1] = 90;
			}
		}

		return params;
	}

	// 初始化KeyMap
	public void initKeyMap() {
		// 字母
		ScreenShotMainGUI.keyMap.put("65", "A");
		ScreenShotMainGUI.keyMap.put("66", "B");
		ScreenShotMainGUI.keyMap.put("67", "C");
		ScreenShotMainGUI.keyMap.put("68", "D");
		ScreenShotMainGUI.keyMap.put("69", "E");
		ScreenShotMainGUI.keyMap.put("70", "F");
		ScreenShotMainGUI.keyMap.put("71", "G");
		ScreenShotMainGUI.keyMap.put("72", "H");
		ScreenShotMainGUI.keyMap.put("73", "I");
		ScreenShotMainGUI.keyMap.put("74", "J");
		ScreenShotMainGUI.keyMap.put("75", "K");
		ScreenShotMainGUI.keyMap.put("76", "L");
		ScreenShotMainGUI.keyMap.put("77", "M");
		ScreenShotMainGUI.keyMap.put("78", "N");
		ScreenShotMainGUI.keyMap.put("79", "O");
		ScreenShotMainGUI.keyMap.put("80", "P");
		ScreenShotMainGUI.keyMap.put("81", "Q");
		ScreenShotMainGUI.keyMap.put("82", "R");
		ScreenShotMainGUI.keyMap.put("83", "S");
		ScreenShotMainGUI.keyMap.put("84", "T");
		ScreenShotMainGUI.keyMap.put("85", "U");
		ScreenShotMainGUI.keyMap.put("86", "V");
		ScreenShotMainGUI.keyMap.put("87", "W");
		ScreenShotMainGUI.keyMap.put("88", "X");
		ScreenShotMainGUI.keyMap.put("89", "Y");
		ScreenShotMainGUI.keyMap.put("90", "Z");

		// 数字
		ScreenShotMainGUI.keyMap.put("48", "0");
		ScreenShotMainGUI.keyMap.put("49", "1");
		ScreenShotMainGUI.keyMap.put("50", "2");
		ScreenShotMainGUI.keyMap.put("51", "3");
		ScreenShotMainGUI.keyMap.put("52", "4");
		ScreenShotMainGUI.keyMap.put("53", "5");
		ScreenShotMainGUI.keyMap.put("54", "6");
		ScreenShotMainGUI.keyMap.put("55", "7");
		ScreenShotMainGUI.keyMap.put("56", "8");
		ScreenShotMainGUI.keyMap.put("57", "9");

		// 小键盘数字
		ScreenShotMainGUI.keyMap.put("96", "0");
		ScreenShotMainGUI.keyMap.put("97", "1");
		ScreenShotMainGUI.keyMap.put("98", "2");
		ScreenShotMainGUI.keyMap.put("99", "3");
		ScreenShotMainGUI.keyMap.put("100", "4");
		ScreenShotMainGUI.keyMap.put("101", "5");
		ScreenShotMainGUI.keyMap.put("102", "6");
		ScreenShotMainGUI.keyMap.put("103", "7");
		ScreenShotMainGUI.keyMap.put("104", "8");
		ScreenShotMainGUI.keyMap.put("105", "9");

		// 控制键
		ScreenShotMainGUI.keyMap2.put("16", "Shift");
		ScreenShotMainGUI.keyMap2.put("17", "Ctrl");
		ScreenShotMainGUI.keyMap2.put("18", "Alt");
	}

	// 从ssmm中取出右击菜单
	public Map<Integer, String> getJpmMenu() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		if (ScreenShotMainGUI.ssmm != null) {
			if (ScreenShotMainGUI.ssmm.isAddRCCancel()) {
				map.put(1, "取消当前截图");
			}
			if (ScreenShotMainGUI.ssmm.isAddRCSave()) {
				map.put(2, "保存当前截图");
			}
			if (ScreenShotMainGUI.ssmm.isAddRCCopy()) {
				map.put(3, "复制当前截图");
			}
			if (ScreenShotMainGUI.ssmm.isAddRCCallBack()) {
				map.put(4, "撤销至上一步");
			}
			if (ScreenShotMainGUI.ssmm.isAddRCShare()) {
				map.put(5, "分享当前截图");
			}
			if (ScreenShotMainGUI.ssmm.isAddRCExit()) {
				map.put(6, "退出截图程序");
			}
		}
		return map;
	}
}
