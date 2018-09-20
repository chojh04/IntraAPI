package com.bnl.brts.service;

public class BrtsBarCodeLib {
	public static native String Encode(String instr);
    public static native String Decode(String instr);
    public static native String EncodeByLen(String instr, int len);
	public static native String DecodeByLen(String instr, int len);
	
    static 
    {
        System.loadLibrary("barcode");
    	
    	//local 환경 Library file load
    	//System.load("d:/barcode.dll");
    	
    	//server Library file load
        //System.load("/home/backadm/billingService/bin/libbarcode.so");
    }
    
    public BrtsBarCodeLib() {}

    //public String GetEncBarCodeNumber ( String instr ) {
    public String Encoding ( String instr ) {
    	String outstr = "";
    	outstr = Encode(instr);
    	
    	return outstr;
    }
    
    //public String GetDecBarCodeNumber ( String instr ) {
    public String Decoding ( String instr ) {
    	String outstr = "";
        outstr = Decode(instr);
    	
    	return outstr;
    }
    
    public String EncodeBy ( String instr, int len ) {
    	String outstr = "";
    	
    	if(instr != null && instr.length() > 0)
    	{
    		outstr = EncodeByLen(instr, len);
    	}
    	return outstr;
    }
    
    public String DecodeBy ( String instr, int len ) {
    	String outstr = "";
    	
    	if(instr != null && instr.length() > 0)
    	{
    		outstr = DecodeByLen(instr, len);
    	}
    	
    	return outstr;
    }
    
    public static void main(String[] args){
    	BrtsBarCodeLib brtsBarCodeLib = new BrtsBarCodeLib();
    	System.out.println(brtsBarCodeLib.DecodeBy("1019192392102510",9));
    }
}
