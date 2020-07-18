package cc.darhao.fund;

public class YourLife {

	public static void main(String[] args) {
		life();
	}

	private static void life() {
		//通货膨胀率
		final float useCpi = 1.015f;
		//当前年龄
		final int age = 25;
		//理想寿命
		final int lifeYears = 100;
		//工资每年涨幅
		final float workcpi = 1.015f;
		//理财年化收益率
		final float restYield = 0.045f;
		//每年平均开销（不包括房租） + 额外消费计划
		final int useInYear = 5000 * 12 + 10000;
		//每年平均收入 + 奖金（或副业收入）
		final int getInYear = 12000 * 12 + 18000;
		//现有初始资产
		final int initMoney = 70000;
		//房租
		final int rent = 2000;
		//买房时你的年龄
		final int ageWhenBuyHouse = 27;
		//房子首付
		final int houseFirstPay = 250000;
		//房子贷款年限
		final int houseLoanYears = 30;
		//房子月供
		final int houseMonthlyPay = 1700;
		
		int moneyUseInYear = useInYear + rent;
		int sumUse = moneyUseInYear;
		int moneyGetInYear = getInYear;
		int sumGet = moneyGetInYear;
		int sumYield = 0;
		int sumRest = initMoney;
		
		System.out.println(age + "岁前现存初始资产：" + sumRest);
		System.out.print(age + "岁年工资收入：" + moneyGetInYear);
		System.out.print("，年基础开销：" + moneyUseInYear);
		sumRest += (moneyGetInYear - moneyUseInYear);
		sumYield += sumRest * restYield;
		System.out.println("，资产结余：" + sumRest + "，理财收入：" + (int)(sumRest * restYield) 
				+ "，理财后资产结余：" + (int)(sumRest + sumRest * restYield));
		sumRest += sumRest * restYield;
		
		for (int i = age; i <= lifeYears; i++) {
			if(i < 60) {
				moneyGetInYear *= workcpi;
				System.out.print(i + "岁年工资收入：" + moneyGetInYear);
				sumGet += moneyGetInYear;
			}else {
				System.out.print(i + "岁年工资收入：0");
			}
			
			moneyUseInYear *= useCpi;
			if(i == ageWhenBuyHouse) {
				System.out.print("，【买房】首付：" + houseFirstPay);
				sumUse += houseFirstPay;
				sumRest -= houseFirstPay;
				moneyUseInYear += houseMonthlyPay;
				moneyUseInYear -= rent;
			}
			if(i == ageWhenBuyHouse + houseLoanYears) {
				moneyUseInYear -= houseMonthlyPay;
			}
			System.out.print("，年基础开销：" + moneyUseInYear);
			sumUse += moneyUseInYear;
			
			if(i < 60) {
				sumRest += (moneyGetInYear - moneyUseInYear);
			}else {
				sumRest += (0 - moneyUseInYear);
			}
			
			sumYield += sumRest * restYield;
			System.out.println("，资产结余：" + sumRest + "，理财收入：" + (int)(sumRest * restYield) 
					+ "，理财后资产结余：" + (int)(sumRest + sumRest * restYield));
			sumRest += sumRest * restYield;
		}
		
		System.out.print("人生总工资收入：" + sumGet);
		System.out.print("，理财总收入：" + sumYield);
		System.out.print("，总开销：" + sumUse);
		System.out.println("，总资产结余：" + sumRest);
	}
	
}
