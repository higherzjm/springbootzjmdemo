package com.zjm.kindsmodels.composite;

import java.util.List;

/*
 *  Composite模式也叫组合模式，是构造型的设
 计模式之一。通过递归手段来构造树形的对象结
 构，并可以通过一个对象来访问整个对象树。

 */
public class MainClass {
	public static void main(String[] args) {
		// test1();
		test2();
	}

	/*
	 * 实验一
	 */
	public static void test1() {
		// C盘
		Folder rootFolder = new Folder("C:");
		// allen目录
		Folder allen = new Folder("allen");// 文件夹
		File beifengFile = new File("allen.txt");// 文件
		File beifengFile2 = new File("allen2.txt");
		rootFolder.add(allen);
		rootFolder.add(beifengFile);
		rootFolder.add(beifengFile2);

		// alisa目录
		Folder alisa = new Folder("alisa");// 文件夹
		File ibeifengFile = new File("alisa1.txt");
		File ibeifengFile2 = new File("alisa2.txt");
		rootFolder.add(alisa);
		rootFolder.add(ibeifengFile);
		rootFolder.add(ibeifengFile2);

		displayTree(rootFolder, 0);

	}

	public static void displayTree(Ifile rootFolder, int deep) {
		for (int i = 0; i < deep; i++) {
			System.out.print("----");
		}
		// 显示自身的名称
		rootFolder.display();
		// 获得子树
		List<Ifile> children = rootFolder.getChild();
		// 遍历子树
		for (Ifile file : children) {
			if (file instanceof File) {
				for (int i = 0; i <= deep; i++) {
					System.out.print("--");
				}
				file.display();
			} else {
				displayTree(file, deep + 1);
			}
		}
	}

	/*
	 * 实验二
	 */
	public static void test2() {
		Folder folder = new Folder("D:");
		Folder folder2 = new Folder("allen", 0);
		File file = new File("zjm");
		File file2 = new File("every");
		folder2.add2(file);
		folder2.add2(file2);
		folder.add(folder2);

		Folder folder3 = new Folder("alisa", 0);
		File file3 = new File("xyf");
		File file4 = new File("fxy");
		folder3.add2(file3);
		folder3.add2(file4);
		folder.add(folder3);

		List<Ifile> folders = folder.getChild();
		folder.display();
		for (Ifile ifile2 : folders) {
			System.out.print("---");
			ifile2.display();
			List<Ifile> ifiles = ifile2.getChild2();
			for (Ifile ifile : ifiles) {
				ifile.display();
			}

		}

	}
}

