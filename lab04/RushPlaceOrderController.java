package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class RushPlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the avalibility of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	
    }
    
    /**
     * Phuong thuc kiem tra san pham co duoc phep giao nhanh hay khong
     * @param productList: danh sach san pham duoc dat hang, rushPlaceOrderProductList: danh sach san pham duoc phep giao nhanh
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean validateProduct(String[] productList, String[] rushPlaceOrderProductList) {
    	for(String product: productList) {
    		boolean flag = false;
    		for(String pd: rushPlaceOrderProductList) { // Neu san pham thuoc muc giao nhanh, flag = true va tra ve true
    			if(pd.equals(product)) flag = true;
    		}
    		if(flag==false) return false; // neu khong tra ve false
    		else return true;
    	}
    	return false;
    }
    
    /**
     * Phuong thuc kiem tra dia chi co duoc phep giao nhanh hay khong
     * @param address: dia chi nhan hang, rushPlaceOrderAddress: danh sach dia chi duoc phep giao nhanh
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean validateAddress(String address, String[] rushPlaceOrderAddress) {
    	boolean flag = false;
    	for(String ad: rushPlaceOrderAddress) { // neu dia chi thuoc muc giao nhanh tra ve true
			if(ad.equals(address)) flag = true;
		}
		if(flag==false) return false;
		else return true;
    }
    
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
