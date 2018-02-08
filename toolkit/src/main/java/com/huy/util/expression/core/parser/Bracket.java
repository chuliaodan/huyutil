package com.huy.util.expression.core.parser;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表达式计算 括号类
 * Created by huyong on 2017/5/24.
 */
@Data
@NoArgsConstructor
public class Bracket {

    /**
     * 开始符号：如( [ {
     */
    private Character startSymble;

    /**
     * 结束符号：如 ) ] }
     */
    private Character endSymble;

    /**
     * 左括号在表达式中的位置
     */
    private Integer leftBracketPosition;

    /**
     * 右括号在表达式中的位置
     */
    private Integer rightBracketPosition;

    /**
     * 左括号左边的前缀
     */
    private String leftBracketPrefix;

    public Bracket(Character startSymble) {
        this.startSymble = startSymble;
    }

    public Bracket(Character startSymble, Integer leftBracketPosition) {
        this.startSymble = startSymble;
        this.leftBracketPosition = leftBracketPosition;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj instanceof Bracket) {
            Bracket bracket = (Bracket) obj;
            if (this.getStartSymble() == bracket.getStartSymble()
                    && this.getLeftBracketPrefix().equals(bracket.getLeftBracketPrefix())
                    && this.getLeftBracketPosition() == bracket.getLeftBracketPosition()
                    && this.getRightBracketPosition() == bracket.getRightBracketPosition()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
