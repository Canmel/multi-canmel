package com.restful.entity.enums;

/**
 *
 *  枚举类
 *
 * @author baily
 * 描述:
 *   ┏ ┓   ┏ ┓
 *  ┏┛ ┻━━━┛ ┻┓
 *  ┃         ┃
 *  ┃    ━    ┃
 *  ┃  ┳┛  ┗┳ ┃
 *  ┃         ┃
 *  ┃    ┻    ┃
 *  ┃         ┃
 *  ┗━━┓    ┏━┛
 *     ┃    ┃神兽保佑
 *     ┃    ┃代码无BUG！
 *     ┃    ┗━━━━━━━┓
 *     ┃            ┣┓
 *     ┃            ┏┛
 *     ┗┓┓┏━━━━━━┳┓┏┛
 *      ┃┫┫      ┃┫┫
 *      ┗┻┛      ┗┻┛
 * @since 2018年08月19日
 */
public enum WorkFlowRectType {
    RECT_TYPE_START("开始", "start"),
    RECT_TYPE_STATE("状态", "state"),
    RECT_TYPE_TASK("任务", "task"),
    RECT_TYPE_FORK("分支", "fork"),
    RECT_TYPE_JOIN("合并", "join"),
    RECT_TYPE_END("结束", "end");

    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    WorkFlowRectType(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
