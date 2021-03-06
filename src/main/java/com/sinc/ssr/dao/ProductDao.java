package com.sinc.ssr.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.sinc.ssr.vo.ProductVO;
import com.sinc.ssr.vo.SavingsVO;
import com.sinc.ssr.vo.StepVO;

@Repository("productDao")
public class ProductDao {

	@Resource(name="sqlSession")
	private SqlSession session;
	
	
	public ArrayList<Object> getProductsByUser(ProductVO productVo, int randomTrend) {
		
		ArrayList<Object> productList = null;
		System.out.println(randomTrend + "in dao");
		
		//Testing 용으로 셋팅
		//randomTrend = 1;
		
		switch(randomTrend) {
		case 0: productList = (ArrayList<Object>) session.selectList("com.sinc.ssr.mybatis.product.getTrendA",productVo);
				break;
		case 1: productList = (ArrayList<Object>) session.selectList("com.sinc.ssr.mybatis.product.getTrendB",productVo);
				break;
		case 2: productList = (ArrayList<Object>) session.selectList("com.sinc.ssr.mybatis.product.getTrendC",productVo);
		        break; 
		case 3: productList = (ArrayList<Object>) session.selectList("com.sinc.ssr.mybatis.product.getTrendD",productVo);
		        break;
		}
		
		System.out.println(productList);
	

		return productList;
	}
	
	public int checkPushNotice(String user_id) {
		
		StepVO stepVO = new StepVO();
		stepVO.setUser_id(Integer.parseInt(user_id));
		
		String pushYN = session.selectOne("com.sinc.ssr.mybatis.product.checkPushYN", stepVO);
		System.out.println("pushYN: " + pushYN.toString());
		if(pushYN == null || pushYN.isEmpty() || pushYN.equals("")) {
			session.insert("com.sinc.ssr.mybatis.product.insertPushYN", stepVO);
			return 0;
		
		}else if (pushYN == "N" || pushYN=="n" || pushYN.equals("N")){
			return 1;
		}
		return 2;
	}
	
	public void updatePushNotice(StepVO stepVO) {
		
			session.update("com.sinc.ssr.mybatis.product.updatePushYN", stepVO);

	}
}
