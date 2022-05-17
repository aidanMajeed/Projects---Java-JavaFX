/*
 *  Name: Aidan Majeed
 * 	Student Number: 501084337
 * 
 */

import java.util.ArrayList;

public class Cart 
{
    //CartItem array list of items in cart
    private ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

//add items to cart
    public void addCart(CartItem x)
    {
        cartItems.add(x);
    }

//checks if cart items exist
    public boolean find(CartItem i)
    {
        for(CartItem p : cartItems)
        {
            if(p.equals(i))
            {
                return true;
            }
        }
        return false;
    }

//if it does exist, item can now be removed
    public void removeCart(CartItem y)
    {
        if(find(y) == true)
        {
            cartItems.remove(y);
        }
    }

//return array list cartItems
   public ArrayList<CartItem> getCart()
   {
    return cartItems;
   } 

//clear cartItems arraylist
   public ArrayList<CartItem> clearCart()
   {
    cartItems.clear();
    return cartItems;
   }

//print items in cart
    public void cartprint()
    {
        for(CartItem p: cartItems)
        {
            p.getProduct().print();
        }
    }
}
