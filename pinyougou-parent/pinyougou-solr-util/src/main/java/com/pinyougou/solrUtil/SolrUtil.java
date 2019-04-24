package com.pinyougou.solrUtil;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SolrUtil {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 导入商品数据
     * 思路：
     *        1.通过Mapper来查找商品数据
     *        2.通过solrTemplate来存储并提交
     */
    public void importItemData(){

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo("1");//已审核通过的商品

        List<TbItem> items = tbItemMapper.selectByExample(example);

        for (TbItem item : items) {
            System.out.println("商品名称为:"+item.getTitle()+"\t商品价格为:"+item.getPrice());

            Map specMap = JSON.parseObject(item.getSpec());
            item.setSpecMap(specMap);

        }

        solrTemplate.saveBean(items);
        solrTemplate.commit();

    }


    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath*:sping/applicationContext*.xml");
        SolrUtil  solrUtil = (SolrUtil) context.getBean("solrUtil");
        solrUtil.importItemData();
    }

}
