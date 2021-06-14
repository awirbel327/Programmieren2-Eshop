package shop.local.domain.exceptions;

import shop.local.valueobjects.*;

public class PasswortOderUsernameFalschException  extends Exception {

	
	public PasswortOderUsernameFalschException(String zusatzMsg) {
		super(zusatzMsg);
	}
	
}
