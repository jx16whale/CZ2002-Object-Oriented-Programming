package Database;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import Entity.Order;
import Entity.Item;

/**
 * Contains the methods to read and save data into and from the Order text file.
 */
public class OrderDB implements DB {
	/**
	 * Divides the variables and data in the txt file
	 */
	public static final String SEPARATOR = "|";

	/**
	 * Read the whole Order.txt file.
	 * @param filename Order.txt file to be read.
	 * @return orderList arraylist of all the Order in the Order.txt file
	 */
	@Override
	public ArrayList<Order> read(String fileName) throws IOException {
		ArrayList<String> stringArray = (ArrayList<String>) ReadinFile.read(fileName);
		ArrayList<Order> orderList = new ArrayList<Order>();
		
		
		for (int i = 0; i < stringArray.size(); i++) {
			String st = stringArray.get(i);
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);
			ArrayList<Item> items = new ArrayList<Item>();
			
			int orderId = Integer.valueOf(star.nextToken().trim());
		    String tableId = star.nextToken().trim();
		    String employeeId = star.nextToken().trim();
		    String membership = star.nextToken().trim();
		    String reservationNum = star.nextToken().trim();
		    boolean isPrintedInvoice = Boolean.valueOf(star.nextToken().trim());
		    String date = star.nextToken().trim();

			
		    while(star.hasMoreTokens()) {
		    	int itemID = Integer.valueOf(star.nextToken().trim());
		    	String name = star.nextToken().trim();
		    	String desc = star.nextToken().trim();	
		    	double price = Double.valueOf(star.nextToken().trim());
		    	int type = Integer.valueOf(star.nextToken().trim());
		    	Item item = new Item(itemID, name, desc, price, type);
		    	items.add(item);
		    }
		    
		    Order order = new Order(orderId, tableId, employeeId, membership, reservationNum, isPrintedInvoice, items, date);		    
		    
		    orderList.add(order);
		}
		System.out.println(stringArray.size() + " Order(s) Loaded.");
		return orderList;
	}
	
	/**
	 * save a list of order objects into Order.txt file.
	 * @param filename  Order.txt file to be read.
	 * @param orderList  the list of orders to be saved in the order.txt file.
	 */
	@Override
	public void save(String filename, List orderList) throws IOException {
		ArrayList<String> ordersw = new ArrayList<String>();
		for (int i = 0; i < orderList.size(); i++) {
			Order order = (Order) orderList.get(i);
			StringBuilder st = new StringBuilder();
			st.append(order.getOrderId());
			st.append(SEPARATOR);
			st.append(order.gettableId());
			st.append(SEPARATOR);
			st.append(order.getStaffId());
			st.append(SEPARATOR);
			st.append(order.getMembership());
			st.append(SEPARATOR);
			st.append(order.getReservationNum());
			st.append(SEPARATOR);
			st.append(Boolean.toString(order.getIsPrintedInvoice()));
			st.append(SEPARATOR);
			st.append(order.getDate());
			st.append(SEPARATOR);
			
			
			ArrayList<Item> items = order.getItems();
			for(Item item : items) {
				st.append(item.getItemId());
				st.append(SEPARATOR);
				st.append(item.getName());
				st.append(SEPARATOR);
				st.append(item.getDesc());
				st.append(SEPARATOR);
				st.append(item.getPrice());
				st.append(SEPARATOR);
				st.append(item.getType());
				st.append(SEPARATOR);
			}
			ordersw.add(st.toString());
		}
		
		ReadinFile.write(filename, ordersw);
	}
}