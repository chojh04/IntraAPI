package kr.co.kpcard.backoffice.controller.system.protocol;

import lombok.Getter;
import lombok.Setter;

/**
 * Response for Barcode information
 *
 * @author Chris
 */

@Getter
@Setter
public class ResponseBarcode {
	
	/*
	 * Barcode for card
	 */
	private String barcode;

	/*
	 * Decoded for card
	 */
	private String decoded;

	/*
	 * Verbose(부가정보)
	 */
	private String verbose;

}
