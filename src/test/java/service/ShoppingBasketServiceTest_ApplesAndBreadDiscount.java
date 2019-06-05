package service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import model.BasketItem;
import model.Offer;
import model.ShoppingBasket;
import util.Constants;

public class ShoppingBasketServiceTest_ApplesAndBreadDiscount {

	private ArrayList<BasketItem> mockedBasketItemList;
	private ArrayList<Offer> expectedOfferList;

	@Before
	public void init() {
		mockedBasketItemList = this.getMockedBasketItemList();
		expectedOfferList = this.getExpectedOfferList();
	}
	
	public ArrayList<Offer> getExpectedOfferList() {
		ArrayList<Offer> offerList = new ArrayList<Offer>();
		for(BasketItem item : this.mockedBasketItemList) {
			if(item.getItemName().equals(Constants.APPLES)) {
				offerList.add(new Offer(item, Constants.APPLES_PERCENTAGE_OFF, new BigDecimal(0.1).setScale(2, RoundingMode.HALF_EVEN)));
			}
			if(item.getItemName().equals(Constants.SOUP) && item.getQuantity() >=2) {
				offerList.add(new Offer(item, Constants.BREAD_PERCENTAGE_OFF, new BigDecimal(0.4).setScale(2, RoundingMode.HALF_EVEN)));
			}
		}
		return offerList;
	}


	private ArrayList<BasketItem> getMockedBasketItemList() {
		ShoppingBasket shoppingBasket = new ShoppingBasket();
		BasketItem apples_ = new BasketItem();
		apples_.setQuantity(1);
		apples_.setItemCost(Constants.APPLES_PRICE);
		apples_.setItemName(Constants.APPLES);
		shoppingBasket.getItems().add(apples_);

		BasketItem milk_ = new BasketItem();
		milk_.setQuantity(1);
		milk_.setItemCost(Constants.MILK_PRICE);
		milk_.setItemName(Constants.MILK);
		shoppingBasket.getItems().add(milk_);

		BasketItem bread_ = new BasketItem();
		bread_.setQuantity(1);
		bread_.setItemCost(Constants.BREAD_PRICE);
		bread_.setItemName(Constants.BREAD);
		shoppingBasket.getItems().add(bread_);
		
		BasketItem soup = new BasketItem();
		bread_.setQuantity(2);
		bread_.setItemCost(Constants.SOUP_PRICE);
		bread_.setItemName(Constants.SOUP);
		shoppingBasket.getItems().add(soup);

		return shoppingBasket.getItems();
	}
	
	@Test
	public void shoppingCartApplyOffers_applesAndBreadDiscount() {
		ShoppingBasketService shoppingBasketService = new ShoppingBasketServiceImpl();
		ArrayList<Offer> actualOffers = shoppingBasketService.applyOffers(this.mockedBasketItemList);
		ArrayList<Offer> expectedOffers = this.expectedOfferList;
		for(Offer mockerOffer: expectedOffers) {
			for(Offer actualOffer: actualOffers ) {
				Assert.assertEquals(mockerOffer.getDiscount(), actualOffer.getDiscount());	
			}
		}
	}
	
	

}