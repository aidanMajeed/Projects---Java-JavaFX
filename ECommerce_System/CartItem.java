/*
 *  Name: Aidan Majeed
 * 	Student Number: 501084337
 * 
 *
 * class CartItem defines a item for a customer's cart in the syste,
 */
public class CartItem 
{
   private String productOptions;
   private Product product;
   
   public CartItem(Product product, String productOptions)
   {
       this.product = product;
       this.productOptions = productOptions;
   }

// return the products in the system
   public Product getProduct()
   {
       return product;
   }

//return the different product options in the system
   public String getProductOptions()
   {
       return productOptions;
   }

//print different products 
   public void print()
   {
       product.print();
   }

//Two CartItem objects are equal if they have the same product id string.
   public boolean equals(Object other)
	{
		CartItem otherP = (CartItem) other;
		return this.product.getId().equals(otherP.product.getId());
	}
}



   
