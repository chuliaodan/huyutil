package com.huy.util.expression.core.parser;

import com.huy.util.Switch;
import com.huy.util.assist.constants.Constant;
import com.huy.util.assist.constants.ExpressPrintFlag;
import com.huy.util.expression.core.ExpressionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 表达式解析，用于解析括号的位置，前缀的位置
 * <p>
 * 注意：此类，日志打印不要轻易开启，只能在测试环境调试使用。打印日志很消耗性能。
 * <p>
 * Created by huyong on 2017/5/24.
 */
public class ExpressParserCustom {

    private static Logger logger = LoggerFactory.getLogger(ExpressParserCustom.class);

    /**
     * Java命名规范支持的字符,以及“.”号(防止误判为 函数的调用为前缀)
     */
    private static final String pattern = "[a-zA-Z1-9_\\.]";

    /**
     * 含有前缀的括号位置缓存，map<表达式，List<拆分后得到的括号对象>>
     */
    private static Map<String, List<Bracket>> cacheHasPrefix;

    /**
     * 表达式前缀集合
     */
    private static Set<String> prefixes;

    static {
        init();
    }

    public ExpressParserCustom() {
        init();
    }

    private static void init() {
        cacheHasPrefix = new HashMap<>();
        prefixes = ExpressionFactory.getPrefixAll();
        logger.info("表达式计算的所有前缀有：{}", prefixes);
    }

    /**
     * 获取最深层的含有前缀表达式的括号
     *
     * @param express 表达式：如：exp(spel(request.level)==3?'manager':'other')
     * @return
     */
    public static Bracket getBracketDeepest(String express) {

        List<Bracket> bracketsHasPrefix = getBracketListByExpress(express);

        if (!CollectionUtils.isEmpty(bracketsHasPrefix)) {
            return bracketsHasPrefix.get(bracketsHasPrefix.size() - 1);
        }

        return null;

    }

    /**
     * 解析表达式成括号集合
     *
     * @param express 表达式：如：exp(spel(request.level)==3?'manager':'other')
     * @return
     */
    public static List<Bracket> getBracketListByExpress(String express) {

        //从缓存中获取
        List<Bracket> brackets = cacheHasPrefix.get(express);
        if (!CollectionUtils.isEmpty(brackets)) {
            Switch.run(ExpressPrintFlag.expressAllPrint, () -> {
                logger.debug("缓存大小{},表达式{},在缓存中命中", cacheHasPrefix.size(), express);
            });
            return brackets;
        }

        if (hasNotPrefix(express)) {
            return null;
        }

        return fetchBracketHasPrefix(express);

    }

    /**
     * 表达式中不包含前缀(只是初略过滤)
     *
     * @param express 表达式
     * @return
     */
    private static boolean hasNotPrefix(String express) {
        return StringUtils.isEmpty(express) || !hasPrefix(express);
    }


    /**
     * 表达式中包含前缀(只是初略过滤)
     *
     * @param express 表达式
     * @return
     */
    private static boolean hasPrefix(String express) {

        for (String prefix : prefixes) {
            if (express.indexOf(prefix + "(") > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解析得到含有前缀的表达式
     *
     * @param express
     * @return
     */
    private static List<Bracket> fetchBracketHasPrefix(String express) {

        //只存储含有前缀的括号对象
        List<Bracket> resultList = new ArrayList<>();

        Deque<Bracket> stack = new ArrayDeque<>();

        char[] chars = express.toCharArray();
        for (int i = 0; i < chars.length; i++) {

            if (chars[i] == Constant.LEFT_BRACKET) {
                Bracket bracket = new Bracket(chars[i], i);
                setBracketPrefix(bracket, express);
                stack.push(bracket);

            } else if (chars[i] == Constant.RIGHT_BRACKET) {
                if (stack.isEmpty()//栈为空
                        || stack.peek().getEndSymble() != null) {//栈顶右括号已经存在
                    logger.error("括号不匹配，检查表达式-{},括号位置-{}", express, i);
                    throw new RuntimeException("括号不匹配，检查表达式!");

                } else {
                    Bracket bracket = stack.pop();
                    String prefix = bracket.getLeftBracketPrefix();
                    if (prefix != null && prefix.length() != 0) {//筛选只含有前缀的括号
                        bracket.setEndSymble(chars[i]);
                        bracket.setRightBracketPosition(i);
                        resultList.add(bracket);
                    }
                }
            }
        }

        //按左括号的位置进行排序
        return resultList.stream().sorted(Comparator.comparing(Bracket::getLeftBracketPosition)).collect(Collectors.toList());
    }

    /**
     * 设置前缀，如：exp、spel...
     *
     * @param bracket 括号对象
     * @param express 表达式
     * @return true-表示存在前缀被设置
     */
    private static void setBracketPrefix(Bracket bracket, String express) {

        int position = bracket.getLeftBracketPosition();//左括号的位置

        for (String prefix : prefixes) {

            int prefixLength = prefix.length();//前缀的长度
            if (position < prefixLength) {
                continue;
            }

            //从表达式中，按前缀的长度进行截取
            String tmpPrefix = express.substring(position - prefixLength, position);

            if (prefix.equals(tmpPrefix)) {//前缀相同，则进一步判断，这个前缀是否为单词的一部分

                if (position == prefixLength) {
                    bracket.setLeftBracketPrefix(prefix);
                    break;//结束，是因为一个括号前面只有一种前缀

                } else {
                    //判断前缀的前面一个字符是否为java命名规范中的字符，如果是，则说明它不是前缀
                    char charAt = express.charAt(position - prefixLength - 1);
                    if (!(charAt + "").matches(pattern)) {
                        bracket.setLeftBracketPrefix(prefix);
                        break;//结束，是因为一个括号前面只有一种前缀
                    }
                }
            }
        }
    }

}
