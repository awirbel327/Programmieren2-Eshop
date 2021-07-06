package shop.local.domain.exceptions;


public class PasswortOderUsernameFalschException  extends Exception {

	
	public PasswortOderUsernameFalschException(String zusatzMsg) {
		super(zusatzMsg);
	}
	
}
