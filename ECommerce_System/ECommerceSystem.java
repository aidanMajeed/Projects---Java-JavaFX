/*
 *  Name: Aidan Majeed
 * 	Student Number: 501084337
 * 
 */

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
	Map<String, Product> products = new TreeMap<String, Product>();
	ArrayList<Customer> customers = new ArrayList<Customer>();	
	Map<String, Integer> statistics = new TreeMap<String, Integer>();
	ArrayList<ProductOrder> orders = new ArrayList<ProductOrder>();
	ArrayList<ProductOrder> shippedOrders = new ArrayList<ProductOrder>();

	// These variables are used to generate order numbers, customer id's, product id's 
	int orderNumber = 500;
	int customerId = 900;
	int productId = 700;

	// General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
	String errMsg = null;

	// Random number generator
	Random random = new Random();

	public ECommerceSystem()
	{
		//products
		try
		{
			//create a new file object that reads products.txt
			File productF = new File("products.txt");
			Scanner in = new Scanner(productF);
			int LineCount = 0;
			

			while (in.hasNextLine())
			{
				//since there are 5 lines per product, if divisible by 5
				if(LineCount/5 ==0)
				{
					String cat = in.nextLine();
			

					// if category equals COMPUTERS
					if(cat.equals("COMPUTERS"))
					{
						//advances scanner to next line which is product name
						String name = in.nextLine();
						//advances scanner to next line which is product price
						String strPrice = in.nextLine();
						//advances scanner to next line which is stock amount
						String strStock = in.nextLine();
						
						//returns double price value from String Price
						double price = Double.parseDouble(strPrice);
						//returns int stock value from String stock
						int stock = Integer.parseInt(strStock);

						//create a new product that takes name of product, id, double price, int stock, and specfied product category
						Product computerProduct = new Product(name, generateProductId(), price, stock, Product.Category.COMPUTERS);
						//put product object and it's corresponding ID in product map
						products.put(computerProduct.getId(), computerProduct);
						//put name of product, its id, and set its order value to zero, all within statistics map
						statistics.put(name + " " + computerProduct.getId(), 0);
					}
					
					// process repeats from previous if statement until books
					else if(cat.equals("FURNITURE"))
					{
						String name = in.nextLine();
						String strPrice = in.nextLine();
						String strStock = in.nextLine();
						
						double price = Double.parseDouble(strPrice);
						int stock = Integer.parseInt(strStock);
						Product furnProduct = new Product(name, generateProductId(), price, stock, Product.Category.FURNITURE);
						products.put(furnProduct.getId(), furnProduct);
						statistics.put(name + " " + furnProduct.getId(), 0);
					}
	
					else if(cat.equals("CLOTHING"))
					{
						String name = in.nextLine();
						String strPrice = in.nextLine();
						String strStock = in.nextLine();
						
						double price = Double.parseDouble(strPrice);
						int stock = Integer.parseInt(strStock);
						Product clotheProduct = new Product(name, generateProductId(), price, stock, Product.Category.CLOTHING);
						products.put(clotheProduct.getId(), clotheProduct);
						statistics.put(name + " " + clotheProduct.getId(), 0);
					}
	
					else if(cat.equals("GENERAL"))
					{
						String name = in.nextLine();
						String strPrice = in.nextLine();
						String strStock = in.nextLine();
											
						double price = Double.parseDouble(strPrice);
						int stock = Integer.parseInt(strStock);
						Product genProduct = new Product(name, generateProductId(), price, stock, Product.Category.GENERAL);
						products.put(genProduct.getId(), genProduct);
						statistics.put(name + " " + genProduct.getId(), 0);
					}

					else if(cat.equals("BOOKS"))
					{
						String name = in.nextLine();
						String strPrice = in.nextLine();
						//create string objects for paperback stock, hardcover stock, title, author, and year
						String paperStock = "", hardStock = "";
						String title = "", author = "", year = "";

						//create new string representing scanner line that states stock for both formats
						String typeStock = in.nextLine();
						
						//split stock into seperate lines
						String[] temp = typeStock.split(" ");
						paperStock = temp[0];
						hardStock = temp[1];

						//create new string representing scanner line that states title, author, and published year
						String details = in.nextLine();
						
						//split details into seperate lines
						String[] temp2 = details.split(":");
						title = temp2[0];
						author = temp2[1];
						year = temp2[2];

						double price = Double.parseDouble(strPrice);
						int paperbackStock = Integer.parseInt(paperStock);
						int hardcoverStock = Integer.parseInt(hardStock);
						int published = Integer.parseInt(year);


						Book bookProduct = new Book(name, generateProductId(), price, paperbackStock, hardcoverStock, title, author, published);
						
						products.put(bookProduct.getId(), bookProduct);
						statistics.put(name + " " + bookProduct.getId(), 0);
					}
				}
			}
				
		
		}	

		// catch exception if file is not found
		catch (IOException e)
		{
			System.out.println("File not Found");
			System.exit(1);
		}
		
		// Create some customers
		customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
		customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
		customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
		customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));
	}

	private String generateOrderNumber()
	{
		return "" + orderNumber++;
	}

	private String generateCustomerId()
	{
		return "" + customerId++;
	}

	private String generateProductId()
	{
		return "" + productId++;
	}

	public String getErrorMessage()
	{
		return errMsg;
	}

	public void printAllProducts()
	{
		for (Product p : products.values())
			p.print();
	}

	public void printAllBooks()
	{
		for (Product p : products.values())
		{
			if (p.getCategory() == Product.Category.BOOKS)
				p.print();
		}
	}

	public ArrayList<Book> booksByAuthor(String author)
	{
		ArrayList<Book> books = new ArrayList<Book>();
		for (Product p : products.values())
		{
			if (p.getCategory() == Product.Category.BOOKS)
			{
				Book book = (Book) p;
				if (book.getAuthor().equals(author))
					books.add(book);
			}
		}
		return books;
	}

	public void printAllOrders()
	{
		for (ProductOrder o : orders)
			o.print();
	}

	public void printAllShippedOrders()
	{
		for (ProductOrder o : shippedOrders)
			o.print();
	}

	public void printCustomers()
	{
		for (Customer c : customers)
			c.print();
	}
	/*
	 * Given a customer id, print all the current orders and shipped orders for them (if any)
	 */
	public boolean printOrderHistory(String customerId)
	{
		// Make sure customer exists
		int index = customers.indexOf(new Customer(customerId));
		if (index == -1)
		{
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}	
		System.out.println("Current Orders of Customer " + customerId);
		for (ProductOrder order: orders)
		{
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
		System.out.println("\nShipped Orders of Customer " + customerId);
		for (ProductOrder order: shippedOrders)
		{
			if (order.getCustomer().getId().equals(customerId))
				order.print();
		}
		return true;
	}

	public String orderProduct(String productId, String customerId, String productOptions)
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, set errMsg and return null
    	// else get the Customer object
      
      // create a new customer object
      Customer objCustomer = null; 
      // iterate through customer list
      for (Customer cus : customers)
      {
        // if id in list is equal to customerId string
        if (cus.getId().equals(customerId))
        {
          // get customer object
          objCustomer = cus;
          break;
        }
      }
        // if customer object does not exist call error
        if(objCustomer == null)
        {
          throw new UnknownCustomerException("Customer " + customerId + " Not Found");
        }
        
      
    	// Check to see if product object with productId exists in array list of products
    	// if it does not, set errMsg and return null
    	// else get the Product object 
      
      // create a new product object
      Product objProduct = null;
      // iterate through product list
      for (Product p: products.values() )
      {
        // if id in list is equal to productId string
        if (p.getId().equals(productId))
        {
          // get product object
          objProduct = p;
          break;
        }
      }
      // if product object does not exist call error 
      if(objProduct == null)
        {
          throw new UnknownProductIDException("Product " + productId + " Not Found");
        }
      

    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method validOptions()
    	// If options are not valid, set errMsg string and return null;

    	for (Product y: products.values() )
      {
        // if id equals string productId and valid options inputted are false, call error
        if((y.getId().equals(productId) && y.validOptions(productOptions)== false))
        {
          throw new InvalidOrderException("Product " + y.getCategory() + " ProductId " + productId + " Invalid Option: " + productOptions);
        }

        
      }

    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, set errMsg string and return null

      for (Product v: products.values() )
      {
        // if id equals string productId and stock count is 0, call error
        if((v.getId().equals(productId) && v.getStockCount(productOptions) == 0))
        {
          throw new OutOfStockException("Product " + v.getCategory() + " ProductId " + productId + " Stock Not Available");
        }

      }
      
      // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string

      // string orderNum generates new order number
      String orderNum = generateOrderNumber();
      for (Product m: products.values() )
      {

      // if id equals string productId and stock count is not 0, reduce stock count
        if (m.getId().equals(productId) && m.getStockCount(productOptions) != 0)
        {
        m.reduceStockCount(productOptions);
        }
      }


	  for(String x: statistics.keySet())
	  {
		  if(x.contains(productId))
		  {
			  statistics.put(x, statistics.get(x)+1);
		  }
	  }


      // create new ProductOrder object that contains order number, customer and product objects, and all valid options
    	ProductOrder productOrders = new ProductOrder(orderNum, objProduct, objCustomer, productOptions);
      // add new object to orders list
      orders.add(productOrders);
      return "Order #" + orderNum;
    	
    }

	/*
	 * Create a new Customer object and add it to the list of customers
	 */

	public boolean createCustomer(String name, String address)
	{
		// Check to ensure name is valid
		if (name == null || name.equals(""))
		{
			throw new InvalidCustomerException("Invalid Customer Name " + name);
			
		}
		// Check to ensure address is valid
		if (address == null || address.equals(""))
		{
			throw new InvalidAddressException("Invalid Customer Address " + address);
		}
		Customer customer = new Customer(generateCustomerId(), name, address);
		customers.add(customer);
		return true;
	}

	public ProductOrder shipOrder(String orderNumber)
	{
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber,null,null,""));
		if (index == -1)
		{
			throw new InvalidOrderException("Order " + orderNumber + " Not Found");
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
		shippedOrders.add(order);
		return order;
	}

	/*
	 * Cancel a specific order based on order number
	 */
	public boolean cancelOrder(String orderNumber)
	{
		// Check if order number exists
		int index = orders.indexOf(new ProductOrder(orderNumber,null,null,""));
		if (index == -1)
		{
			throw new InvalidOrderException("Order " + orderNumber + " Not Found");
			
		}
		ProductOrder order = orders.get(index);
		orders.remove(index);
		return true;
	}

	// Sort products by increasing price
	public void sortByPrice()
	{
		// add values of map product to array list, sort, then print
		ArrayList<Product> sortPrice = new ArrayList<Product>(products.values());
		Collections.sort(sortPrice, new PriceComparator());
		for (Product x: sortPrice)
		{
			x.print();
		}
			
	}

	private class PriceComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			if (a.getPrice() > b.getPrice()) return 1;
			if (a.getPrice() < b.getPrice()) return -1;	
			return 0;
		}
	}

	// Sort products alphabetically by product name
	public void sortByName()
	{
		// add values of map product to array list, sort, then print
		ArrayList<Product> sortName = new ArrayList<Product>(products.values());
		Collections.sort(sortName, new NameComparator());
		for (Product x: sortName)
		{
			x.print();
		}
		
	}

	private class NameComparator implements Comparator<Product>
	{
		public int compare(Product a, Product b)
		{
			return a.getName().compareTo(b.getName());
		}
	}

	// Sort products alphabetically by product name
	public void sortCustomersByName()
	{
		Collections.sort(customers);
	}

