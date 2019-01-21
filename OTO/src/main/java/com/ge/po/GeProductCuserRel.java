package com.ge.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import org.hibernate.validator.constraints.*;

import org.springframework.validation.annotation.Validated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* ge_product_cuser_rel
*  Created by autoGen Tools 
*/
@ApiModel(value = "ge_product_cuser_rel")
public class GeProductCuserRel implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	/**
	* 主键,自增
	*/
		@Id
		 @ApiModelProperty(value = "主键,自增")
	private java.lang.Integer id;
	/**
	* 认证资质用户ID
	*/
		 @ApiModelProperty(value = "认证资质用户ID")
	private java.lang.Integer cid;
	/**
	* 认证资质产品id
	*/
		 @ApiModelProperty(value = "认证资质产品id")
	private java.lang.Integer pid;
	/**
	* 有效期时间
	*/
		 @ApiModelProperty(value = "有效期时间")
	private java.util.Date validTime;
	/**
	* 创建时间
	*/
		 @ApiModelProperty(value = "创建时间")
	private java.util.Date createTime;

	/**
	* set 主键,自增
	*/
	public void setId ( java.lang.Integer id ) {
		this.id = id;
	}
	/**
	* get 主键,自增
	*/
	public java.lang.Integer getId (){
		return id;
	}
	
	/**
	* set 认证资质用户ID
	*/
	public void setCid ( java.lang.Integer cid ) {
		this.cid = cid;
	}
	/**
	* get 认证资质用户ID
	*/
	public java.lang.Integer getCid (){
		return cid;
	}
	
	/**
	* set 认证资质产品id
	*/
	public void setPid ( java.lang.Integer pid ) {
		this.pid = pid;
	}
	/**
	* get 认证资质产品id
	*/
	public java.lang.Integer getPid (){
		return pid;
	}
	
	/**
	* set 有效期时间
	*/
	public void setValidTime ( java.util.Date validTime ) {
		this.validTime = validTime;
	}
	/**
	* get 有效期时间
	*/
	public java.util.Date getValidTime (){
		return validTime;
	}
	
	/**
	* set 创建时间
	*/
	public void setCreateTime ( java.util.Date createTime ) {
		this.createTime = createTime;
	}
	/**
	* get 创建时间
	*/
	public java.util.Date getCreateTime (){
		return createTime;
	}
	

	/**
	 * 自带校验的功能.如果不能解决.请在相应的controller 里，去掉 @Validated 这样，就不会走后台的校验.
	 * *@NotNull  注解元素必须是非空
		*@Null  注解元素必须是空
		*@Digits  验证数字构成是否合法
		*@Future  验证是否在当前系统时间之后
		*@Past  验证是否在当前系统时间之前
		*@Max  验证值是否小于等于最大指定整数值
		*@Min  验证值是否大于等于最小指定整数值
		* @Pattern 验证字符串是否匹配指定的正则表达式
		* @Size 验证元素大小是否在指定范围内
		* @DecimalMax 验证值是否小于等于最大指定小数值
		* @DecimalMin 验证值是否大于等于最小指定小数值
		* @AssertTrue 被注释的元素必须为true
		* @AssertFalse 被注释的元素必须为false
		HibernateValidator扩展注解类：
		*@Email  被注释的元素必须是电子邮箱地址
		*@Length  被注释的字符串的大小必须在指定的范围内
		*@NotEmpty  被注释的字符串的必须非空
		*@Range  被注释的元素必须在合适的范围内
		如--------------
	 * @NotBlank(message="{valid.name}") 非空.
	 * @Length(min=4, max=20, message="{valid.password}") 长度限制
	 * @Min(value=18, message="{valid.ageMin}")
     * @Max(value=100, message="{valid.ageMax}") 可以与上面的min 一起使用.限定范围
     * @Email(message="{valid.email}") 正确email
     * @Past(message="{valid.birthday}") 今天以前的日期.
     * @Pattern(regexp="^[a-zA-Z]{2,}$", message="{valid.address}") 最最强大的 正则的.你想要啥样
     * @com.my.controller.validator.MyValidator(message="{valid.tel}", min=3) 还不成的话自己写一个类.想校验啥
    */

}