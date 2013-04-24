package model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="prestashop")
public class PrestashopCustomer {
	@Element(name="customer")
	private User user;
	
	public PrestashopCustomer(User user){
		this.user = user;
	}
}