// Sort product order statisitcs from greatest to least
	public void sortStats()
	{
		//LinkedHashMap preserve the ordering of elements
		LinkedHashMap<String, Integer> increasing = new LinkedHashMap<>();

		//use stream and reversed to sort map in reversed order
		statistics.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEachOrdered(x -> increasing.put(x.getKey(), x.getValue()));
		
		  for(String x : increasing.keySet())
		  {
			  
			  String keyString = x.toString();
			  //return a string representing integer value
			  String valueString = increasing.get(x).toString();
			  System.out.println(keyString + "  Times Ordered: " + valueString);
		  }

		
	}



// method to add customer's items to cart - reuses code from orderProduct with slight additions
public String addToCart(String productId, String customerId, String productOptions)
    {
      
      // create a new customer object
      Customer objCustomer = null; 
      // iterate through customer list
      for (Customer cus : customers)
      {
        // if id in list is equal to customerId string
        if (cus.getId().equals(customerId))
        {
          // get customer object
          objCustomer = cus;
          break;
        }
      }

        // if customer object does not exist call error
        if(objCustomer == null)
        {
          throw new UnknownCustomerException("Customer " + customerId + " Not Found");
        }
        

      // create a new product object
      Product objProduct = null;
      // iterate through product list
      for (Product p: products.values() )
      {
        // if id in list is equal to productId string
        if (p.getId().equals(productId))
        {
          // get product object
          objProduct = p;
          break;
        }
      }

      // if product object does not exist call error 
      if(objProduct == null)
        {
          throw new UnknownProductIDException("Product " + productId + " Not Found");
        }

    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
      for (Product y: products.values() )
      {
        // if id equals string productId and valid options inputted are false, call error
        if((y.getId().equals(productId) && y.validOptions(productOptions)== false))
        {
          throw new InvalidOrderException("Product " + y.getCategory() + " ProductId " + productId + " Invalid Option: " + productOptions);
        }   
      }

    	// Check if the product has stock available (i.e. not 0)
      for (Product v: products.values() )
      {
        // if id equals string productId and stock count is 0, call error
        if((v.getId().equals(productId) && v.getStockCount(productOptions) == 0))
        {
          throw new OutOfStockException("Product " + v.getCategory() + " ProductId " + productId + " Stock Not Available");
        }
      }
      
    
      for (Product m: products.values() )
      {

      // if id equals string productId and stock count is not 0, reduce stock count
        if (m.getId().equals(productId) && m.getStockCount(productOptions) != 0)
        {
        m.reduceStockCount(productOptions);
        }
      }
	  
	  //creates a cart item with product object and product format
	  CartItem c = new CartItem(objProduct, productOptions);

	  //adds cart item to cart
	  objCustomer.CusCart.addCart(c);
	  return "\n Product " + objProduct.getId() + " has been added to the cart";
    	




    }

