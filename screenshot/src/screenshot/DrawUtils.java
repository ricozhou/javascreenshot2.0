package screenshot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DrawUtils {
	protected void drawCurveLine(int sx, int sy, int ex, int ey, Graphics g) {
		g.drawLine(sx, sy, ex, ey);
	}

	// @Override
	// public void paintComponents(Graphics g) {
	// float lineWidth = 8.0f;
	// ((Graphics2D) g).setStroke(new BasicStroke(lineWidth));
	// g.setColor(Color.YELLOW);
	// // g.drawLine(startX, startY, endX, endY);
	// g.dispose();
	// }

	// 马赛克
	public static String markImageByMosaic(BufferedImage img, int size, int sx, int sy, int ex, int ey) {
		Graphics2D g2 = (Graphics2D) img.getGraphics();
		// System.out.println(sx + " " + sy + " " + ex + " " + ey);
		int width = Math.abs(ex - sx) + 1;
		; // 原图片宽
		int height = Math.abs(ey - sy) + 1; // 原图片高

		// 马赛克格尺寸太大或太小
		if (width < size || height < size) {
			return "马赛克格尺寸太大";
		}
		if (size <= 0) {
			return "马赛克格尺寸太小";
		}
		int xcount = 0; // x方向绘制个数
		int ycount = 0; // y方向绘制个数
		if (width % size == 0) {
			xcount = width / size;
		} else {
			xcount = width / size + 1;
		}
		if (height % size == 0) {
			ycount = height / size;
		} else {
			ycount = height / size + 1;
		}
		int x = sx; // x坐标
		int y = sy;
		// System.out.println(xcount+" "+ycount+" "+width+" "+height);
		// y坐标
		// 绘制马赛克(绘制矩形并填充颜色)
		for (int i = 0; i < xcount; i++) {
			// System.out.println(i);
			for (int j = 0; j < ycount; j++) {
				// 马赛克矩形格大小
				int mwidth = size;
				int mheight = size;
				// if (i == xcount - 1) { // 横向最后一个不够一个size
				// mwidth = width - x;
				// }
				// if (j == ycount - 1) { // 纵向最后一个不够一个size
				// mheight = height - y;
				// }
				// 矩形颜色取中心像素点RGB值
				int centerX = x;
				int centerY = y;
				if (mwidth % 2 == 0) {
					centerX += mwidth / 2;
				} else {
					centerX += (mwidth - 1) / 2;
				}
				if (mheight % 2 == 0) {
					centerY += mheight / 2;
				} else {
					centerY += (mheight - 1) / 2;
				}
				String rgb = getRandColorCode();
				Color color = new Color(Integer.parseInt(rgb, 16));
				g2.setColor(color);
				// System.out.println(x + " " + y + " " + mwidth + " " +
				// mheight);
				g2.fillRect(x, y, mwidth, mheight);
				y = y + size;// 计算下一个矩形的y坐标
			}
			y = sy;// 还原y坐标
			x = x + size;// 计算x坐标
		}
		return null;
	}

	// 随机颜色
	public static String getRandColorCode() {
		String r, g, b;
		Random random = new Random();
		r = Integer.toHexString(random.nextInt(256)).toUpperCase();
		g = Integer.toHexString(random.nextInt(256)).toUpperCase();
		b = Integer.toHexString(random.nextInt(256)).toUpperCase();

		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;

		return r + g + b;
	}

	// 普通直线
	public static void drawStrLine(int sx, int sy, int ex, int ey, Graphics2D g2) {
		// 画线
		g2.drawLine(sx, sy, ex, ey);
	}

	// 画箭头线
	public static void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2) {

		double H = 10; // 箭头高度
		double L = 4; // 底边的一半
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = ey - arrXY_2[1];

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		// 画线
		g2.drawLine(sx, sy, ex, ey);
		//
		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(ex, ey);
		triangle.lineTo(x3, y3);
		triangle.lineTo(x4, y4);
		triangle.closePath();
		// 实心箭头
		// g2.fill(triangle);
		// 非实心箭头
		g2.draw(triangle);

	}

	// 计算
	public static double[] rotateVec(int px, int py, double ang, boolean isChLen, double newLen) {

		double mathstr[] = new double[2];
		// 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}

}
