package cc.darhao.fund;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * @author DarHao
 * 普通定投回测
 */
public class NormalFundTester {
	public static void main(String[] args) throws IOException {
		OkHttpClient client = new OkHttpClient();
		List<Float> anns = new LinkedList<Float>();
		//博时沪深300A：050002
		//博时黄金ETFA：002610
		//博时标普500ETF联接A：050025
		final String fundCode = "050025";
		final int s = 2012;
		final int e = 2020;
		for (int sdate = s; sdate < e; sdate++) {
			for (int edate = sdate + 1; edate < e + 1; edate++) {
				String url = "http://fund.eastmoney.com/data/FundInvestCaculator_AIPDatas.aspx?"
						+ "fcode="+ fundCode +"&sdate=" + sdate + "-1-1&edate=" + edate + "-1-1&shdate=" + edate
						+ "-1-1&round=1&dtr=1&p=0.15&je=500&stype=1&needfirst=2&jsoncallback=FundDTSY.result";
				Request request = new Request.Builder().url(url).get().build();
				Call call = client.newCall(request);
				String result = call.execute().body().string();
				String yield = result.split("\\|")[6].replace("%", "");
				if(yield.contains("-")) {
					System.out.print("□");
				}else {
					System.out.print("■");
				}
				float annualizedYield = Float.parseFloat(yield) / (edate - sdate);
				anns.add(annualizedYield);
				System.out.println(sdate + " - " + edate + " : " + annualizedYield + "%");
			}
		}
		float sum = 0;
		for (Float float1 : anns) {
			sum+=float1;
		}
		System.out.println("平均年化：" + sum/anns.size() + "%");
	}

}