//method to remove item from cart
	public String removeOrder(String customerID, String productID)
	{
		//check if product id exists
		Product objProduct = null;
		
		for (Product p: products.values() )
		 {
		   
		   if (p.getId().equals(productID))
		   {
			 objProduct = p;
			 break;
		   }
		 }

		// if product does not exist
		if(objProduct == null)
		{
			throw new UnknownProductIDException("Product " + productId + " Not Found");
		}
	
		//check if customer exists
		Customer existCus = null; 
		for (Customer x : customers)
		 {
		   if (x.getId().equals(customerID))
		   {
			existCus = x;
			break;
		   }
		 }

		//if customer does not exist throw exception error
		if(existCus == null)
		{
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}

		//create a new cart item that takes product object and id
		CartItem cartExist = new CartItem(objProduct, productID); 

		//remove item from cart
		existCus.CusCart.removeCart(cartExist);
		return "Item has been removed from the cart";
	}


	//method to print customer's cart
	public void printCartMethod(String customerID)
	{
		//checks if customer exists
		Customer printCusItem = null; 
		for (Customer x : customers)
		{
		   if (x.getId().equals(customerID))
			{
				printCusItem = x;
			break;
		   	}
		}
		//if customer does not exist throw exception error
		if(printCusItem == null)
		{
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}
		
		//print cart
		printCusItem.CusCart.cartprint();
	}


	//method to order items in customer's cart
	public void orderItems(String customerID)
	{
		//checks if customer exists
		Customer orderCusItem = null; 
		for (Customer x : customers)
		{
		   if (x.getId().equals(customerID))
			{
				orderCusItem = x;

				//iterates through cart list
				for(CartItem c : orderCusItem.CusCart.getCart())
				{
					//call orderProduct method to create an valid order
					orderProduct(c.getProduct().getId(), customerID, c.getProductOptions());
					//reduce stock count after order
					c.getProduct().reduceStockCount(c.getProductOptions());
				}
				//after cart, clear order
				orderCusItem.CusCart.clearCart();
				System.out.println("Items have been ordered.");
		   	}
		}
		//if customer does not exist throw exception error
		if(orderCusItem == null)
		{
			throw new UnknownCustomerException("Customer " + customerId + " Not Found");
		}
	
	}
}

