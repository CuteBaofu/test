package com.pinyougou.manager.controller;

import java.util.List;

import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;


@RestController
@RequestMapping("/brand")
public class BrandController {

	@Reference
	private BrandService brandService;

	/**
	 * 查找全部
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbBrand> findAll(){
		return brandService.findAll();		
	}


	/**
	 * 查找一个
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbBrand findOne(Long id){
		return brandService.findOne(id);
	}

	/**
	 * 分页
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page,int rows){
		return brandService.findPage(page,rows);
	}


	/**
	 * 增加
	 * @ RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的);
	 * 				前端不能使用GET方式提交数据，而是用POST方式进行提交。
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand brand){
		System.out.println(brand);
		try {
			brandService.add(brand);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}

	/**
	 * 修改
	 * 思路：
	 * 		1.在点击修改按钮时获取需修改的id-->findOne(id)
	 * 		2.在保存方法中，判断id是否为空
	 * 					为空则实现增加
	 * 				    不为空则实现修改
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand brand){
		try {
			brandService.update(brand);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}



	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public Result delete(Long[] ids){
		try {
			brandService.delete(ids);
			return new Result(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}


	/**
	 * 品牌条件查询功能，输入品牌名称、首字母后查询，并分页。
	 */
	@RequestMapping("/findPageSearch")
	public PageResult findPage(@RequestBody TbBrand tbBrand,int page,int rows){
		return brandService.findPage(tbBrand,page,rows);
	}

}
