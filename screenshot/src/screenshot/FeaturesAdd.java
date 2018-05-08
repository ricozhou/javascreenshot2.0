package screenshot;

public class FeaturesAdd {
	// 截图是否带光标
	public boolean isGetCursor;
	// 显示放大镜
	public boolean isShowMagnifier;
	// 显示像素
	public boolean isShowPixels;
	// 添加文字
	public boolean isAddChar;
	// 椭圆标注
	public boolean isOvalCallout;
	// 马赛克
	public boolean isMosaic;
	// 撤销
	public boolean isCallBack;
	// 直线
	public boolean isLineCallout;
	// 圆形标注
	public boolean isCirCallout;
	// 分享
	public boolean isShare;
	// 圆角矩形
	public boolean isRoundedRectangleCallout;
	// 矩形
	public boolean isRectangleCallout;
	// 曲线
	public boolean isCurveCallout;
	// 显示坐标轴
	public boolean isShowAxis;
	// 注册服务
	public boolean isRegistrationService;

	// 鼠标事件设置
	// 单击功能
	public int mouseSCFeature;
	// 双击功能
	public int mouseDCFeature;
	// 右键菜单
	// 是否添加取消功能
	public boolean isAddRCCancel;
	// 是否添加保存功能
	public boolean isAddRCSave;
	// 是否添加复制功能
	public boolean isAddRCCopy;
	// 是否添加撤销功能
	public boolean isAddRCCallBack;
	// 是否添加分享功能
	public boolean isAddRCShare;
	// 是否添加退出功能
	public boolean isAddRCExit;

	public int getMouseSCFeature() {
		return mouseSCFeature;
	}

	public void setMouseSCFeature(int mouseSCFeature) {
		this.mouseSCFeature = mouseSCFeature;
	}

	public int getMouseDCFeature() {
		return mouseDCFeature;
	}

	public void setMouseDCFeature(int mouseDCFeature) {
		this.mouseDCFeature = mouseDCFeature;
	}

	public boolean isAddRCCancel() {
		return isAddRCCancel;
	}

	public void setAddRCCancel(boolean isAddRCCancel) {
		this.isAddRCCancel = isAddRCCancel;
	}

	public boolean isAddRCSave() {
		return isAddRCSave;
	}

	public void setAddRCSave(boolean isAddRCSave) {
		this.isAddRCSave = isAddRCSave;
	}

	public boolean isAddRCCopy() {
		return isAddRCCopy;
	}

	public void setAddRCCopy(boolean isAddRCCopy) {
		this.isAddRCCopy = isAddRCCopy;
	}

	public boolean isAddRCCallBack() {
		return isAddRCCallBack;
	}

	public void setAddRCCallBack(boolean isAddRCCallBack) {
		this.isAddRCCallBack = isAddRCCallBack;
	}

	public boolean isAddRCShare() {
		return isAddRCShare;
	}

	public void setAddRCShare(boolean isAddRCShare) {
		this.isAddRCShare = isAddRCShare;
	}

	public boolean isAddRCExit() {
		return isAddRCExit;
	}

	public void setAddRCExit(boolean isAddRCExit) {
		this.isAddRCExit = isAddRCExit;
	}

	public boolean isGetCursor() {
		return isGetCursor;
	}

	public void setGetCursor(boolean isGetCursor) {
		this.isGetCursor = isGetCursor;
	}

	public boolean isShowMagnifier() {
		return isShowMagnifier;
	}

	public void setShowMagnifier(boolean isShowMagnifier) {
		this.isShowMagnifier = isShowMagnifier;
	}

	public boolean isShowPixels() {
		return isShowPixels;
	}

	public void setShowPixels(boolean isShowPixels) {
		this.isShowPixels = isShowPixels;
	}

	public boolean isAddChar() {
		return isAddChar;
	}

	public void setAddChar(boolean isAddChar) {
		this.isAddChar = isAddChar;
	}

	public boolean isOvalCallout() {
		return isOvalCallout;
	}

	public void setOvalCallout(boolean isOvalCallout) {
		this.isOvalCallout = isOvalCallout;
	}

	public boolean isMosaic() {
		return isMosaic;
	}

	public void setMosaic(boolean isMosaic) {
		this.isMosaic = isMosaic;
	}

	public boolean isCallBack() {
		return isCallBack;
	}

	public void setCallBack(boolean isCallBack) {
		this.isCallBack = isCallBack;
	}

	public boolean isLineCallout() {
		return isLineCallout;
	}

	public void setLineCallout(boolean isLineCallout) {
		this.isLineCallout = isLineCallout;
	}

	public boolean isCirCallout() {
		return isCirCallout;
	}

	public void setCirCallout(boolean isCirCallout) {
		this.isCirCallout = isCirCallout;
	}

	public boolean isShare() {
		return isShare;
	}

	public void setShare(boolean isShare) {
		this.isShare = isShare;
	}

	public boolean isRoundedRectangleCallout() {
		return isRoundedRectangleCallout;
	}

	public void setRoundedRectangleCallout(boolean isRoundedRectangleCallout) {
		this.isRoundedRectangleCallout = isRoundedRectangleCallout;
	}

	public boolean isRectangleCallout() {
		return isRectangleCallout;
	}

	public void setRectangleCallout(boolean isRectangleCallout) {
		this.isRectangleCallout = isRectangleCallout;
	}

	public boolean isCurveCallout() {
		return isCurveCallout;
	}

	public void setCurveCallout(boolean isCurveCallout) {
		this.isCurveCallout = isCurveCallout;
	}

	public boolean isShowAxis() {
		return isShowAxis;
	}

	public void setShowAxis(boolean isShowAxis) {
		this.isShowAxis = isShowAxis;
	}

	public boolean isRegistrationService() {
		return isRegistrationService;
	}

	public void setRegistrationService(boolean isRegistrationService) {
		this.isRegistrationService = isRegistrationService;
	}

	@Override
	public String toString() {
		return "FeaturesAdd [isGetCursor=" + isGetCursor + ", isShowMagnifier=" + isShowMagnifier + ", isShowPixels="
				+ isShowPixels + ", isAddChar=" + isAddChar + ", isOvalCallout=" + isOvalCallout + ", isMosaic="
				+ isMosaic + ", isCallBack=" + isCallBack + ", isLineCallout=" + isLineCallout + ", isCirCallout="
				+ isCirCallout + ", isShare=" + isShare + ", isRoundedRectangleCallout=" + isRoundedRectangleCallout
				+ ", isRectangleCallout=" + isRectangleCallout + ", isCurveCallout=" + isCurveCallout + ", isShowAxis="
				+ isShowAxis + ", isRegistrationService=" + isRegistrationService + ", mouseSCFeature=" + mouseSCFeature
				+ ", mouseDCFeature=" + mouseDCFeature + ", isAddRCCancel=" + isAddRCCancel + ", isAddRCSave="
				+ isAddRCSave + ", isAddRCCopy=" + isAddRCCopy + ", isAddRCCallBack=" + isAddRCCallBack
				+ ", isAddRCShare=" + isAddRCShare + ", isAddRCExit=" + isAddRCExit + "]";
	}
}
