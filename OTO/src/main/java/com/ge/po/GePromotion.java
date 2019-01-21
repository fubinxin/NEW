package com.ge.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * ge_news
 * Created by autoGen Tools
 */
@ApiModel(value = "ge_promotion")
public class GePromotion implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,自增
     */
    @Id
    @ApiModelProperty(value = "主键,自增")
    private java.lang.Integer id;
    /**
     * 新闻图片
     */
    @ApiModelProperty(value = "新闻图片")
    private java.lang.String imgPath;
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private java.lang.String title;
    /**
     * 新闻内容
     */
    @ApiModelProperty(value = "新闻内容")
    private java.lang.String content;
    /**
     * 类型(1、不滚动；2、图片滚动)
     */
    @ApiModelProperty(value = "类型(1、不滚动；2、图片滚动)")
    private java.lang.Integer type;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
    private java.lang.String describe;
    private java.lang.Integer settop;
    private java.lang.Integer categoryid;
    private java.lang.String guide;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getSettop() {
        return settop;
    }

    public void setSettop(Integer settop) {
        this.settop = settop;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    /**
     * set 主键,自增
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }

    /**
     * get 主键,自增
     */
    public java.lang.Integer getId() {
        return id;
    }

    /**
     * set 新闻图片
     */
    public void setImgPath(java.lang.String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * get 新闻图片
     */
    public java.lang.String getImgPath() {
        return imgPath;
    }

    /**
     * set 标题
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    /**
     * get 标题
     */
    public java.lang.String getTitle() {
        return title;
    }

    /**
     * set 新闻内容
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }

    /**
     * get 新闻内容
     */
    public java.lang.String getContent() {
        return content;
    }

    /**
     * set 类型(1、不滚动；2、图片滚动)
     */
    public void setType(java.lang.Integer type) {
        this.type = type;
    }

    /**
     * get 类型(1、不滚动；2、图片滚动)
     */
    public java.lang.Integer getType() {
        return type;
    }

    /**
     * set 排序
     */
    public void setSort(java.lang.Integer sort) {
        this.sort = sort;
    }

    /**
     * get 排序
     */
    public java.lang.Integer getSort() {
        return sort;
    }

    /**
     * set 创建时间
     */
    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    /**
     * get 创建时间
     */
    public java.util.Date getCreateTime() {
        return createTime;
    }


    /**
     * 自带校验的功能.如果不能解决.请在相应的controller 里，去掉 @Validated 这样，就不会走后台的校验.
     * *@NotNull  注解元素必须是非空
     *@Null 注解元素必须是空
     *@Digits 验证数字构成是否合法
     *@Future 验证是否在当前系统时间之后
     *@Past 验证是否在当前系统时间之前
     *@Max 验证值是否小于等于最大指定整数值
     *@Min 验证值是否大于等于最小指定整数值
     * @Pattern 验证字符串是否匹配指定的正则表达式
     * @Size 验证元素大小是否在指定范围内
     * @DecimalMax 验证值是否小于等于最大指定小数值
     * @DecimalMin 验证值是否大于等于最小指定小数值
     * @AssertTrue 被注释的元素必须为true
     * @AssertFalse 被注释的元素必须为false
    HibernateValidator扩展注解类：
     *@Email 被注释的元素必须是电子邮箱地址
     *@Length 被注释的字符串的大小必须在指定的范围内
     *@NotEmpty 被注释的字符串的必须非空
     *@Range 被注释的元素必须在合适的范围内
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