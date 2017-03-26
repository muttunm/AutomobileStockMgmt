package com.automobile.sms.util;

import java.util.ArrayList;
import java.util.List;

import com.automobile.sms.dao.StockItemDaoImpl;
import com.automobile.sms.model.StockItem;

public class TransactionUtil {
	/*public List<StockItem>  getDealerStockItemDetails(DealerTransactionDetails dealerTransactionDetails){
		
		StockItemDaoImpl stockItemDaoImpl = new StockItemDaoImpl();
		String[] splittedStockItemIds = dealerTransactionDetails.getSeperatedStockItemIds().split(",");
		String reCombinedStockItemIds = new String("");
		for(int i=0;i<splittedStockItemIds.length;i++){
			if(i<splittedStockItemIds.length-1)
				reCombinedStockItemIds+="'"+splittedStockItemIds[i]+"',";
			else{
				reCombinedStockItemIds+="'"+splittedStockItemIds[i]+"'";
			}
		}
		List<StockItem> storedStockItems = stockItemDaoImpl.getStockItemsList(reCombinedStockItemIds);
		List<StockItem> stockItems = new ArrayList<StockItem>();
		String[] stockItemIds = dealerTransactionDetails.getSeperatedStockItemIds() != null ? dealerTransactionDetails.getSeperatedStockItemIds().split(","):null;
		String[] stockItemQuantities = dealerTransactionDetails.getSeperatedStockItemQuantities() != null ? dealerTransactionDetails.getSeperatedStockItemQuantities().split(","):null;
		
		if(stockItemIds != null && stockItemQuantities != null){
			for(int i=0;i<stockItemIds.length;i++){
				StockItem stockItem = new StockItem();
				stockItem.setStockItemId(Long.parseLong(stockItemIds[i]));
				stockItem.setQuantity(String.valueOf(Integer.parseInt(stockItemQuantities[i])+Integer.parseInt(storedStockItems.get(i).getQuantity())));
				stockItems.add(stockItem);
			}
		}
		return stockItems;
	}*/
	
	
public List<StockItem>  getStockItemDetails(String seperatedStockItemIds){
		
		StockItemDaoImpl stockItemDaoImpl = new StockItemDaoImpl();
		String[] splittedStockItemIds = seperatedStockItemIds.split(",");
		String reCombinedStockItemIds = new String("");
		for(int i=0;i<splittedStockItemIds.length;i++){
			if(i<splittedStockItemIds.length-1)
				reCombinedStockItemIds+="'"+splittedStockItemIds[i]+"',";
			else{
				reCombinedStockItemIds+="'"+splittedStockItemIds[i]+"'";
			}
		}
		List<StockItem> storedStockItems = stockItemDaoImpl.getStockItemsList(reCombinedStockItemIds);
		return storedStockItems;
	}
public List<StockItem>  updateCustomerStockItems(String seperatedStockItemIds,String seperatedStockItemQuantities, List<StockItem> storedStockItems){
	
	List<StockItem> stockItems = new ArrayList<StockItem>();
	String[] stockItemIds = seperatedStockItemIds != null ? seperatedStockItemIds.split(","):null;
	String[] stockItemQuantities = seperatedStockItemQuantities != null ? seperatedStockItemQuantities.split(","):null;
	
	if(stockItemIds != null && stockItemQuantities != null){
		for(int i=0;i<stockItemIds.length;i++){
			StockItem stockItem = new StockItem();
			stockItem.setStockItemId(Long.parseLong(stockItemIds[i]));
			stockItem.setQuantity(String.valueOf(Integer.parseInt(storedStockItems.get(i).getQuantity())-Integer.parseInt(stockItemQuantities[i])));
			stockItems.add(stockItem);
		}
	}
	return stockItems;
}
public List<StockItem>  updateDealerStockItems(String seperatedStockItemIds,String seperatedStockItemQuantities, List<StockItem> storedStockItems){
	
	List<StockItem> stockItems = new ArrayList<StockItem>();
	String[] stockItemIds = seperatedStockItemIds != null ? seperatedStockItemIds.split(","):null;
	String[] stockItemQuantities = seperatedStockItemQuantities != null ? seperatedStockItemQuantities.split(","):null;
	
	if(stockItemIds != null && stockItemQuantities != null){
		for(int i=0;i<stockItemIds.length;i++){
			StockItem stockItem = new StockItem();
			stockItem.setStockItemId(Long.parseLong(stockItemIds[i]));
			stockItem.setQuantity(String.valueOf(Integer.parseInt(storedStockItems.get(i).getQuantity())+Integer.parseInt(stockItemQuantities[i])));
			stockItems.add(stockItem);
		}
	}
	return stockItems;
}
}
