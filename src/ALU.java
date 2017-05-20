/**
 * ģ��ALU���������͸���������������
 * @author [161250125_���Ʒ�]
 *
 */

public class ALU {

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
	 */
	public static String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.	
		String str="";
		if(number.substring(0,1).equals("-")){// ����
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
			}//ȡ���Ĺ���
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
			
			}	//��1�Ĺ���
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
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
			char[] Onearray=tempOne.toCharArray();//�õ�С����ǰ�������Ķ����Ʊ�ʾOnearray
			////
			String tempTwo="0"+"."+TwoString[1];
			Double temptwo=Double.valueOf(tempTwo);//temptwo��С��
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
			}//�õ�С���Ķ����Ʊ�ʾTwoarray
			//////
			char[] FinalChar=new char[Onearray.length+23+1];
			for(int i=0;i<Onearray.length;i++){
				FinalChar[i]=Onearray[i];
			}
			FinalChar[Onearray.length]='.'; //����ǰС�����λ���±���  Onearray.length
			for(int i=Onearray.length+1;i<Onearray.length+23+1;i++){
				FinalChar[i]=Twoarray[i-(Onearray.length+1)];
			}//�ѽ�numberת�ɶ����Ʊ�ʾ���������� FinalChar��
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
					isOne=i;//�ҵ�1��λ���ˣ�
					Exist1=true;
					break;
				}
			}
			int e=Onearray.length-isOne-1;
			if(e>0&&Exist1==true){
				e=Onearray.length-isOne-1+127;// ����e+��127
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
			E=E.substring(E.length()-eLength,E.length());  //�����StringΪE
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
					// β����StringΪs
			
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
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 */
	public static String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		String result="";
		if(operand.startsWith("1")){//����
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
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
	 */
	public static String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		String result="";
		if(operand.startsWith("1")){
			result="-";
		}
		
		int SisZero=Integer.valueOf(operand.substring(1+eLength),2);
		int EisZero=Integer.valueOf(operand.substring(1, 1+eLength),2);
		if(EisZero==0){//����ȫΪ0        �ǹ����
			String S=operand.substring(1+eLength);//β��
			char[] myS=S.toCharArray();
			double valueS=0;
			for(int i=0;i<myS.length;i++){
				if(myS[i]=='1'){
					valueS+=Math.pow(2, -(i+1));
				}
			}
			valueS=valueS*Math.pow(2, -126);
			result+=valueS;
		}//�ǹ��������
		
		if(EisZero==(Math.pow(2, eLength)-1)){//����ȫΪ1     
			if(SisZero==0){// β��ȫΪ0   Inf
				result=result+"Inf";
			}
			else{// NaN
				result="NaN";
			}
		}
		
		else{
			int valueE=EisZero-127;
			String S=operand.substring(1+eLength);//β��
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
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
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
		}//ȡ��
		
		
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
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
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
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
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
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
	 */
	public static String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		int length=operand.length();
		if(operand.startsWith("1")){//����
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
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public static String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		char m,n;    //m��ʾ��λ��n��ʾ��
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
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
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
			myChar=fullAdder(char1,char2,myChar).charAt(0);//��λ
		}
		String str=myChar+String.valueOf(result);
		return str;
	}

	/**
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
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
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
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
		
		//��ȫ
		String result="";
		for(int i=0;i<length/4;i++){
			if(i==0){
				result=claAdder(operand1.substring(length-4,length),operand2.substring(length-4, length),c);
			}
			//ĩ��λ��ӵõ�һ����λ  ��λ���
			else{
				result=claAdder(operand1.substring(length-((i+1)*4),length-(i*4)),operand2.substring(length-((i+1)*4),length-(i*4)),result.charAt(0))+result.substring(1);
			}
		}
		return result;
	}

	/**
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>

	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public static String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result=adder(operand1, operand2,'0',length);
		return result;
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public static String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String result="";
		int op2=Integer.valueOf(integerTrueValue(operand2));
		op2*=(-1);
		String OP2=Integer.toBinaryString(op2);//OP2Ϊ�����ĸ����Ĳ���
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
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
	 */
	public static String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		while(operand1.length()<length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//��ȫ
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
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public static String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		
		while(operand1.length()<2*length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//��ȫ   X.length=2*length
		String X=operand1;
		String Y=operand2;
		String R1="0";
		String[] R=new String[length];
		int mid=Integer.valueOf(integerTrueValue(operand2));
		mid*=(-1);
		String FY=integerRepresentation(String.valueOf(mid),length);//FY�� -Y�Ĳ���
		
		if(operand1.charAt(0)==Y.charAt(0)){//ͬ��
			R1=adder(X.substring(0, length),FY,'0',length).substring(1)+X.substring(length,2*length);
		}
		else{//��ͬ��
			R1=adder(X.substring(0, length),Y,'0',length).substring(1)+X.substring(length,2*length);
		}
		if(R1.charAt(0)==Y.charAt(0)){//R1��Yͬ��  ��Qn��1
			R1+="1";
		}
		else{
			R1+="0";
		}//Qn
		R[0]=R1.substring(1);
		int flag=0;
		boolean w=false;
		for(int i=1;i<=length-1;i++){
			if(R[i-1].endsWith("1")){//��һ����ͬ��   ������������
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
			if(Integer.valueOf(R[i].substring(0, 2*length-7))==0){//yu shu�Ѿ�Ϊ0;
				flag=i;
				w=true;
				break;
			}
		}//�������һ��
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
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
	 */
	public static String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String Result="";
		if(operand1.charAt(0)==operand2.charAt(0)){//ͬ��
			String result=adder("0"+operand1.substring(1),"0"+operand2.substring(1),'0',length);
			//if()
			Result=result.substring(0,1)+operand1.charAt(0)+result.substring(1);
		}
		else{//���
			if(operand1.startsWith("1")){//operand1 Ϊ����
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
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
}
