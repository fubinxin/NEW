package utils.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 授权访问
 * 只能在@Controller注解的类的方法上面
 * Created by weican on 2017/12/12.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizedAccess {
    /**
     * 描述
     */
    String description() default "";

    /**
     * 角色
     */
    String[] roles() default {};

    /**
     * 权限
     */
    String[] permissions() default {};
}


