package io.walkers.planes.fundhelper.service;

import io.walkers.planes.fundhelper.config.FundDataSource;
import io.walkers.planes.fundhelper.entity.dict.FundTypeDict;
import io.walkers.planes.fundhelper.entity.model.FundModel;
import io.walkers.planes.fundhelper.entity.pojo.EastMoneyResult;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 爬虫服务层 based on Jsoup
 *
 * @author planeswalker23
 */
@Slf4j
@Service
public class CrawlerService {

    private static final String TITLE = "fundDetail-tit";
    private static final String INFO = "infoOfFund";
    @Resource
    private FundDataSource fundDataSource;

    public FundModel getFundByCode(String code) {
        String path = fundDataSource.getDetailPathPrefix() + code + fundDataSource.getDetailPathSuffix();
        EastMoneyResult result = new EastMoneyResult();
        try {
            Document doc = Jsoup.connect(path).get();
            // 基金名称
            Node nameNode = doc.getElementsByClass(TITLE).get(0).childNode(0).childNode(0);
            result.setName(nameNode.outerHtml().replace("\n", ""));

            Node infoNode = doc.getElementsByClass(INFO).get(0).childNode(1).childNode(0).childNode(0);
            // 基金类型
            Node typeNode = infoNode.childNode(0).childNode(1).childNode(0);
            result.setType(typeNode.outerHtml());
            // 基金经理
            Node managerNode = infoNode.childNode(2).childNode(1).childNode(0);
            result.setManager(managerNode.outerHtml());
        } catch (Exception e) {
            log.error("Fetch url {} error, following is reason: {}", path, e.getMessage(), e);
            throw new RuntimeException("Fetch fund info error, probably caused by error code");
        }
        return FundModel.builder()
                .name(result.getName())
                .code(code)
                .type(FundTypeDict.containsValue(result.getType()).name())
                .manager(result.getManager())
                .build();
    }
}
