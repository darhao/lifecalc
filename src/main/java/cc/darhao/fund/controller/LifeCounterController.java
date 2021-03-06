package cc.darhao.fund.controller;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;

import cc.darhao.dautils.api.TextFileUtil;

/**
 * @author DarHao
 * 人生计算器控制器
 */
public class LifeCounterController extends Controller{

	public void index() {
		render("lifeCounter.html");
	}
	
	
	public void save(String name,float useCpi,  int age,  int lifeYears,  int retireAge,  float workcpi,
			 float restYield,  int useInYear,  int getInYear,  int getInYearRetire, int initMoney,  int rent,
			 int ageWhenBuyHouse,  int houseFirstPay,  int houseLoanYears,  int houseMonthlyPay,
			 int ageWhenBuyCar,  int carFirstPay,  int carLoanYears,  int carMonthlyPay,
			 String children,  int childFeeInYear,  int childIndieAge ) {
		if(name == null || name.isEmpty()) {
			renderText("fail");
			return;
		}
		JSONObject object = new JSONObject();
		object.put("useCpi", useCpi);
		object.put("age", age);
		object.put("lifeYears", lifeYears);
		object.put("retireAge", retireAge);
		object.put("workcpi", workcpi);
		object.put("restYield", restYield);
		object.put("useInYear", useInYear);
		object.put("getInYear", getInYear);
		object.put("getInYearRetire", getInYearRetire);
		object.put("initMoney", initMoney);
		object.put("rent", rent);
		object.put("ageWhenBuyHouse", ageWhenBuyHouse);
		object.put("houseFirstPay", houseFirstPay);
		object.put("houseLoanYears", houseLoanYears);
		object.put("houseMonthlyPay", houseMonthlyPay);
		object.put("ageWhenBuyCar", ageWhenBuyCar);
		object.put("carFirstPay", carFirstPay);
		object.put("carLoanYears", carLoanYears);
		object.put("carMonthlyPay", carMonthlyPay);
		object.put("children", children);
		object.put("childFeeInYear", childFeeInYear);
		object.put("childIndieAge", childIndieAge);
		try {
			TextFileUtil.writeToFile(name + ".sav", object.toJSONString());
			renderText("succ");
		} catch (IOException e) {
			renderText(e.getMessage());
		}
	}
	
	
	public void load(String name) {
		if(name == null || name.isEmpty()) {
			renderText("fail");
			return;
		}
		try {
			String json = TextFileUtil.readFromFile(name + ".sav");
			renderText(json);
		} catch (IOException e) {
			renderText(e.getMessage());
		}
	}
	
	
	public void count(float useCpi,  int age,  int lifeYears,  int retireAge,  float workcpi,
	 float restYield,  int useInYear,  int getInYear,  int getInYearRetire, int initMoney,  int rent,
	 int ageWhenBuyHouse,  int houseFirstPay,  int houseLoanYears,  int houseMonthlyPay,
	 int ageWhenBuyCar,  int carFirstPay,  int carLoanYears,  int carMonthlyPay,
	 String children,  int childFeeInYear,  int childIndieAge ){
		String[] childrenStrings = children.split(",");
		int[] childrenI = new int[childrenStrings.length];
		for (int i = 0; i < childrenI.length; i++) {
			childrenI[i] = Integer.parseInt(childrenStrings[i]);
		}
		 String result = realCount(useCpi, age, lifeYears, retireAge, workcpi, restYield, useInYear, 
				 getInYear, getInYearRetire, initMoney, rent, ageWhenBuyHouse, houseFirstPay, houseLoanYears, 
				 houseMonthlyPay, ageWhenBuyCar, carFirstPay, carLoanYears, carMonthlyPay, childrenI, childFeeInYear, childIndieAge);
		 renderText(result);
	 }
	
	
	private String realCount(float useCpi,  int age,  int lifeYears,  int retireAge,  float workcpi,
			 float restYield,  int useInYear,  int getInYear,  int getInYearRetire,  int initMoney,  int rent,
			 int ageWhenBuyHouse,  int houseFirstPay,  int houseLoanYears,  int houseMonthlyPay,
			 int ageWhenBuyCar,  int carFirstPay,  int carLoanYears,  int carMonthlyPay,
			 int[] children,  int childFeeInYear,  int childIndieAge) {
		
		//临时变量
		int moneyUseInYear = useInYear + rent * 12;
		int sumUse = moneyUseInYear;
		int moneyGetInYear = getInYear;
		int moneyGetInYearRetire = getInYearRetire;
		int sumGet = moneyGetInYear;
		int sumGetRetire = moneyGetInYearRetire;
		int sumYield = 0;
		int sumRest = initMoney;
		StringBuilder sb = new StringBuilder();
		StringBuilder sumRestString = new StringBuilder();
		
		//初始情况
		sb.append(age + "岁前现存初始资产：" + sumRest + "<br>");
		
		//计算往后余生情况
		for (int i = age; i <= lifeYears; i++) {
			//计算每年理财收入
			int moneyYieldInYear = (int) (restYield * sumRest);
			sumYield += moneyYieldInYear;
			sb.append(i + "岁那一年的理财收入：" + moneyYieldInYear);
			
			//计算每年基本收入
			if(i < retireAge) {//是否已经退休
				moneyGetInYear *= (1 + workcpi);
				sb.append("，年工资收入：" + moneyGetInYear);
				sumGet += moneyGetInYear;
			}else {
				moneyGetInYearRetire *= (1 + useCpi);
				sb.append("，年退休收入：" + moneyGetInYearRetire);
				
			}
			
			//计算每年支出
			moneyUseInYear *= (1+useCpi);
			if(i == ageWhenBuyHouse) {//是否买房年
				sb.append("，【买房】首付：" + houseFirstPay);
				sumUse += houseFirstPay;
				sumRest -= houseFirstPay;
				moneyUseInYear += (houseMonthlyPay * 12);
				moneyUseInYear -= (rent * 12);
			}
			if(i == ageWhenBuyHouse + houseLoanYears) {//是否月供房子结束年
				moneyUseInYear -= (houseMonthlyPay * 12);
				sb.append("，【房子】供完了");
			}
			if(i == ageWhenBuyCar) {//是否买车年
				sb.append("，【买车】首付：" + carFirstPay);
				sumUse += carFirstPay;
				sumRest -= carFirstPay;
				moneyUseInYear += (carMonthlyPay * 12);
			}
			if(i == ageWhenBuyCar + carLoanYears) {//是否月供车子结束年
				sb.append("，【车子】供完了");
				moneyUseInYear -= (carMonthlyPay * 12);
			}
			for (int childBornYouAge : children) {//育儿经费
				if(i == childBornYouAge) {
					sb.append("，1个【孩子】诞生了");
					moneyUseInYear += childFeeInYear;
				}
				if(i == childBornYouAge + childIndieAge) {
					sb.append("，1个【孩子】经济独立了");
					moneyUseInYear -= childFeeInYear;
				}
			}
			sb.append("，年基础开销：" + moneyUseInYear);
			sumUse += moneyUseInYear;
			
			//计算每年基本结余
			int moneyRestInYear = 0;
			if(i < retireAge) {//是否已经退休
				moneyRestInYear = moneyGetInYear - moneyUseInYear;
			}else {
				sumRest += (moneyGetInYearRetire - moneyUseInYear);
			}
			
			//计算每年最终结余
			sumRest += (moneyRestInYear + moneyYieldInYear);
			sumRestString.append(sumRest + ",");
			sb.append("，资产结余：" + sumRest + "<br>");
		}
		
		sb.append("人生基本资收入：" + sumGet);
		sb.append("，总退休收入：" + sumGetRetire);
		sb.append("，总理财收入：" + sumYield);
		sb.append("，总开销：" + sumUse);
		sb.append("，总资产结余：" + sumRest + "<br>");
		sumRestString.deleteCharAt(sumRestString.length() - 1);
		return sb.toString() + "|" + sumRestString.toString();
	}


