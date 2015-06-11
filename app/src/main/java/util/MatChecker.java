package util;

public class MatChecker{
	public static boolean isCompleted(int[][] matr){
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				if(matr[i][j]==0) return false;
			}
		}
		return true;
	}
	
	public static boolean[] checkRow(int[][] matr){
		boolean[] res=new boolean[9];
		for(int i=0;i<9;i++){
			int sum=0;
			for(int j=0;j<9;j++){
				sum=sum+matr[j][i];
			}
			res[i]=(sum==45);
		}
		return res;
	}

	public static boolean[] checkCol(int[][] matr){
		boolean[] res=new boolean[9];
		for(int i=0;i<9;i++){
			int sum=0;
			for(int j=0;j<9;j++){
				sum=sum+matr[i][j];
			}
			res[i]=(sum==45);
		}
		return res;
	}

	public static boolean[] checkBlock(int[][] matr){
		boolean[] res=new boolean[9];
		for(int i=0;i<9;i++){
			int sum=0;
			for(int j=0;j<9;j++){
				sum=sum+matr[3*(i%3)+j%3][3*(i/3)+j/3];
			}
			res[i]=(sum==45);
		}
		return res;
	}

	public static void print(boolean[] num){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<num.length;i++){
			sb.append(num[i]);
			sb.append(" ");
		}
		System.out.println(sb.toString());
	}

}