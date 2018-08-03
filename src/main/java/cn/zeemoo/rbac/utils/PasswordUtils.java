package cn.zeemoo.rbac.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 密码工具类
 *
 * @author zhang.shushan
 */
@Component
public class PasswordUtils {

    @Value("${password.salt}")
    private String salt;

    /**
     * 是否同时包含字母，数字和特殊符号
     * @param str
     * @return true-是 false-否
     */
    public boolean isContainLetterAndNumAndSymbols(String str) {
        Matcher lower = Pattern.compile("[a-z]+").matcher(str);
        Matcher upper = Pattern.compile("[A-Z]+").matcher(str);
        Matcher numbers = Pattern.compile("[0-9]+").matcher(str);
        Matcher symbols = Pattern.compile("[\\*#@%&_\\.]+").matcher(str);
        return lower.find()&&upper.find()&&numbers.find()&&symbols.find();
    }

    /**
     * 是否包含3位及以上字符组合的重复
     *
     * @param str
     * @return true-是 false-否
     */
    public boolean isOver3SameElement(String str) {
        return str.matches("^.*(.)\\1{2,}+.*$");
    }

    /**
     * 是否包含空格、制表符、换页符等空白字符
     *
     * @param str
     * @return true-是 false-否
     */
    public boolean isContainSpecialSymbols(String str) {
        return str.matches("^.*[\\s]+.*$");
    }

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    public String encode(String password) {
        return DigestUtils.md5Hex(password + salt);
    }

    /**
     * 密码匹配
     *
     * @param password        明文密码
     * @param encodedPassword 加密后的密码
     * @return
     */
    public boolean isSame(String password, String encodedPassword) {
        return encode(password).equals(encodedPassword);
    }
}
