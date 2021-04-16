package io.walkers.planes.fundhelper.entity.dict;

/**
 * 提示性信息字典
 *
 * @author planeswalker23
 */
public interface MessageDict {

    String SYSTEM_ERROR = "系统异常，请联系管理员";
    String PARAMETER_VALIDATOR_FAILED = "参数校验失败：";
    String FUND_TYPE_NOT_SUPPORT = "基金类型 {%s} 不支持, 请检查基金代码是否正确";
    String DOWNLOAD_FUND_VALUE_FAILED = "获取基金详情失败，请检查基金代码{%s}是否正确";
    String FUND_ALREADY_EXIST_IN_OPTIONAL = "基金{%s}已经加入自选";
    String JSON_FORMAT_FAILED = "JSON序列化失败";
    String NOT_LOGIN = "用户未登录";
    String DATE_FORMAT_FAILED = "日期格式化失败";


    String ACCOUNT_CAN_NOT_BE_NULL = "账户不能为空";
    String PASSWORD_CAN_NOT_BE_NULL = "密码不能为空";
    String WRONG_PASSWORD = "密码错误";
}
