package cc.darhao.fund;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * @author DarHao
 * 智能定投回测
 */
public class SmartFundTester {
	public static void main(String[] args) throws IOException {
		OkHttpClient client = new OkHttpClient();
		List<Float> anns = new LinkedList<Float>();
		//博时沪深300A：050002
		//博时黄金ETFA：002610
		//博时标普500ETF联接A：050025
		final String fundCode = "050002";
		final String compareIndexCode = "sh000300";
		final int s = 2006;
		final int e = 2020;
		for (int sdate = s; sdate < e; sdate++) {
			for (int edate = sdate + 1; edate < e + 1; edate++) {
				String url = "http://www.zhitouxing.com/fund?search-fund="+ fundCode +"&selectday=0&"
						+ "fund-interv=30&fund-someday=15&fund-week=0&fund-xingqi=0&fund-start="+ sdate +"-01-01&"
						+ "fund-end="+ edate +"-01-01&fee=0&base=sh000001&high-out-m0=&high-out-b0=&"
						+ "high-out-jy0=-1&high-out-pos0=&high-out-u0=1&low-in-m0=&low-in-b0=&"
						+ "low-in-jy0=1&low-in-pos0=&low-in-u0=1&qj-out-m0=1&qj-out-b0=&qj-out-jy0=-1&"
						+ "qj-out-pos0=&qj-out-u0=1&qj-in-m0=1&qj-in-b0=&qj-in-jy0=1&qj-in-pos0=&"
						+ "qj-in-u0=1&ma-cb-h=1&ma-idx-h=" + compareIndexCode + "&ma-m=ma60&ma-c=1&ma-high-b0=25&"
						+ "ma-high-jy0=-1&ma-high-pos0=10&ma-high-u0=2&ma-high-b1=8&ma-high-jy1=1&"
						+ "ma-high-pos1=0&ma-high-u1=1&ma-high-b2=0&ma-high-jy2=1&ma-high-pos2=3000&"
						+ "ma-high-u2=1&ma-cb-l=1&ma-idx-l=" + compareIndexCode + "&ma-m=ma60&ma-low-b0=8&ma-low-jy0=1&"
						+ "ma-low-pos0=9000&ma-low-u0=1&ma-low-b1=0&ma-low-jy1=1&ma-low-pos1=6000&"
						+ "ma-low-u1=1&ttm-idx-h=0&ttm-out-b0=&ttm-out-jy0=-1&ttm-out-pos0=&ttm-out-u0=1&"
						+ "ttm-idx-l=0&ttm-in-b0=&ttm-in-jy0=1&ttm-in-pos0=&ttm-in-u0=1&pst-way=2&pst-out-b0=&"
						+ "pst-out-jy0=-1&pst-out-pos0=&pst-out-u0=1&pst-in-b0=&pst-in-jy0=1&pst-in-pos0=&"
						+ "pst-in-u0=1&bdl-day=&bdl-r=2&bdl-selectday=0&bdl-fund-interv=60&bdl-fund-week=0&"
						+ "bdl-fund-xingqi=0&va-jine=2000&va-value-a=0&va-value-u=1&va-sell=0&va-buy=0&"
						+ "va-h=1&va-l=1&va-buys=0&fund-jine=1000&fund-rate=0&i_more_f=0&more_f=";
				System.out.println(url);
				Request request = new Request.Builder().url(url).get().build();
				Call call = client.newCall(request);
				String result = call.execute().body().string();
				int yieldStringIndex = result.indexOf("<tr><th>收益率");
				result = result.substring(yieldStringIndex, result.indexOf("%", yieldStringIndex));
				String yield = result.substring(result.lastIndexOf(">") + 1);
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
