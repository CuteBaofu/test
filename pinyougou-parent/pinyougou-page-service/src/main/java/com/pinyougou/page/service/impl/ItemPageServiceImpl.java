package com.pinyougou.page.service.impl;

import com.pinyougou.mapper.TbGoodsDescMapper;
import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.page.service.ItemPageService;
import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private TbGoodsMapper goodsMapper;

    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    @Value("${pagedir}")
    private String pagedir;

    @Override
    public boolean genItemHtml(Long goodsId)  {
        Configuration configuration = new Configuration(Configuration.getVersion());
        //加载一个模板，创建一个模板对象
        Template template = null;
        try {
            template = configuration.getTemplate("item.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建一个模板使用的数据集，可以是 pojo 也可以是 map。一般是 Map
        Map dataModel=new HashMap<>();

        //1.加载商品表数据
        TbGoods goods = goodsMapper.selectByPrimaryKey(goodsId);
        dataModel.put("goods",goods);

        //2.加载商品扩展表数据
        TbGoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);
        dataModel.put("goodsDesc",goodsDesc);

        Writer out = null;
        try {
            out = new FileWriter(pagedir+goodsId+".html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //调用模板对象的 process 方法输出文件。
        try {
            template.process(dataModel,out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