//Custom exceptions that throw errors for specific situations.
// Has default constructor, and another that accepts a message


//Exception for unknown customer
class UnknownCustomerException extends RuntimeException
{
	public UnknownCustomerException(){}

	public UnknownCustomerException(String errMsg)
	{
		super(errMsg);
	}
}


//Exception for unknown product
class UnknownProductIDException extends RuntimeException
{
	public UnknownProductIDException(){}

	public UnknownProductIDException(String errMsg)
	{
		super(errMsg);
	}
}

//Exception for invalid order
class InvalidOrderException extends RuntimeException
{
	public InvalidOrderException(){}

	public InvalidOrderException(String errMsg)
	{
		super(errMsg);
	}
}

//Exception for invalid product option
class InvalidProductException extends RuntimeException
{
	public InvalidProductException(){}

	public InvalidProductException(String errMsg)
	{
		super(errMsg);
	}
}

//Exception for out of stock product
class OutOfStockException extends RuntimeException
{
	public OutOfStockException(){}

	public OutOfStockException(String errMsg)
	{
		super(errMsg);
	}
}

//Exception for invalid customer name
class InvalidCustomerException extends RuntimeException
{
	public InvalidCustomerException(){}

	public InvalidCustomerException(String errMsg)
	{
		super(errMsg);
	}
}

//Exception for invalid customer address
class InvalidAddressException extends RuntimeException
{
	public InvalidAddressException(){}

	public InvalidAddressException(String errMsg)
	{
		super(errMsg);
	}
}

