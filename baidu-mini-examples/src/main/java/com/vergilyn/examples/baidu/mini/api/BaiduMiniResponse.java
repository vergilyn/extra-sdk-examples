package com.vergilyn.examples.baidu.mini.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author vergilyn
 * @since 2022-04-20
 */
@Data
@NoArgsConstructor
public class BaiduMiniResponse<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 3030826784608174417L;

	private static final Long SUCCESS = 0L;

	/**
	 * 错误码
	 */
	private Long errno;

	/**
	 * 错误信息
	 */
	private String msg;

	/**
	 *
	 */
	private T data;

	public boolean isSuccess() {
		return this.errno != null && Objects.equals(SUCCESS, this.errno);
	}
}
