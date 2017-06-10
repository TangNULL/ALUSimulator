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
				String Fore=Integer.toBinaryString(Integer.valueOf(TwoString[0]));//С����ǰ�����Ķ����ơ�
				int OneInFore=0;//�������ǰ��1��λ��  0123456
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
				
				double Back=Double.valueOf("0"+"."+TwoString[1]);//  С������ 0.*******
				StringBuffer Buffer=new StringBuffer();
				if(OneInForeExist==true){//��ζ��Ҫ�ҹ�   β��ֻ��sLengthλ
					int MOVE=Fore.length()-1-OneInFore;   //Ҫ���Ƶ�λ��
					if(MOVE>=(int)Math.pow(2, eLength-1)){// �������̫���� Ҫ����Inf��Χ��
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
					else{//�������ҹ�����
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
						e=e+(int)Math.pow(2, eLength-1)-1;//����
						String E=Integer.toBinaryString(e);
						while(E.length()<eLength){
							E="0"+E;
						}
						E=E.substring(E.length()-eLength,E.length());
						result=result+E+S;
					}
					
				}
				else{//˵����Ҫ��� �����Ǻ�����
					int FirstOneInBack=0;// С���е�һ�����ֵ�1��λ��  0123456
					Double temp=Back;
					while(temp*2<1){
						temp=temp*2;
						FirstOneInBack++;
					}// ����������FirstOneInBack+1λ   ����Ҫ sLength+(FirstOneInBack+1)λ��β��
					if(FirstOneInBack+1>=(int)Math.pow(2, eLength-1)){//  ̫С��    ����ǹ�����ķ�Χ�� 
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
					else{//  ������������Ρ�
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
			if(SisZero==0){// ����β����Ϊ0
				result="0.0";
			}
			else{
				String S=operand.substring(1+eLength);//β��
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
		}//�ǹ��������
		
		else if(EisZero==(Math.pow(2, eLength)-1)){//����ȫΪ1     
			if(SisZero==0){// β��ȫΪ0   Inf
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
		int one=-1,two=-1;
		for(int i=3;i>=0;i--){
			char char1=Operand1[i];
			char char2=Operand2[i];
			result[i]=fullAdder(char1,char2,myChar).charAt(1);
			myChar=fullAdder(char1,char2,myChar).charAt(0);//��λ
		}
		String str=""+myChar+String.valueOf(result);
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
		char a=operand1.charAt(0);
		char b=operand2.charAt(0);
		
		
		
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
		char re=result.charAt(1);
		if(a==b&&a!=re){
			result="1"+result.substring(1);
		}
		else{
			result="0"+result.substring(1);
		}
		return result;
	}

	public static String myAdderJinWei(String operand1, String operand2, int length){
		
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
				result=claAdder(operand1.substring(length-4,length),operand2.substring(length-4, length),'0');
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
		String OP2=integerRepresentation(""+op2,length);//OP2Ϊ�����ĸ����Ĳ���
		char prefix=operand1.charAt(0);
		while(operand1.length()<length){
			operand1=prefix+operand1;
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
		char a=operand1.charAt(0);
		char b=operand2.charAt(0);
		while(operand1.length()<length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//��ȫ
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
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
	 */
	public static String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		char a=operand1.charAt(0);
		char b=operand2.charAt(0);
		//������չ
		while(operand1.length()<2*length){
			operand1=operand1.substring(0, 1)+operand1;
		}
		while(operand2.length()<length){
			operand2=operand2.substring(0, 1)+operand2;
		}
		//��ȫ   X.length=2*length
		
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
			String FY=integerRepresentation(""+mid,length);//FY�� -Y�Ĳ���
			while(FY.length()<length){
				FY=FY.substring(0, 1)+FY;
			}
			
			if(operand1.charAt(0)==Y.charAt(0)){//XYͬ��������  ���һλShang
				R1=myAdderJinWei(X.substring(0, length),FY,length).substring(1)+X.substring(length,2*length);
			}
			else{//��ͬ��
				R1=myAdderJinWei(X.substring(0, length),Y,length).substring(1)+X.substring(length,2*length);
			}
			if(R1.charAt(0)==Y.charAt(0)){//R1��Yͬ��  ��Q(length-1)��1   Q(length-1)~Q0
				R1+="1";
			}
			else{
				R1+="0";
			}//Qn
			R[0]=R1.substring(1);
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
			}//�������һ��
			
			if(a=='1'&&b=='0'){
				String myShang="",myYu="0";
				if(Integer.valueOf(R[length-1].substring(0,length))==0){//�����Ĵ���Ϊ0
					myShang=R[length-1].substring(length+1,2*length)+"0";///////������
				
					myYu="0";
					while(myYu.length()<length){
						myYu="0"+myYu;
					}
				
				}
				return "0"+myShang+myYu;
			}
			
			
			//�̵�����
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
				if(a!=b){//�����������ͬ��
					myShang=oneAdder(myShang).substring(1);
				}
				//����������
				
				if(myYu.charAt(0)!=a){//��������buͬ�ڱ���������
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
		if(operand1.charAt(0)==operand2.charAt(0)){//ͬ�����
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
		else{//������
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
		//�����Ĺ����
		int max=(int) Math.pow(2, eLength)-1;  //�൱��255
		String Result="0";
		
		
		int Ex=Integer.valueOf(operand1.substring(1,1+eLength),2);
		int Ey=Integer.valueOf(operand2.substring(1,1+eLength),2);
		String Mx=operand1.substring(1+eLength,1+eLength+sLength);
		String My=operand2.substring(1+eLength,1+eLength+sLength);
		int midE=Ex-Ey;
		String prefix=operand1.substring(0,1);
		if(midE>0){  //����Y
			String ReserveY=operand2.substring(operand2.length()-midE,operand2.length());//�������ƶ�������operand2�ĵ���midEλ
			My=logRightShift("1"+My.substring(0,My.length()-1),midE-1);//My������λΪ0
			String Msum="";
			if(operand1.charAt(0)==operand2.charAt(0)){//ͬ��  
				Msum=myAdderJinWei(Mx,My,sLength);
				if(Msum.startsWith("1")){//��Ҫ����
					Msum="0"+Msum.substring(1, Msum.length()-1);//ʡ������λ1
					Ex++;
				}
				else if(Msum.startsWith("0")){//û����� ������Ҫ����???
					Msum=Msum.substring(1);//����λ1
				}
				String ExtoBi=Integer.toBinaryString(Ex);
				ExtoBi=ExtoBi.substring(ExtoBi.length()-eLength,ExtoBi.length());
				//�жϽ����Ƿ����
				if(Ex>=max||Ex<=0){
					Result="1";
				}
				Result=Result+prefix+ExtoBi+Msum;
			}

			else if(operand1.charAt(0)!=operand2.charAt(0)){//ȡMy�Ĳ���   ����������λ�Ļ�������������������С����ǰ����1??????????
				My=negation(My);
				My=oneAdder(My);
				My=My.substring(1);  //My�Ĳ���  
				Msum=integerAddition(Mx,My,sLength);
				if(Msum.startsWith("0")){
					
				}
			}
			
			
			
			
		
		}
		
		if(operand1.charAt(0)==operand2.charAt(0)){//ͬ��  ���ӷ�
			
			
			if(midE<0){//�� ����������������λ��
				midE=-midE;
				int temp;
				String Temp;
				temp=Ex;
				Ex=Ey;
				Ey=temp;
				Temp=Mx;
				Mx=My;
				My=Temp;
			}
			
		//}
			
	    }
		else if(operand1.charAt(0)!=operand2.charAt(0)){//���������
			
		}
		
		
		
		
		
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
