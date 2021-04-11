package io.walkers.planes.fundhelper.entity.pojo;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 新浪财经接口返回格式
 *
 * @author planeswalker23
 */
@Data
public class SinaResult {
    private Result result;

    /**
     * 获取返回详细数据
     *
     * @return List
     */
    public List<SinaFundValueDetail> getData() {
        if (this.isEmpty()) {
            return null;
        }
        return result.getData().getData();
    }

    /**
     * 获取总数
     *
     * @return Integer
     */
    public Integer getTotalNum() {
        if (this.isEmpty()) {
            return null;
        }
        return Integer.valueOf(result.getData().getTotal_num());
    }

    /**
     * 判空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        if (result == null || result.getData() == null || CollectionUtils.isEmpty(result.getData().getData())) {
            return true;
        }
        return false;
    }
}

@Data
class Result {
    private Status status;
    private DataInResult data;
}

@Data
class Status {
    private Long code;
}

@Data
class DataInResult {
    private List<SinaFundValueDetail> data;
    /**
     * 总数
     */
    private String total_num;
}

