package com.pinyougou.sellergoods.service.impl;

import java.util.List;

import com.pinyougou.pojo.TbBrandExample;
import entity.PageResult;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;


@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper brandMapper;
	
	@Override
	public List<TbBrand> findAll() {

		return brandMapper.selectByExample(null);
	}

	/**
	 * 品牌分页
	 * @param pageNum 当前页面
	 * @param pageSize 每页记录数
	 *        Page继承了ArrayLis
	 *
	 * 思路：
	 * 		1.后端给前端提供当前页、总记录数、当前页条数...
	 * 		2.前端给后端需要提供pageNum（当前页码）、pageSize（当前页记录数），因为需要告诉后端当前页码及记录数以方便后端发送相应数据
	 * 	    3.使用PageHelper工具类的startPage方法进行传递
	 * 	    4.把传递的数据封装进Page<E>
	 */
	public PageResult findPage(int pageNum,int pageSize){
		PageHelper.startPage(pageNum,pageSize);
		Page<TbBrand> tbBrands = (Page<TbBrand>)brandMapper.selectByExample(null);
		//getTotal:总数     getResult:当前页结果(因为当前结果为list集合)
		return new PageResult(tbBrands.getTotal(),tbBrands.getResult());
	}

	@Override
	public void add(TbBrand brand) {
		System.out.println(brand);
		brandMapper.insert(brand);
	}

	@Override
	public TbBrand findOne(Long id) {
		return brandMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(TbBrand brand) {
		brandMapper.updateByPrimaryKey(brand);
	}

	@Override
	public void delete(Long[] ids) {				
		for(Long id:ids){
			brandMapper.deleteByPrimaryKey(id);
		}		
	}

	@Override
	public PageResult findPage(TbBrand brand, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		TbBrandExample example = new TbBrandExample();
		TbBrandExample.Criteria criteria = example.createCriteria();
		if (brand!=null){
			if (brand.getName()!=null&brand.getName().length()>0){
				criteria.andNameLike("%"+brand.getName()+"%");
			}
			if (brand.getFirstChar()!=null&brand.getFirstChar().length()>0){
				criteria.equals(brand.getFirstChar());
			}
		}
		Page<TbBrand> tbBrands = (Page<TbBrand>)brandMapper.selectByExample(null);
		//getTotal:总数     getResult:当前页结果(因为当前结果为list集合)
		return new PageResult(tbBrands.getTotal(),tbBrands.getResult());
	}


}
