package io.walkers.planes.fundhelper.entity.dict;

/**
 * Dict of Fund Type
 *
 * @author planeswalker23
 */
public enum FundTypeDict {
    /**
     * 混合型
     */
    MIXED("混合型"),
    /**
     * 股票型
     */
    STOCK("股票型"),
    /**
     * 债券型
     */
    BOND("债券型"),
    /**
     * 指数型
     */
    EXPONENTIAL("指数型"),
    /**
     * FOF型
     */
    FOF("FOF型"),
    /**
     * QDII型
     */
    QDII("QDII型");

    private final String label;

    FundTypeDict(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 判断中文类型名称在不在字段中，不存在返回 null
     *
     * @param typeName 类型名称
     * @return FundTypeDict
     */
    public static FundTypeDict containsValue(String typeName) {
        for (FundTypeDict type : FundTypeDict.values()) {
            if (type.getLabel().equals(typeName)) {
                return type;
            }
        }
        throw new RuntimeException(String.format("基金类型 {%s} 不支持, 请检查基金代码是否正确", typeName));
    }
}
