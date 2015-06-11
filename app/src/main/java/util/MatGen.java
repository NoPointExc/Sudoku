package util;

import java.util.*;
public  class MatGen{
	public static int[][] generat(){
		int[][] matr=new int[9][9];
		final int[][] MAP= {
				{8,6,7,2,0,1,5,3,4},
				{2,0,1,5,3,4,8,6,7},
				{5,3,4,8,6,7,2,0,1},
				{6,7,8,0,1,2,3,4,5},
				{0,1,2,3,4,5,6,7,8},
				{3,4,5,6,7,8,0,1,2},
				{7,8,6,1,2,0,4,5,3},
				{1,2,0,4,5,3,7,8,6},
				{4,5,3,7,8,6,1,2,0}
		};
		int[] center=genCenter();
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				matr[i][j]=center[MAP[i][j]];
			}
		}
		//swap random
		Random random=new Random();
		for(int k=0;k<3;k++){
			int i=random.nextInt(3)+k*3;
			int j=random.nextInt(3)+k*3;
			swapCol(i,j,matr);
			i=random.nextInt(3)+k*3;
			j=random.nextInt(3)+k*3;
			swapRow(i,j,matr);
		}
		return matr;
	}
	public static int[][] remove(int k, int[][] m){
		int[][] matr=m;
		Random random=new Random();		
		for(int i=0;i<k;i++){
			int index=random.nextInt(81);
			matr[index/9][index%9]=0;
		}
		return matr;
	}

	private static int[] genCenter(){
		int[] center=new int[9];
		ArrayList<Integer> num=new ArrayList<Integer>();
		int len=9;
		Random random=new Random();
		for(int i=1;i<10;i++){
			num.add(i);
		} 
		while(len>0){
			int index=random.nextInt(len);
			center[9-len]=num.remove(index);;
			len--;
		}
		return center;
	}

	public static void swapCol(int i,int j, int[][] matr){
		if(i==j) return;
		for(int k=0;k<9;k++){
			int tmp=matr[i][k];
			matr[i][k]=matr[j][k];
			matr[j][k]=tmp;
		}
	}
	public static void swapRow(int i, int j,int[][] matr){
		if(i==j) return;
		for(int k=0;k<9;k++){
			int tmp=matr[k][i];
			matr[k][i]=matr[k][j];
			matr[k][j]=tmp;
		}
	}



	public static void print(int[][] matr){		
		int len=matr.length;
		for(int i=0;i<len;i++){
			StringBuilder sb=new StringBuilder("");	
			for(int j=0;j<len;j++){
				sb.append(matr[i][j]);
				sb.append(" ");
			}
			System.out.println(sb.toString());
		}
	}
	public static void print(int[] num){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<num.length;i++){
			sb.append(num[i]);
			sb.append(" ");
		}
		System.out.println(sb.toString());
	}

}