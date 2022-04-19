package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseService {
	
	// 구매
	public void addPurchase(Purchase purchase) throws Exception;
	
	// 구매 정보 확인
	public Purchase getPurchase(int tranNo) throws Exception;
	
	// 구매 목록
	public Map<String, Object> getPurchaseList(Search search) throws Exception;

	// 판매 목록
	public Map<String, Object> getSaleList(Search search) throws Exception;
	
	// 구매 정보 수정
	public void updatePurchase(Purchase purchase) throws Exception;	
	
	// 구매 상태 수정
	public void updateTranCode(Purchase purchase) throws Exception;		
	
}