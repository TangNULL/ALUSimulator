/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @author [161250125_汤云凡]
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public static String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.	
		String str="";
		if(number.substring(0,1).equals("-")){// 负数
			number=number.substring(1);
			int myNum=Integer.parseInt(number);
			char[] myChar=new char[length];
			for(int i=length-2;i>=0;i--){
				if((myNum&(1<<i))!=0){
					myChar[length-1-i]='0';
				}
				if((myNum&(1<<i))==0){
					myChar[length-1-i]='1';
				}
			}//取反的过程
			myChar[0]='1';
			if(myChar[length-1]=='0'){
				myChar[length-1]=1;
			}
			
			if(myChar[length-1]=='1'){
				for(int i=length-1;i>=1;i--){
				    if(myChar[i]=='1'){
				    	myChar[i]='0';
				    }
				    else if(myChar[i]=='0'){
				    	myChar[i]='1';
				    	break;
				    }
			    }
			
			}	//加1的过程
			for(int i=0;i<myChar.length;i++){
				str=str+myChar[i];
			}
		}
		
		else{
			int myNum=Integer.parseInt(number);
			char[] myChar=new char[length];
		
			for(int i=length-2;i>=0;i--){
				if((myNum&(1<<i))!=0){
					myChar[length-1-i]='1';
				}
				if((myNum&(1<<i))==0){
					myChar[length-1-i]='0';
				}
			}
			myChar[0]='0';
			for(int i=0;i<myChar.length;i++){
				str=str+myChar[i];
			}
			
		}
		return str;
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public static String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
	
		String result="0";
		if(number.split("\\.").length>=2){
			if(number.substring(0,1).equals("-")){
				number=number.substring(1);
				result="1";
			}
			String[] TwoString=number.split("\\.");
			int Fore=Integer.parseInt(TwoString[0]);
			String tempOne=Integer.toBinaryString(Fore);
			char[] Onearray=tempOne.toCharArray();//得到小数点前的整数的二进制表示Onearray
			////
			String tempTwo="0"+"."+TwoString[1];
			Double temptwo=Double.valueOf(tempTwo);//temptwo是小数
			char[] Twoarray=new char[23];
			int flag=0;
			while(temptwo*2!=1&&flag<=22){
				temptwo=temptwo*2;
				if(temptwo==1){
					Twoarray[flag]='1';
					flag++;
				}
				if(temptwo>1){
					Twoarray[flag]='1';
					temptwo=temptwo-1;
					flag++;
				}
				else if(temptwo<1){
					Twoarray[flag]='0';
					flag++;
				}
			}
			if(flag<22){
				Twoarray[flag]='1';
				flag++;
			}
			
			for(int i=flag;i<=22;i++){
				Twoarray[i]='0';
			}//得到小数的二进制表示Twoarray
			//////
			char[] FinalChar=new char[Onearray.length+23+1];
			for(int i=0;i<Onearray.length;i++){
				FinalChar[i]=Onearray[i];
			}
			FinalChar[Onearray.length]='.'; //移码前小数点的位置下标是  Onearray.length
			for(int i=Onearray.length+1;i<Onearray.length+23+1;i++){
				FinalChar[i]=Twoarray[i-(Onearray.length+1)];
			}//已将number转成二进制表示并放在数组 FinalChar中
			int count0=0;
			for(int i=0;i<FinalChar.length;i++){
				if(FinalChar[i]=='0'){
					count0++;
				}
				if(FinalChar[i]=='1'){
					break;
				}
			}
			int isOne=-1;
			boolean Exist1=false;
			for(int i=0;i<FinalChar.length;i++){
				if(FinalChar[i]=='1'){
					isOne=i;//找到1的位置了！
					Exist1=true;
					break;
				}
			}
			int e=Onearray.length-isOne-1;
			if(e>0&&Exist1==true){
				e=Onearray.length-isOne-1+127;// 移码e+过127
			}
			
			else if(e<0){
				e=Onearray.length-isOne+127;
			}
			else if(count0==FinalChar.length-1){
				e=0;
			}
			else if(e==0&&count0!=FinalChar.length-1){
				e=127;
			}
			
			String E=Integer.toBinaryString(e);
			E="000000000000000000000000000"+E;
			E=E.substring(E.length()-eLength,E.length());  //阶码的String为E
			String s="";
			for(int i=isOne+1;i<FinalChar.length;i++){
				if(i== Onearray.length){
					continue;
				}
				else{
					s=s+FinalChar[i];
				}
			}
			while(s.length()<sLength){
				s=s+"0";
			}
			s=s.substring(0, sLength);
					// 尾数的String为s
			
			result=result+E+s;
			return result;
		}  //end
		
		
		else if(number.equals("0")){
			for(int i=1;i<1+eLength+sLength;i++){
				result=result+"0";
			}
			return result;
		}
		
		else if(number.equals("+Inf")||number.equals("-Inf")){
			String e="",s="";
			
			if(number.startsWith("+")){
				for(int i=0;i<eLength;i++){
					e=e+"1";
				}
				for(int i=0;i<sLength;i++){
					s=s+"0";
				}
				result=result+e+s;
			}
			if(number.startsWith("-")){
				result="1";
				for(int i=0;i<eLength;i++){
					e=e+"1";
				}
				for(int i=0;i<sLength;i++){
					s=s+"0";
				}
				result=result+e+s;
			}
			return result;
		}
		
		if(number.equals("NaN")){
			String e="",s="";
			for(int i=0;i<eLength;i++){
				e=e+"1";
			}
			for(int i=0;i<sLength-1;i++){
				s=s+"0";
			}
			s=s+"1";
			result=result+e+s;
			return result;
		}
		
		else{
			return null;
		}
		
	}

	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		if(length==32){
			return floatRepresentation(number,8,23);
		}
		else{
			return floatRepresentation(number,11,52);
		}
		// TODO YOUR CODE HERE.
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public static String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		String result="";
		if(operand.startsWith("1")){//负数
			result="-";
			int num=Integer.valueOf(operand, 2);
			num=(int)Math.pow(2, operand.length())-num;
			result+=num;
		}
		else{
			result+=Integer.valueOf(operand, 2);
		}
		
		return result;
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public static String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String result="";
		if(operand.startsWith("1")){
			result="-";
		}
		
		int SisZero=Integer.valueOf(operand.substring(1+eLength),2);
		int EisZero=Integer.valueOf(operand.substring(1, 1+eLength),2);
		if(EisZero==0){//阶码全为0        非规格化数
			String S=operand.substring(1+eLength);//尾数
			char[] myS=S.toCharArray();
			double valueS=0;
			for(int i=0;i<myS.length;i++){
				if(myS[i]=='1'){
					valueS+=Math.pow(2, -(i+1));
				}
			}
			valueS=valueS*Math.pow(2, -126);
			result+=valueS;
		}//非规格化数结束
		
		if(EisZero==(Math.pow(2, eLength)-1)){//阶码全为1     
			if(SisZero==0){// 尾数全为0   Inf
				result=result+"Inf";
			}
			else{// NaN
				result="NaN";
			}
		}
		
		else{
			int valueE=EisZero-127;
			String S=operand.substring(1+eLength);//尾数
			char[] myS=S.toCharArray();
			double valueS=1;
			for(int i=0;i<myS.length;i++){
				if(myS[i]=='1'){
					valueS+=Math.pow(2, -(i+1));
				}
			}
			valueS*=Math.pow(2, valueE);
			result+=valueS;
		}
		
		
		return result;
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public static String negation (String operand) {
		String str="";
		char[] myChar=operand.toCharArray();
		for(int i=0;i<myChar.length;i++){
			if(myChar[i]=='1'){
				myChar[i]='0';
			}
			else{
				myChar[i]='1';
			}
		}//取反
		
		
		str=String.valueOf(myChar);		
		/*
		for(int i=0;i<myChar.length;i++){
			str+=myChar[i];
		}
		
		
		
		if(myChar[0]=='1'){
			result="-";
			int num=(int)Integer.valueOf(str,2);
			num=(int)Math.pow(2, str.length())-num;
			result+=num;
		}
		
		else{
			int num=(int)Integer.valueOf(str,2);
			result+=num;
		}
		*/
		
		return str;
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public static String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		
		int length=operand.length();
		String result="";
		for(int i=0;i<n;i++){
			operand+="0";
		}
		result=operand.substring(operand.length()-length,operand.length());
		return result;
		/*
		int value=Integer.valueOf(integerTrueValue(operand));
		int result=value<<n;
		String Result=""+result;
		return Result;
		*/
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public static String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int length=operand.length();
		for(int i=0;i<n;i++){
			operand="0"+operand;
		}
		String result=operand.substring(0, length);
		return result;
		
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public static String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int length=operand.length();
		if(operand.startsWith("1")){//负数
			for(int i=0;i<n;i++){
				operand="1"+operand;
			}
		}
		if(operand.startsWith("0")){
			for(int i=0;i<n;i++){
				operand="0"+operand;
			}
		}
		String result=operand.substring(0,length);
		return result;
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public static String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		char m,n;    //m表示进位，n表示和
		if(x=='1'&&y=='1'){
			if(c=='1'){
				m='1';
				n='1';
			}
			else{
				m='1';
				n='0';
			}
		}
		else if(x=='0'&&y=='0'){
			m='0';
			n=c;
		}
		else{
			if(c=='0'){
				m='0';
				n='1';
			}
			else{
				m='1';
				n='0';
			}
		}
		String str=""+m+n;
		return str;
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public static String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		char[] Operand1=operand1.toCharArray();
		char[] Operand2=operand2.toCharArray();
		char[] result=new char[4];
		char myChar=c;
		for(int i=3;i>=0;i--){
			char char1=Operand1[i];
			char char2=Operand2[i];
			result[i]=fullAdder(char1,char2,myChar).charAt(1);
			myChar=fullAdder(char1,char2,myChar).charAt(0);//进位
		}
		String str=myChar+String.valueOf(result);
		return str;
	}

	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public static String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		char[] myChar=operand.toCharArray();
		int Cin=0,Cout=0,k=1;
		int[] sum=new int[myChar.length];
		for(int i=myChar.length-1;i>=0;i--){
			Cin=Cout;
			sum[i]=(myChar[i]-48)^k^Cin;
			Cout=((myChar[i]-48)&k)|(Cin&((myChar[i]-48)^k));
			k=0;
		}
		StringBuffer str=new StringBuffer();
		str.append(""+Cout);
		for(int i=0;i<sum.length;i++){
			str.append(""+sum[i]);
		}
		String result=str.toString();
		return result;
	}

	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public static String adder (String operand1, String operand2, char c, int length) {
		// TODO YOUR CODE HERE.
		/*
		if(operand1.length()<length&&operand2.length()==length){
			int len=operand1.length();
			for(int i=0;i<length-len;i++){
				operand1=operand1.substring(0, 1)+operand1;
			}
		}
		else if(operand1.length()==length&&operand2.length()<length){
			int len=operand2.length();
			for(int i=0;i<length-len;i++){
				operand2=operand2.substring(0, 1)+operand2;
			}
		}
		else if(operand1.length()<length&&operand2.length()<length){
			int len1=operand1.length();
			int len2=operand2.length();
			for(int i=0;i<length-len1;i++){
				operand1=operand1.substring(0, 1)+operand1;	
			}
			for(int i=0;i<length-len2;i++){
				operand2=operand2.substring(0, 1)+operand2;	
			}
		}
		*/
		while(operand1.length()<length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		
		//补全
		String result="";
		for(int i=0;i<length/4;i++){
			if(i==0){
				result=claAdder(operand1.substring(length-4,length),operand2.substring(length-4, length),c);
			}
			//末四位相加得到一个进位  四位结果
			else{
				result=claAdder(operand1.substring(length-((i+1)*4),length-(i*4)),operand2.substring(length-((i+1)*4),length-(i*4)),result.charAt(0))+result.substring(1);
			}
		}
		return result;
	}

	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>

	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public static String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result=adder(operand1, operand2,'0',length);
		return result;
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public static String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result="";
		int op2=Integer.valueOf(integerTrueValue(operand2));
		op2*=(-1);
		String OP2=Integer.toBinaryString(op2);//OP2为减数的负数的补码
		if(op2<0&&OP2.length()<length){
			OP2="1"+OP2;
		}
		else if(op2>0&&OP2.length()<length){
			OP2="0"+OP2;
		}
		result=adder(operand1,OP2,'0',length);
		return result;
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public static String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		while(operand1.length()<length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//补全
		String result="0000";
		char[] OP=operand1.toCharArray();
		for(int i=OP.length-1;i>=0;i--){
			if(OP[i]=='1'){
				if(i==OP.length-1){
					result="0"+operand2;
				}
				else{
					result=adder(leftShift(operand2,OP.length-1-i),result.substring(1),result.charAt(0),length);
				}
			}
			
		}
		return result;
	}

	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public static String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		while(operand1.length()<2*length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//补全   X.length=2*length
		String X=operand1;
		String Y=operand2;
		String R1="0";
		String[] R=new String[length];
		int mid=Integer.valueOf(integerTrueValue(operand2));
		mid*=(-1);
		String FY=integerRepresentation(String.valueOf(mid),length);//FY是 -Y的补码
		
		if(operand1.charAt(0)==Y.charAt(0)){//同号
			R1=adder(X.substring(0, length),FY,'0',length).substring(1)+X.substring(length,2*length);
		}
		else{//不同号
			R1=adder(X.substring(0, length),Y,'0',length).substring(1)+X.substring(length,2*length);
		}
		if(R1.charAt(0)==Y.charAt(0)){//R1与Y同号  ，Qn置1
			R1+="1";
		}
		else{
			R1+="0";
		}//Qn
		R[0]=R1.substring(1);
		int flag=0;
		boolean w=false;
		for(int i=1;i<=length-1;i++){
			if(R[i-1].endsWith("1")){//上一个是同号   接下来做减法
				String temporary=adder(R[i-1].substring(0, length),FY,'0',length).substring(1)+R[i-1].substring(length,2*length);
				if(temporary.charAt(0)==Y.charAt(0)){
					temporary+="1";
					temporary=temporary.substring(1);
					R[i]=temporary;
				}
				else{
					temporary+="0";
					temporary=temporary.substring(1);
					R[i]=temporary;
				}
			}
			else{
				String temporary=adder(R[i-1].substring(0, length),Y,'0',length).substring(1)+R[i-1].substring(length,2*length);
				if(temporary.charAt(0)==Y.charAt(0)){
					temporary+="1";
					temporary=temporary.substring(1);
					R[i]=temporary;
				}
				else{
					temporary+="0";
					temporary=temporary.substring(1);
					R[i]=temporary;
				}
			}
			if(Integer.valueOf(R[i].substring(0, 2*length-7))==0){//yu shu已经为0;
				flag=i;
				w=true;
				break;
			}
		}//还差最后一次
		String temporary="",Shang=R[flag].substring(length,2*length);
		temporary=R[flag].substring(0, length);
		for(int i=0;i<length-flag;i++){
			Shang=Shang+"0";
		}
		Shang="0"+Shang.substring(Shang.length()-length,Shang.length());
		
		if(w==false){
			Shang="";
			if(R[length-1].endsWith("1")){
				temporary=adder(R[length-1].substring(0, length),FY,'0',length).substring(1);
			}
			else{
				temporary=adder(R[length-1].substring(0, length),Y,'0',length).substring(1);
			}
			
			if(temporary.startsWith("0")){
				Shang=(R[length-1].substring(length,2*length)+"1").substring(1);
			}
			else{
				Shang=(R[length-1].substring(length,2*length)+"0").substring(1);
			}
			//shang
			if(Integer.valueOf(temporary)!=0){
				if(X.charAt(0)!=Y.charAt(0)){
					Shang=adder(Shang,"001",'0',length);
				}
				else{
					Shang="0"+Shang;
				}
				//yu shu
				if(temporary.charAt(0)!=X.charAt(0)){
					if(X.charAt(0)==Y.charAt(0)){
						temporary=adder(temporary,Y,'0',length).substring(1);
					}
					else{
						temporary=adder(temporary,FY,'0',length).substring(1);
					}
				}
			}
			else{
				Shang="0"+Shang;
			}
		}
		return Shang+temporary;
	}

	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public static String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String Result="";
		if(operand1.charAt(0)==operand2.charAt(0)){//同号
			String result=adder("0"+operand1.substring(1),"0"+operand2.substring(1),'0',length);
			//if()
			Result=result.substring(0,1)+operand1.charAt(0)+result.substring(1);
		}
		else{//异号
			if(operand1.startsWith("1")){//operand1 为负数
				operand1=negation(operand1.substring(1));
				operand1="1"+oneAdder(operand1).substring(1);
				String result=adder(operand1,operand2.substring(1),'0',length);
				Result=result.substring(0,1)+result.substring(1, 2)+result.substring(1);
			}
			else if(operand2.startsWith("1")){
				operand2=negation(operand2.substring(1));
				operand2="1"+oneAdder(operand2).substring(1);
				String result=adder(operand2,operand1.substring(1),'0',length);
				Result=result.substring(0,1)+result.substring(1, 2)+result.substring(1);
			}
		}
		return Result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
}
