package cc.darhao.fund;

public class YourLife {

	public static void main(String[] args) {
		life();
	}

	private static void life() {
		//平均每年通货膨胀率
		final float useCpi = 1.015f;
		//当前你的年龄
		final int age = 25;
		//你理想中的寿命
		final int lifeYears = 100;
		//计划退休年龄
		final int retireAge = 35;
		//工资每年平均涨幅
		final float workcpi = 1.015f;
		//理财平均年化收益率（必须为正数，当你资产结余为负数时，该参数会成为负债的利率）
		final float restYield = 0.08f;
		//【注意：请为你家人上好医疗保险和重疾险并包含在此参数中，本计算器不考虑重大意外情况，祝你和家人一生平安】
		//每年平均开销（不包括房租） + 额外消费计划如旅行等
		final int useInYear = 5000 * 12 + 10000;
		//每年平均收入 + 奖金或副业等收入
		final int getInYear = 12000 * 12 + 18000;
		//你现有的净资产（可以为负数）
		final int initMoney = 70000;
		//你现在的每月房租（现没租房的输入0）
		final int rent = 2000;
		//买房时你的年龄（不买房的填0）
		final int ageWhenBuyHouse = 27;
		//房子首付（不买房请随便填）
		final int houseFirstPay = 250000;
		//房子贷款年限（不买房请随便填，全款买房的填-1）
		final int houseLoanYears = 30;
		//房子月供（不买房的、全款买房的请随便填）
		final int houseMonthlyPay = 1700;
		//买车时你的年龄（不买车的填0）
		final int ageWhenBuyCar = 30;
		//车子首付（不买车请随便填）
		final int carFirstPay = 80000;
		//车子贷款年限（不买车请随便填，全款买车的填-1）
		final int carLoanYears = 5;
		//车子月供（不买车的、全款买车的请随便填）
		final int carMonthlyPay = 3000;
		//每个孩子出生时你的年龄（要多个孩子用逗号分开，不要孩子可不填）
		final int[] children = {28, 32};
		//每个孩子每年的费用（从出生的奶粉钱到大学毕业前每年的学费、伙食费、礼物等）
		final int childFeeInYear = 12000;
		//孩子能独立生活时的他们的年龄（不要孩子请填-999）
		final int childIndieAge = 22;
		
		//临时变量
		int moneyUseInYear = useInYear + rent;
		int sumUse = moneyUseInYear;
		int moneyGetInYear = getInYear;
		int sumGet = moneyGetInYear;
		int sumYield = 0;
		int sumRest = initMoney;
		
		//计算第一年情况
		System.out.println(age + "岁前现存初始资产：" + sumRest);
		System.out.print(age + "岁年工资收入：" + moneyGetInYear);
		System.out.print("，年基础开销：" + moneyUseInYear);
		sumRest += (moneyGetInYear - moneyUseInYear);
		sumYield += sumRest * restYield;
		System.out.println("，资产结余：" + sumRest + "，理财收入：" + (int)(sumRest * restYield) 
				+ "，理财后资产结余：" + (int)(sumRest + sumRest * restYield));
		sumRest += sumRest * restYield;
		
		//计算往后余生情况
		for (int i = age; i <= lifeYears; i++) {
			//计算每年工资收入
			if(i < retireAge) {//是否已经退休
				moneyGetInYear *= workcpi;
				System.out.print(i + "岁年工资收入：" + moneyGetInYear);
				sumGet += moneyGetInYear;
			}else {
				System.out.print(i + "岁年工资收入：0");
			}
			
			//计算每年平均开销
			moneyUseInYear *= useCpi;
			if(i == ageWhenBuyHouse) {//是否买房年
				System.out.print("，【买房】首付：" + houseFirstPay);
				sumUse += houseFirstPay;
				sumRest -= houseFirstPay;
				moneyUseInYear += houseMonthlyPay;
				moneyUseInYear -= rent;
			}
			if(i == ageWhenBuyHouse + houseLoanYears) {//是否还在月供房子
				moneyUseInYear -= houseMonthlyPay;
			}
			if(i == ageWhenBuyCar) {//是否买车年
				System.out.print("，【买车】首付：" + carFirstPay);
				sumUse += carFirstPay;
				sumRest -= carFirstPay;
				moneyUseInYear += carMonthlyPay;
			}
			if(i == ageWhenBuyCar + carLoanYears) {//是否还在月供车子
				moneyUseInYear -= carMonthlyPay;
			}
			System.out.print("，年基础开销：" + moneyUseInYear);
			sumUse += moneyUseInYear;
			
			//计算每年结余
			if(i < retireAge) {//是否已经退休
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
