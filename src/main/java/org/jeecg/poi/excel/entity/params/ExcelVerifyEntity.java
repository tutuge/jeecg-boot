/**
 * Copyright 2013-2015 JEECG (jeecgos@163.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jeecg.poi.excel.entity.params;

/**
 * Excel 校验对象
 *
 * @author JEECG
 * @date 2014年6月29日 下午4:24:59
 */
public class ExcelVerifyEntity {

	/**
	 * 接口校验
	 *
	 * @return
	 */
	private boolean interHandler;

	/**
	 * 不允许空
	 *
	 * @return
	 */
	private boolean notNull;

	/**
	 * 是13位移动电话
	 *
	 * @return
	 */
	private boolean isMobile;
	/**
	 * 是座机号码
	 *
	 * @return
	 */
	private boolean isTel;

	/**
	 * 是电子邮件
	 *
	 * @return
	 */
	private boolean isEmail;

	/**
	 * 最小长度
	 *
	 * @return
	 */
	private int minLength;

	/**
	 * 最大长度
	 *
	 * @return
	 */
	private int maxLength;

	/**
	 * 正在表达式
	 *
	 * @return
	 */
	private String regex;
	/**
	 * 正在表达式,错误提示信息
	 *
	 * @return
	 */
	private String regexTip;

	public int getMaxLength() {
		return maxLength;
	}

	public int getMinLength() {
		return minLength;
	}

	public String getRegex() {
		return regex;
	}

	public String getRegexTip() {
		return regexTip;
	}

	public boolean isEmail() {
		return isEmail;
	}

	public boolean isInterHandler() {
		return interHandler;
	}

	public boolean isMobile() {
		return isMobile;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public boolean isTel() {
		return isTel;
	}

	public void setEmail(boolean isEmail) {
		this.isEmail = isEmail;
	}

	public void setInterHandler(boolean interHandler) {
		this.interHandler = interHandler;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public void setRegexTip(String regexTip) {
		this.regexTip = regexTip;
	}

	public void setTel(boolean isTel) {
		this.isTel = isTel;
	}

}
