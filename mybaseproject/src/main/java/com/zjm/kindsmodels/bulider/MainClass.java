package com.zjm.kindsmodels.bulider;

/*
 * BuilderģʽҲ�н�����ģʽ����������ģʽ��
 ����GoF�����23�����ģʽ�е�һ�֡�Builderģʽ��һ�ֶ��󴴽���ģʽ֮һ������
 ���ظ��϶���Ĵ������̣����Ѹ��϶���Ĵ���
 ���̼��Գ���ͨ������̳к����صķ�ʽ����
 ̬�ش������и������ԵĶ���
 */
public class MainClass {
	public static void main(String[] args) {
		HouseBulider GYbulider = new GongyuBulider();//��Ԣ���̶�
		HouseBulider PFbulider = new PingfanBulider();//ƽ�����̶�
		new Housedirector(PFbulider);//ƽ������ʦ    ���ظ��϶���Ĵ�������
		new Housedirector(GYbulider);//��Ԣ����ʦ    ���ظ��϶���Ĵ�������
		House house = PFbulider.getHouse();
		House house2 = GYbulider.getHouse();
		System.out.println(house.getFloor());
		System.out.println(house.getWall());
		System.out.println(house2.getFloor());
		System.out.println(house2.getWall());

	}
}

