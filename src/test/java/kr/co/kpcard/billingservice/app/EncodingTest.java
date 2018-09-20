package kr.co.kpcard.billingservice.app;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bnl.brts.service.BrtsBarCodeLib;
import com.google.gson.Gson;

import kr.co.kpcard.backoffice.component.SystemCodeConstant;
import kr.co.kpcard.backoffice.repository.approval.NewApprovalRepository;
import kr.co.kpcard.backoffice.repository.approval.model.Approval;
import kr.co.kpcard.backoffice.repository.approval.model.ApprovalContent;
import kr.co.kpcard.backoffice.repository.approval.model.CardBalanceRefund;
import kr.co.kpcard.backoffice.repository.approval.model.CardRestrictUsage;
import kr.co.kpcard.backoffice.service.approval.NewApprovalService;
import kr.co.kpcard.backoffice.service.card.ReqBalanceRefundInfo;
import kr.co.kpcard.billingservice.app.service.erp.FiAdocuService;
import kr.co.kpcard.common.utils.EncodeString;
import kr.co.kpcard.common.utils.StringUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncodingTest {
	BrtsBarCodeLib barcode = new BrtsBarCodeLib(); 
	EncodeString EncodeString = new EncodeString();
	StringUtil util = new StringUtil();
	//@Test
	public void test(){
		System.out.println("encoding : ");
		System.out.println(EncodeString.encodeSha512("dndbrlvmxm"));
		System.out.println("");
	}
	
	//@Test
	public void stringTest(){
		String text = "";
		int length=6;
		String result = util.padRight(text, length);
		System.out.println("asd"+result+"asd");
	}
	
	//@Test
	public void StringEncoding(){
		
		String decodingNum = "3200000001400";
		int len = 9;
		String eccodingNum = barcode.EncodeBy(decodingNum, len);
		
		System.out.println("");
		System.out.println("");
		System.out.println("decodingNum : "+decodingNum+"/// eccodingNum : "+eccodingNum);
		System.out.println("");
		System.out.println("");
		
	}

}
