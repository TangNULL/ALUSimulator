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
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.	
		boolean isNegative=false;
		StringBuffer str=new StringBuffer();
		int myNum = 0;
		if(!number.startsWith("-")){
			myNum=Integer.parseInt(number);
		}
		else if(number.startsWith("-")){
			isNegative=true;
			number=number.substring(1);
			myNum=(int) Math.pow(2, length)-Integer.parseInt(number);
		}
		
		while(myNum!=0){
			int YU=myNum%2;
			char Yu='/';
			if(YU==0){
				Yu='0';
			}
			else{
				Yu='1';
			}
			str.insert(0,Yu);
			myNum=myNum/2;
		}
		while(str.length()<length){
			if(isNegative==true){
				str.insert(0, "1");
		    }
			else{
				str.insert(0, "0");
			}
		}
		String result=str.toString();
		return result;
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
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
	
		String result="0";
		if(number.substring(1).equals("Inf")){
			if(number.startsWith("-")){
				result="1";
			}
			String E="1";
			while(E.length()<eLength){
				E+="1";
			}
			String S="0";
			while(S.length()<sLength){
				S+="0";
			}
			result=result+E+S;
		}
		else if(number.split("\\.").length>=2){
			if(number.substring(0,1).equals("-")){
				number=number.substring(1);
				result="1";
			}
			String[] TwoString=number.split("\\.");
			
			if(TwoString[0].equals("0")&&TwoString[1].equals("0")){
				String E="0";
				while(E.length()<eLength){
					E+="0";
				}
				String S="0";
				while(S.length()<sLength){
					S+="0";
				}
				result=result+E+S;
			}
			
			else{
				String Fore=Integer.toBinaryString(Integer.valueOf(TwoString[0]));//小数点前整数的二进制。
				int OneInFore=0;//整数中最靠前的1的位置  0123456
				boolean OneInForeExist=false;
				if(!(TwoString[0].equals("0"))){
					while(OneInFore<=Fore.length()-1){
						if(Fore.charAt(OneInFore)=='1'){
							OneInForeExist=true;
							break;
						}
						else{
							OneInFore++;
						}
					}
					
				}
				
				double Back=Double.valueOf("0"+"."+TwoString[1]);//  小数部分 0.*******
				StringBuffer Buffer=new StringBuffer();
				if(OneInForeExist==true){//意味着要右规   尾数只需sLength位
					int MOVE=Fore.length()-1-OneInFore;   //要右移的位数
					if(MOVE>=(int)Math.pow(2, eLength-1)){// 你这个数太大啦 要进入Inf范围了
						String E="1";
						while(E.length()<eLength){
							E+="1";
						}
						String S="0";
						while(S.length()<sLength){
							S+="0";
						}
						result=result+E+S;
					}
					else{//正常的右规情形
						for(int i=0;i<sLength;i++){
							if(Back*2>=1){
								Buffer.append("1");
								Back=Back*2-1;
							}
							else if(Back*2<1){
								Back=Back*2;
								Buffer.append("0");
							}
						}
						String BackOfS=Buffer.toString();
						String S=Fore.substring(OneInFore+1, Fore.length())+BackOfS;
						S=S.substring(0, sLength);
						int e=Fore.length()-(OneInFore+1);
						e=e+(int)Math.pow(2, eLength-1)-1;//阶码
						String E=Integer.toBinaryString(e);
						while(E.length()<eLength){
							E="0"+E;
						}
						E=E.substring(E.length()-eLength,E.length());
						result=result+E+S;
					}
					
				}
				else{//说不定要左规 那岂不是很尴尬
					int FirstOneInBack=0;// 小数中第一个出现的1的位置  0123456
					Double temp=Back;
					while(temp*2<1){
						temp=temp*2;
						FirstOneInBack++;
					}// 所以需左移FirstOneInBack+1位   即需要 sLength+(FirstOneInBack+1)位的尾数
					if(FirstOneInBack+1>=(int)Math.pow(2, eLength-1)){//  太小啦    进入非规格化数的范围了 
						String E="0";
						while(E.length()<eLength){
							E+="0";
						}
						for(int i=0;i<sLength+(int)Math.pow(2, eLength-1)-2;i++){
							if(Back*2>=1){
								Buffer.append("1");
								Back=Back*2-1;
							}
							else if(Back*2<1){
								Back=Back*2;
								Buffer.append("0");
							}
						}
						String BackOfS=Buffer.toString();
						String S=BackOfS.substring(BackOfS.length()-sLength,BackOfS.length());
						result=result+E+S;
					}
					else{//  正常的左规情形。
						for(int i=0;i<sLength+FirstOneInBack+1;i++){
							if(Back*2>=1){
								Buffer.append("1");
								Back=Back*2-1;
							}
							else if(Back*2<1){
								Back=Back*2;
								Buffer.append("0");
							}
						}
						String BackOfS=Buffer.toString();
						String S=BackOfS.substring(BackOfS.length()-sLength,BackOfS.length());
						int e=-(FirstOneInBack+1);
						e=e+(int)Math.pow(2, eLength-1)-1;
						String E=Integer.toBinaryString(e);
						while(E.length()<eLength){
							E="0"+E;
						}
						E=E.substring(E.length()-eLength,E.length());
						result=result+E+S;
					}
					
				}
			}
		
			
			
		}
		return result;	
		
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
	public String integerTrueValue (String operand) {
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
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String result="";
		if(operand.startsWith("1")){
			result="-";
		}
		
		int SisZero=Integer.valueOf(operand.substring(1+eLength),2);
		int EisZero=Integer.valueOf(operand.substring(1, 1+eLength),2);
		if(EisZero==0){//阶码全为0        非规格化数
			if(SisZero==0){// 阶码尾数都为0
				result="0.0";
			}
			else{
				String S=operand.substring(1+eLength);//尾数
				char[] myS=S.toCharArray();
				double valueS=0;
				for(int i=0;i<myS.length;i++){
					if(myS[i]=='1'){
						valueS+=Math.pow(2, -(i+1));
					}
				}
				valueS=valueS*Math.pow(2, -(Math.pow(2, eLength-1)-2));
				result+=valueS;
			}
		}//非规格化数结束
		
		else if(EisZero==(Math.pow(2, eLength)-1)){//阶码全为1     
			if(SisZero==0){// 尾数全为0   Inf
				if(operand.startsWith("0")){
					result="+";
				}
				result=result+"Inf";
			}
			else{// NaN
				result="NaN";
			}
		}
		
		else{
			int valueE=EisZero-(int)Math.pow(2, eLength-1)+1;
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
	public String negation (String operand) {
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
	public String leftShift (String operand, int n) {
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
	public String logRightShift (String operand, int n) {
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
	public String ariRightShift (String operand, int n) {
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
	public String fullAdder (char x, char y, char c) {
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
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		char[] Operand1=operand1.toCharArray();
		char[] Operand2=operand2.toCharArray();
		char[] result=new char[4];
		char myChar=c;
		int one=-1,two=-1;
		for(int i=3;i>=0;i--){
			char char1=Operand1[i];
			char char2=Operand2[i];
			result[i]=fullAdder(char1,char2,myChar).charAt(1);
			myChar=fullAdder(char1,char2,myChar).charAt(0);//进位
		}
		String str=""+myChar+String.valueOf(result);
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
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		char[] myChar=operand.toCharArray();
		int Cin=0,Cout=0,k=1;
		int one=-1,two=-1;
		int[] sum=new int[myChar.length];
		for(int i=myChar.length-1;i>=0;i--){
			Cin=Cout;
			sum[i]=(myChar[i]-48)^k^Cin;
			Cout=((myChar[i]-48)&k)|(Cin&((myChar[i]-48)^k));
			k=0;
			if(i==1){
				two=Cout;
			}
			if(i==0){
				one=Cout;
			}
		}
		int isOverFlow=one^two;
		StringBuffer str=new StringBuffer();
		str.append(""+isOverFlow);
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
	public String adder (String operand1, String operand2, char c, int length) {
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
		char a=operand1.charAt(0);
		char b=operand2.charAt(0);
		
		
		
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
		char re=result.charAt(1);
		if(a==b&&a!=re){
			result="1"+result.substring(1);
		}
		else{
			result="0"+result.substring(1);
		}
		return result;
	}

	public String myAdderJinWei(String operand1, String operand2, int length){
		
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
				result=claAdder(operand1.substring(length-4,length),operand2.substring(length-4, length),'0');
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
	public String integerAddition (String operand1, String operand2, int length) {
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
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result="";
		int op2=Integer.valueOf(integerTrueValue(operand2));
		op2*=(-1);
		String OP2=integerRepresentation(""+op2,length);//OP2为减数的负数的补码
		char prefix=operand1.charAt(0);
		while(operand1.length()<length){
			operand1=prefix+operand1;
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
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		char a=operand1.charAt(0);
		char b=operand2.charAt(0);
		while(operand1.length()<length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//补全
		String result="00";
		while(result.length()<=length){
			result=result+"0";
		}
		char[] OP=operand1.toCharArray();
		for(int i=OP.length-1;i>=0;i--){
			if(OP[i]=='1'){
				if(i==OP.length-1){
					result="0"+operand2;
				}
				else{
					result=adder(leftShift(operand2,OP.length-1-i),result.substring(1),'0',length);
				}
			}
			else{
				result=adder(result.substring(1),"0",'0',length);
			}
			
		}
		char re=result.charAt(1);
		if((a!=b&&re=='0')||(a==b&&re=='1')){
			result="1"+result.substring(1);
		}
		else{
			result="0"+result.substring(1);
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
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		char a=operand1.charAt(0);
		char b=operand2.charAt(0);
		//符号拓展
		while(operand1.length()<2*length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//补全   X.length=2*length
		
		if(Integer.valueOf(operand2)==0){
			return "NaN";
		}
		else{
			String X=operand1;
			String Y=operand2;
			String R1="0";
			String[] R=new String[length];
			int mid=Integer.valueOf(integerTrueValue(operand2));
			mid=-mid;
			String FY=integerRepresentation(""+mid,length);//FY是 -Y的补码
			while(FY.length()<length){
				FY=FY.substring(0, 1)+FY;
			}
			
			if(operand1.charAt(0)==Y.charAt(0)){//XY同号做减法  求第一位Shang
				R1=myAdderJinWei(X.substring(0, length),FY,length).substring(1)+X.substring(length,2*length);
			}
			else{//不同号
				R1=myAdderJinWei(X.substring(0, length),Y,length).substring(1)+X.substring(length,2*length);
			}
			if(R1.charAt(0)==Y.charAt(0)){//R1与Y同号  ，Q(length-1)置1   Q(length-1)~Q0
				R1+="1";
			}
			else{
				R1+="0";
			}//Qn
			R[0]=R1.substring(1);
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
			}//还差最后一次
			
			if(a=='1'&&b=='0'){
				String myShang="",myYu="0";
				if(Integer.valueOf(R[length-1].substring(0,length))==0){//余数寄存器为0
					myShang=R[length-1].substring(length+1,2*length)+"0";///////有问题
				
					myYu="0";
					while(myYu.length()<length){
						myYu="0"+myYu;
					}
				
				}
				return "0"+myShang+myYu;
			}
			
			
			//商的修正
			else{
				String myYu=R[length-1].substring(0, length);
				if(R[length-1].endsWith("1")){
					myYu=myAdderJinWei(myYu,FY,length).substring(1);
				}
				else{
					myYu=myAdderJinWei(myYu,Y,length).substring(1);
				}
				char Last=myYu.charAt(0);
				String rrrr="";
				if(Last==b){
					rrrr="1";
				}
				else{
					rrrr="0";
				}
				
				String myShang=R[length-1].substring(length, 2*length).substring(1)+rrrr;
				if(a!=b){//被除数与除数同号
					myShang=oneAdder(myShang).substring(1);
				}
				//余数的修正
				
				if(myYu.charAt(0)!=a){//余数符号bu同于被除数符号
					if(a==b){
						myYu=myAdderJinWei(myYu,Y,length).substring(1);
					}
					else{
						myYu=myAdderJinWei(myYu,FY,length).substring(1);
					}
				}
				return "0"+myShang+myYu;
			}
		}
		
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
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String Result="";
		if(operand1.charAt(0)==operand2.charAt(0)){//同号求和
			String result="";
			if(operand1.substring(1).length()<length){
				result=myAdderJinWei("0"+operand1.substring(1),"0"+operand2.substring(1),length);
			}
			else{
				result=myAdderJinWei(operand1.substring(1),operand2.substring(1),length);
			}
			//if()
			Result=result.substring(0,1)+operand1.charAt(0)+result.substring(1);
		}
		else{//异号求差
			int prefix=operand1.charAt(0)-48;
			String x=operand2;
			x=oneAdder(negation(operand2.substring(1))).substring(1);
			String result=myAdderJinWei(operand1.substring(1),x,length);
			if(result.charAt(0)=='1'){
				Result="0"+prefix+result.substring(1);
			}
			else{
				if(prefix==0){
					prefix=1;
				}
				else{
					prefix=0;
				}
				Result="0"+prefix+oneAdder(negation(result.substring(1))).substring(1);
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
		//正常的规格化数
		int max=(int) Math.pow(2, eLength)-1;  //相当于255
		String Result="0";
		int Ex=Integer.valueOf(operand1.substring(1,1+eLength),2);
		int Ey=Integer.valueOf(operand2.substring(1,1+eLength),2);
		String Mx=operand1.substring(1+eLength,1+eLength+sLength);
		String My=operand2.substring(1+eLength,1+eLength+sLength);
		int midE=Ex-Ey;
		String prefix=operand1.substring(0,1);
		String Deprefix="";
		if(prefix.equals("0")){
			Deprefix="1";
		}
		else{
			Deprefix="0";
		}
		if(midE>0){  //右移Y
			String ReserveY=operand2.substring(operand2.length()-midE,operand2.length());//保留右移而丢弃的operand2的倒数midE位
			ReserveY=ReserveY.substring(0,gLength);  //保护位
			My=logRightShift("1"+My.substring(0,My.length()-1),midE-1);//My的隐藏位为0
			String Msum="";
			if(operand1.charAt(0)==operand2.charAt(0)){//同加  
				Msum=myAdderJinWei(Mx,My,sLength);
				if(Msum.startsWith("1")){//需要右移
					Msum="0"+Msum.substring(1, Msum.length()-1);//省略隐藏位1
					Ex++;
				}
				else if(Msum.startsWith("0")){//没有溢出 可能需要左移???
					Msum=Msum.substring(1);//隐藏位1
				}
				String ExtoBi=integerRepresentation(""+Ex,eLength);
			//	ExtoBi=ExtoBi.substring(ExtoBi.length()-eLength,ExtoBi.length());
				//判断阶码是否溢出
				if(Ex>=max||Ex<=0){
					Result="1";
				}
				Result=Result+prefix+ExtoBi+Msum;
			}

			else if(operand1.charAt(0)!=operand2.charAt(0)){//取My的补码   不考虑隐藏位的话，最后别忘了两个数的小数点前都是1??????????
				My=negation("0"+My);
				My=oneAdder(My);
				String JinWei="";
				if(Integer.valueOf(My)==0){
					JinWei="1";
				}
				///////////  0.0000 > 1.1111 > (1)0.0000   然后 1.0000+0.0000=0.0000表示没有进位，但是那个取反加一时的进位被忽略了，导致后来的结果的符号不对
				
			    My=My.substring(1);
				char Yprefix=My.charAt(0);
				My=My.substring(1);
				Msum=myAdderJinWei(Mx,My,sLength);
				if(Msum.startsWith("0")){   //   X=1.****  Y=Yprefix.****   Msum=0.****
					if(Yprefix=='1'){  //10.*****  需右移  产生进位 
						int i=1;
						while(Msum.charAt(i)!='1'){
							i++;
						}// i表示1的位置   0123456   左移i位
						Ex=Ex-i;
						if(ReserveY.length()<i){
							Msum=Msum.substring(i+1)+ReserveY;
							while(Msum.length()<sLength){
								Msum+="0";
							}
						}
						else{
							Msum=Msum.substring(i+1)+ReserveY.substring(0,i);	
						}
						//Msum省略位为1
						String ExtoBi=integerRepresentation(""+Ex,eLength);
						Result+=Deprefix+ExtoBi+Msum;
						
						/*Msum="0"+Msum.substring(1, Msum.length()-1);//省略隐藏位1   结果符号位取被加数符号   prefix
						Ex++;
						String ExtoBi=integerRepresentation(""+Ex,eLength);
						Result+=prefix+ExtoBi+Msum;*/
					}
					else{ //无进位  需对结果求补   1.**** +0.****=1.****
						Msum=oneAdder(negation("1"+Msum.substring(1))).substring(1);
						if(Msum.startsWith("0")){//需左移  ReserveY 要上场啦      现在的Msum长的是这样的 0.*******    要找到小数点后面的第一个1
							int i=1;
							while(Msum.charAt(i)!='1'){
								i++;
							}// i表示1的位置   0123456   左移i位
							Ex=Ex-i;
							if(ReserveY.length()<i){
								Msum=Msum.substring(i+1)+ReserveY;
								while(Msum.length()<sLength){
									Msum+="0";
								}
							}
							else{
								Msum=Msum.substring(i+1)+ReserveY.substring(0,i);	
							}
							//Msum省略位为1
							String ExtoBi=integerRepresentation(""+Ex,eLength);
							Result+=Deprefix+ExtoBi+Msum;
						}
						else{//Msum长这样 1.******   不需要左移右移
							Msum=Msum.substring(1);
							String ExtoBi=integerRepresentation(""+Ex,eLength);
							if(JinWei=="1"){
								Result+=prefix+ExtoBi+Msum;
							}
							else{
								Result+=Deprefix+ExtoBi+Msum;
							}
							
						}
					}
				}
				
				else{//必然有进位  所以无需取反操作啦
					if(Yprefix=='0'){// 10.******   右移   Msum现在有  sLength+1 位
						int i=1;
						while(Msum.charAt(i)!='1'){
							i++;
						}// i表示1的位置   0123456   左移i位
						Ex=Ex-i;
						if(ReserveY.length()<i){
							Msum=Msum.substring(i+1)+ReserveY;
							while(Msum.length()<sLength){
								Msum+="0";
							}
						}
						else{
							Msum=Msum.substring(i+1)+ReserveY.substring(0,i);	
						}
						//Msum省略位为1
						String ExtoBi=integerRepresentation(""+Ex,eLength);
						Result+=Deprefix+ExtoBi+Msum;
						
						/*Msum="0"+Msum.substring(1, Msum.length()-1);//省略隐藏位1   结果符号位取被加数符号   prefix
						Ex++;
						String ExtoBi=integerRepresentation(""+Ex,eLength);
						Result+=prefix+ExtoBi+Msum;*/
					}
					else{  //  11.****
						Msum=Msum.substring(1);
						/*Msum="1"+Msum.substring(1, Msum.length()-1);//省略隐藏位1   结果符号位取被加数符号   prefix
						Ex++;*/
						String ExtoBi=integerRepresentation(""+Ex,eLength);
						Result+=prefix+ExtoBi+Msum;
					}
				}
			}
		}
		
		
		
		else if(midE<0){//右移X    以Ey位基准
			midE=-midE;
			String ReserveX=operand1.substring(operand1.length()-midE,operand1.length());//保留右移而丢弃的operand1的倒数midE位
			if(ReserveX.length()>gLength){
				ReserveX=ReserveX.substring(0,gLength);  //保护位
			}
			Mx=logRightShift("1"+Mx.substring(0,Mx.length()-1),midE-1);//Mx的隐藏位为0    0.****   My=1.****
			String Msum="";
			if(operand1.charAt(0)==operand2.charAt(0)){//同加  
				Msum=myAdderJinWei(Mx,My,sLength);
				if(Msum.startsWith("1")){//需要右移
					Msum="0"+Msum.substring(1, Msum.length()-1);//省略隐藏位1
					Ey++;
				}
				else if(Msum.startsWith("0")){//没有溢出 可能需要左移???
					Msum=Msum.substring(1);//隐藏位1
				}
				String EytoBi=integerRepresentation(""+Ey,eLength);
				//判断阶码是否溢出
				if(Ey>=max||Ey<=0){
					Result="1";
				}
				Result+=prefix+EytoBi+Msum;
			}
			else if(operand1.charAt(0)!=operand2.charAt(0)){//取My的补码   不考虑隐藏位的话，最后别忘了两个数的小数点前都是1?? 0.****+ 本来My=1.****
				My=negation("1"+My);
				My=oneAdder(My);
				String JinWei="";
				if(Integer.valueOf(My)==0){
					JinWei="1";
				}
				My=My.substring(1);  // *.****
				char Yprefix=My.charAt(0);
				My=My.substring(1);
				Msum=myAdderJinWei(Mx,My,sLength);  //  ****+****=*.****     0.****+ Yprefix.****
				if(Msum.startsWith("1")&&Yprefix=='1'){   //10.****   需右移  产生进位
					int i=1;
					while(Msum.charAt(i)!='1'){
						i++;
					}// i表示1的位置   0123456   左移i位
					Ey=Ey-i;
					if(ReserveX.length()<i){
						Msum=Msum.substring(i+1)+ReserveX;
						while(Msum.length()<sLength){
							Msum+="0";
						}
					}
					else{
						Msum=Msum.substring(i+1)+ReserveX.substring(0,i);	
					}
					//Msum省略位为1
					/*Msum="0"+Msum.substring(1, Msum.length()-1);//省略隐藏位1   结果符号位取被加数符号   prefix
					Ey++;*/
					String EytoBi=integerRepresentation(""+Ey,eLength);
					Result+=prefix+EytoBi+Msum;
				}
				else if((Msum.startsWith("0")&&Yprefix=='1')||(Msum.startsWith("1")&&Yprefix=='0')){  //  1.**** 无进位  需求补
					Msum=oneAdder(negation("1"+Msum.substring(1))).substring(1);
					if(Msum.startsWith("0")){//需左移  ReserveX 要上场啦      现在的Msum长的是这样的 0.****    要找到小数点后面的第一个1
						int i=1;
						while(Msum.charAt(i)!='1'){
							i++;
						}// i表示1的位置   0123456   左移i位
						Ey=Ey-i;
						if(ReserveX.length()<i){
							Msum=Msum.substring(i+1)+ReserveX;
							while(Msum.length()<sLength){
								Msum+="0";
							}
						}
						else{
							Msum=Msum.substring(i+1)+ReserveX.substring(0,i);	
						}
						//Msum省略位为1
						String EytoBi=integerRepresentation(""+Ey,eLength);
						if((Msum.startsWith("0")&&Yprefix=='1')&&JinWei=="1"){
							Result+=prefix+EytoBi+Msum;
						}
						else{
							Result+=Deprefix+EytoBi+Msum;
						}
						
					}
					else{//Msum长这样 1.******   不需要左移右移
						Msum=Msum.substring(1);
						String EytoBi=integerRepresentation(""+Ey,eLength);
						Result+=Deprefix+EytoBi+Msum;
					}
				}
				else if(Msum.startsWith("0")&&Yprefix=='0'){   //0.****   无进位 需求补
					Msum=oneAdder(negation("0"+Msum.substring(1))).substring(1);
					if(Msum.startsWith("0")){//需左移  ReserveX 要上场啦      现在的Msum长的是这样的 0.****    要找到小数点后面的第一个1
						int i=1;
						while(Msum.charAt(i)!='1'){
							i++;
						}// i表示1的位置   0123456   左移i位
						Ey=Ey-i;
						if(ReserveX.length()<i){
							Msum=Msum.substring(i+1)+ReserveX;
							while(Msum.length()<sLength){
								Msum+="0";
							}
						}
						else{
							Msum=Msum.substring(i+1)+ReserveX.substring(0,i);	
						}
						//Msum省略位为1
						String EytoBi=integerRepresentation(""+Ey,eLength);
						Result+=Deprefix+EytoBi+Msum;
					}
					else{  //Msum长这样 1.******   不需要左移右移
						Msum=Msum.substring(1);
						String EytoBi=integerRepresentation(""+Ey,eLength);
						Result+=Deprefix+EytoBi+Msum;
					}
				}
			}
		}
		
		else  if(midE==0){  //无需对阶   1.****   +  1.****
			String Msum="";
			if(operand1.charAt(0)==operand2.charAt(0)){ //同加  
				Msum=myAdderJinWei(Mx,My,sLength);
				if(Msum.startsWith("1")){//需要右移  11.****
					Msum="1"+Msum.substring(1, Msum.length()-1);//省略隐藏位1
					Ey++;
				}
				else if(Msum.startsWith("0")){//  10.****
					Msum="0"+Msum.substring(1, Msum.length()-1);//省略隐藏位1
					Ey++;
				}
				String EytoBi=integerRepresentation(""+Ey,eLength);
				//判断阶码是否溢出
				if(Ey>=max||Ey<=0){
					Result="1";
				}
				Result+=prefix+EytoBi+Msum;
			}
			
			else{
				My=negation("1"+My);
				My=oneAdder(My).substring(1);  // Yprefix.****
				char Yprefix=My.charAt(0);
				My=My.substring(1); //****
				Msum=myAdderJinWei(Mx,My,sLength);  //  ****+****=*.****     1.****+ Yprefix.****
				if(Msum.startsWith("0")&&Yprefix=='0'){   //1.****   无进位 需求补
					Msum=oneAdder(negation("1"+Msum.substring(1))).substring(1);
					if(Msum.startsWith("0")){//需左移   现在的Msum长的是这样的 0.****    要找到小数点后面的第一个1
						int i=1;
						while(Msum.charAt(i)!='1'){
							i++;
						}// i表示1的位置   0123456   左移i位
						Ey=Ey-i;
						Msum=Msum.substring(i+1);
						while(Msum.length()<sLength){
							Msum+="0";
						}
						//Msum省略位为1
						String EytoBi=integerRepresentation(""+Ey,eLength);
						Result+=Deprefix+EytoBi+Msum;
					}
					else{  //Msum长这样 1.******   不需要左移右移
						Msum=Msum.substring(1);
						String EytoBi=integerRepresentation(""+Ey,eLength);
						Result+=Deprefix+EytoBi+Msum;
					}
			    }
				//必然有进位
				else if(Msum.startsWith("1")&&Yprefix=='1'){   //  11.****  右移
					Msum=Msum.substring(1);
					/*Msum="1"+Msum.substring(1, Msum.length()-1);//省略隐藏位1   结果符号位取被加数符号   prefix
					Ey++;*/
					String EytoBi=integerRepresentation(""+Ey,eLength);
					Result+=prefix+EytoBi+Msum;
				}
				else{  //  10.****  右移
					boolean isOne=false;
					int i=1;
					while(Msum.charAt(i)!='1'&&i<=Msum.length()-2){
						i++;
					}
					if(Msum.charAt(i)=='1'){
						isOne=true;
					}// i表示1的位置   0123456   左移i位
					if(isOne==true){
						Ey=Ey-i;
					}
					else{
						Ey=0;
					}
					Msum=Msum.substring(i+1);
					while(Msum.length()<sLength){
						Msum+="0";
					}
					/*Msum="0"+Msum.substring(1, Msum.length()-1);//省略隐藏位1   结果符号位取被加数符号   prefix
					Ey++;*/
					String EytoBi=integerRepresentation(""+Ey,eLength);
					Result+=prefix+EytoBi+Msum;
				}
	    	}
		}
		if(Ex>=max||Ey>=max){
			Result="1"+Result.substring(1);
		}
		return Result;
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
		if(operand2.startsWith("1")){ //   -（-**）
			operand2="0"+operand2.substring(1);
			String Result=floatAddition(operand1,operand2,eLength,sLength,gLength);
			return Result;
		}
		else{ //   -(****)
			operand2="1"+operand2.substring(1);
			String Result=floatAddition(operand1,operand2,eLength,sLength,gLength);
			return Result;
		}
		
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
		int max=(int)Math.pow(2, eLength)-1;
		String Result="0";
		int Ex=Integer.valueOf("0"+operand1.substring(1,1+eLength),2);
		int Ey=Integer.valueOf("0"+operand2.substring(1,1+eLength),2);
		String Mx=operand1.substring(1+eLength,1+eLength+sLength);
		String My=operand2.substring(1+eLength,1+eLength+sLength);
		int mx=Integer.valueOf(Mx);
		int my=Integer.valueOf(My);
		int SumE=-1;
		if((Ex==0&&mx==0)||(Ey==0&&my==0)){
			while(Result.length()<2+eLength+sLength){
				Result+="0";
			}
		}
		else{
			String prefix="";
			String M="";
			if(operand1.charAt(0)==operand2.charAt(0)){
				prefix="0";
			}
			else{
				prefix="1";
			}
			//算阶码
			SumE=Ex+Ey-((int)Math.pow(2, eLength-1)-1);
			String Mul=integerMultiplication("01"+Mx,"01"+My,(sLength+4)*2);  //得到 溢出位+ 3*sLength 
			Mul=Mul.charAt(0)+Mul.charAt(sLength)+Mul.substring(Mul.length()-2*sLength,Mul.length()-sLength);
			if(Mul.startsWith("1")){ //右移一位
				SumE++;
				M=Mul.substring(1, 1+sLength);
			}
			else{
				M=Mul.substring(2);
			}
			String E=integerRepresentation(""+SumE,eLength);
			Result+=prefix+E+M;
			
		}
		if(SumE>=max){
			Result="1"+Result.substring(1);
		}
		return Result;
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
		int max=(int)Math.pow(2, eLength)-1;
		String Result="0";
		int Ex=Integer.valueOf("0"+operand1.substring(1,1+eLength),2);
		int Ey=Integer.valueOf("0"+operand2.substring(1,1+eLength),2);
		String Mx=operand1.substring(1+eLength,1+eLength+sLength);
		String My=operand2.substring(1+eLength,1+eLength+sLength);
		int mx=Integer.valueOf(Mx);
		int my=Integer.valueOf(My);
		int SumE=-1;
		if(Ey==0&&my==0){  //除零
			String E="1";
			while(E.length()<eLength){
				E+="1";
			}
			String M="0";
			while(M.length()<sLength){
				M+="0";
			}
			Result="00"+E+M;
		}
		else if(Ex==0&&mx==0){
			String E="0";
			while(E.length()<eLength){
				E+="0";
			}
			String M="0";
			while(M.length()<sLength){
				M+="0";
			}
			Result="00"+E+M;
		}
		else{
			String prefix="";
			String M="";
			if(operand1.charAt(0)==operand2.charAt(0)){
				prefix="0";
			}
			else{
				prefix="1";
			}
			//算阶码
			SumE=Ex-Ey+((int)Math.pow(2, eLength-1)-1);
			M=integerDivision("01"+Mx,"01"+My,sLength+4);
			char re=M.charAt(M.length()-sLength-1);
			M=M.substring(M.length()-sLength,M.length());
			/*if(M.startsWith("1")){
				M=M.substring(1);
			}
			if(M.startsWith("0")&&re=='0'){
				M=M.substring(1)+"0";
				SumE--;
			}*/
			String E=integerRepresentation(""+SumE,eLength);
			Result+=prefix+E+M;
		}
		if(SumE>=max){
			Result="1"+Result.substring(1);
		}
		return Result;
	}
}
