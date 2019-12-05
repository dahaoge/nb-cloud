package cn.hao.nb.cloud.common.entity;

import cn.hao.nb.cloud.common.util.CheckUtil;
import cn.hao.nb.cloud.common.util.ListUtil;
import com.baomidou.mybatisplus.core.conditions.ISqlSegment;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Description: queryWrapper扩展
 * @Author: scootXin
 * @Date: 2018/12/11 11:31
 */
public class Qw<T> extends QueryWrapper<T> {

    private Qw() {
        super();
    }

    public Map<String, Object> map() {
        String sql = this.getCustomSqlSegment();
        Map<String, Object> map = this.getParamNameValuePairs();
        sql = sql.replace("ew.paramNameValuePairs", "map");
        map.put("sql", sql);
        return map;
    }

    /**
     * 实例化
     *
     * @return
     */
    public static <T> Qw create(T t) {
        Qw qw = new Qw();
        qw.setEntity(t);
        qw.initNeed();

        return qw;
    }

    /**
     * 实例化
     *
     * @param <T>
     * @return
     */
    public static <T> Qw create() {
        Qw qw = new Qw();
        qw.setEntity(Qd.create());
        qw.initNeed();

        return qw;
    }



    /**
     * 倒序
     *
     * @param columns
     * @return
     */
    @Override
    public Qw<T> orderByDesc(String... columns) {
        super.orderByDesc(columns);

        return this;
    }

    /**
     * 正序
     *
     * @param columns
     * @return
     */
    @Override
    public Qw<T> orderByAsc(String... columns) {
        super.orderByAsc(columns);

        return this;
    }

    /**
     * 等于
     *
     * @param column
     * @param val
     * @return
     */
    @Override
    public Qw<T> eq(String column, Object val) {
        super.eq(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * 不等于
     *
     * @param column
     * @param val
     * @return
     */
    @Override
    public Qw<T> ne(String column, Object val) {
        super.ne(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * 大于
     *
     * @param column
     * @param val
     * @return
     */
    public Qw<T> gt(String column, Object val) {
        super.gt(column, val);

        return this;
    }

    /**
     * 大于等于
     *
     * @param column
     * @param val
     * @return
     */
    public Qw<T> ge(String column, Object val) {
        super.ge(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * 小于
     *
     * @param column
     * @param val
     * @return
     */
    public Qw<T> lt(String column, Object val) {
        super.lt(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * 小于等于
     *
     * @param column
     * @param val
     * @return
     */
    public Qw<T> le(String column, Object val) {
        super.le(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * 在 x-x 之间
     *
     * @param column
     * @return
     */
    @Override
    public Qw<T> between(String column, Object start, Object end) {
        super.between(column, start, end);

        return this;
    }

    /**
     * 不在 x-x 之间
     *
     * @param column
     * @return
     */
    public Qw<T> notBetween(String column, Object start, Object end) {
        super.notBetween(column, start, end);

        return this;
    }

    /**
     * like
     *
     * @param column
     * @return
     */
    public Qw<T> like(String column, Object val) {
        super.like(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * notLike
     *
     * @param column
     * @return
     */
    public Qw<T> notLike(String column, Object val) {
        super.notLike(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * likeLeft
     *
     * @param column
     * @return
     */
    public Qw<T> likeLeft(String column, Object val) {
        super.likeLeft(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * likeRight
     *
     * @param column
     * @return
     */
    public Qw<T> likeRight(String column, Object val) {
        super.likeRight(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * isNull
     *
     * @param column
     * @return
     */
    public Qw<T> isNull(String column) {
        super.isNull(column);

        return this;
    }

    /**
     * isNotNull
     *
     * @param column
     * @return
     */
    public Qw<T> isNotNull(String column) {
        super.isNotNull(column);

        return this;
    }

    /**
     * in
     *
     * @param column
     * @return
     */
    public Qw<T> in(String column, Object... val) {
        super.in(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * in
     *
     * @param column
     * @return
     */
    public Qw<T> in(String column, List<Object> val) {
        super.in(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * notIn
     *
     * @param column
     * @return
     */
    public Qw<T> notIn(String column, Object... val) {
        super.notIn(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * notIn
     *
     * @param column
     * @return
     */
    public Qw<T> notIn(String column, List<Object> val) {
        super.notIn(CheckUtil.objIsNotEmpty(val), column, val);

        return this;
    }

    /**
     * 二进制判断
     *
     * @param column
     * @param security
     * @return
     */
    public Qw<T> matchSecurity(String column, Integer... security) {
        this.doIt(true, () -> {
            return "";
        }, MatchKeyword.match, this.matchExpression(this.columnToString(column), ListUtil.getListByArray(security)));

        return this;
    }

    private ISqlSegment matchExpression(String column, Collection<Integer> values) {
        return () -> {
            Sb sb = Sb.create().add("(");

            boolean notFirst = false;
            for (Integer value : values) {
                if (notFirst) {
                    sb.add(" AND ");
                }

                sb.add(column).add(" & ").add(value).add(" > 0");

                notFirst = true;
            }

            sb.add(")");

            return sb.toString();
        };
    }

    @Override
    public Qw<T> clone() {
        return SerializationUtils.clone(this);
    }

    private enum MatchKeyword implements ISqlSegment {
        match("");

        private final String keyword;

        private MatchKeyword(String keyword) {
            this.keyword = keyword;
        }

        @Override
        public String getSqlSegment() {
            return this.keyword;
        }
    }
}