	public static void main(String[] args) {
		//平均每年通货膨胀率
		final float useCpi = 0.015f;
		//当前你的年龄
		final int age = 25;
		//你理想中的寿命
		final int lifeYears = 100;
		//计划退休年龄
		final int retireAge = 35;
		//工资每年平均涨幅
		final float workcpi = 0.015f;
		//理财平均年化收益率（必须为正数，当你资产结余为负数时，该参数会成为负债的利率）
		final float restYield = 0.08f;
		//【注意：请为你家人上好医疗保险和重疾险并将保险费包含在此参数中，本计算器不考虑重大意外情况，祝你和家人一生平安】
		//每年平均开销（不包括房租） + 额外消费计划如旅行等
		final int useInYear = 5000 * 12 + 10000;
		//每年平均收入
		final int getInYear = 12000 * 12 + 18000;
		//退休每年平均收入
		final int getInYearRetire = 2000 * 12;
		//你现有的净资产（可以为负数）
		final int initMoney = 70000;
		//你现在的每月房租（现没租房的输入0）
		final int rent = 2000;
		//买房时你的年龄（不买房的填0）
		final int ageWhenBuyHouse = 27;
		//房子首付（不买房的填0）
		final int houseFirstPay = 250000;
		//房子贷款年限（不买房的填0，全款买房的填-1）
		final int houseLoanYears = 30;
		//房子月供（不买房的、全款买房的请填0）
		final int houseMonthlyPay = 1700;
		//买车时你的年龄（不买车的填0）
		final int ageWhenBuyCar = 40;
		//车子首付（不买车的填0）
		final int carFirstPay = 80000;
		//车子贷款年限（不买车的填0，全款买车的填-1）
		final int carLoanYears = 5;
		//车子月供（不买车的、全款买车的请填0）
		final int carMonthlyPay = 3000;
		//每个孩子出生时你的年龄（要多个孩子用英文逗号分开，不要孩子填0）
		final int[] children = {28, 32};
		//每个孩子每年的费用（从出生的奶粉钱到大学毕业前每年的学费、伙食费、礼物等）（不要孩子的请填0）
		final int childFeeInYear = 12000;
		//孩子能独立生活时的他们的年龄（不要孩子的请填0）
		final int childIndieAge = 22;
		
		System.out.print(new LifeCounterController().realCount(useCpi, age, lifeYears, retireAge, workcpi, restYield, useInYear, getInYear, 
				getInYearRetire, initMoney, rent, ageWhenBuyHouse, houseFirstPay, houseLoanYears, houseMonthlyPay, ageWhenBuyCar, carFirstPay,
				carLoanYears, carMonthlyPay, children, childFeeInYear, childIndieAge));
	}
	
}
