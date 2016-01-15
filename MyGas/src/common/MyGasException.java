/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package common;
/**
 * MyGas exception class
 * @author zinoo
 *
 */
public class MyGasException  extends Exception{

	public MyGasException() {}
    
    public MyGasException(String message) {
        super(message);
    }

	private static final long serialVersionUID = 1L;

}
