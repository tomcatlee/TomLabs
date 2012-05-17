package com.tom.labs;

public enum EnumTest {
	DELETE("删除", (short) 0), DAI_FUWUCHE("等待指派服务车", (short) 1), DAI_FUWUCHE_ARRIVE(
			"等待服务车到达", (short) 2), DAI_ADD_FORM_1("等待填写派出单", (short) 3), DAI_TEL_BACK(
			"等待电话回馈", (short) 4), DAI_ADD_FORM_2("等待填写派出单", (short) 5), DAI_UPLOAD(
			"等待上传扫描件", (short) 6), DAI_CHECK_1("等待服务人员审核", (short) 7), DAI_CHECK_2(
			"等待结算人员审核", (short) 8), FINISH("完成", (short) 9);

	private String text;
	private Short index;

	private EnumTest(String text, Short index) {
		this.text = text;
		this.index = index;
	}

	public Short getIndex() {
		return index;
	}

	public String getText() {
		return this.text;
	}

	public static void main(String[] args) {
		System.out.println(DELETE.getIndex());
		System.out.println(DELETE.getText());
	}
}
