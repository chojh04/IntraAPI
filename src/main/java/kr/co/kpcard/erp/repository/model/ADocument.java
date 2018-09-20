/**
 * 
 */
package kr.co.kpcard.erp.repository.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * FI_ADOCUMENT 테이블 매핑 모델
 * @author chris
 *
 */
@Getter
@Setter
@ApiModel(value="ADocument", description="회계전표모델")
public class ADocument {
	
	@ApiModelProperty(notes="전표번호")
	private String rowIdentifier;
	
	@ApiModelProperty(notes="전표넘버")
	private String rowNo;

	@ApiModelProperty(notes="세금계산서번호")
	private String noTax;

	@ApiModelProperty(notes="회계단위")
	private String cdPc;

	@ApiModelProperty(notes="작성부서")
	private String cdWdept;

	@ApiModelProperty(notes="전표번호")
	private String noDocu;

	@ApiModelProperty(notes="라인번호")
	private long   noDoline;

	@ApiModelProperty(notes="회사코드")
	private String cdCompany;

	@ApiModelProperty(notes="작성사원")
	private String idWrite;

	@ApiModelProperty(notes="전표유형")
	private String cdDocu;

	@ApiModelProperty(notes="회계일자")
	private String dtAcct;

	@ApiModelProperty(notes="승인여부")
	private String stDocu;

	@ApiModelProperty(notes="차대구분")
	private String tpDrcr;

	@ApiModelProperty(notes="계정코드")
	private String cdAcct;

	@ApiModelProperty(notes="금액")
	private long   amt;

	@ApiModelProperty(notes="거래처코드")
	private String cdPartner;

	@ApiModelProperty(notes="거래처명")
	private String nmPartner;
}
