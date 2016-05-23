package com.horstmann.violet.application.consolepart;

import java.util.ArrayList;
import java.util.List;

public class ConsolePartDataTestDao {
	/**
	 * 获取详细信息
	 * @return
	 */
	public static List<ConsolePartDetailInfo> getDetailInfoList(int index) {
		List<ConsolePartDetailInfo> details = new ArrayList<ConsolePartDetailInfo>();
		switch (index) {
		case 0:
			details.add(new ConsolePartDetailInfo("1","testcase1_done","抽象测试用例1成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("2","testcase2_done","抽象测试用例2成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("3","testcase3_done","抽象测试用例3成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("4","testcase4_done","抽象测试用例4成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("5","testcase5_done","抽象测试用例5成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("6","testcase6_done","抽象测试用例6成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("7","testcase7_done","抽象测试用例7成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("8","testcase8_done","抽象测试用例8成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("9","testcase9_done","抽象测试用例9成功生成","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("10","testcase10_done","抽象测试用例10成功生成","2016/5/14",""));
			break;
		case 1:
			details.add(new ConsolePartDetailInfo("1","testcase1_done","抽象测试用例1实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("2","testcase2_done","抽象测试用例2实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("3","testcase3_done","抽象测试用例3实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("4","testcase4_done","抽象测试用例4实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("5","testcase5_done","抽象测试用例5实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("6","testcase6_done","抽象测试用例6实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("7","testcase7_done","抽象测试用例7实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("8","testcase8_done","抽象测试用例8实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("9","testcase9_done","抽象测试用例9实例化成功","2016/5/14",""));
			details.add(new ConsolePartDetailInfo("10","testcase10_done","抽象测试用例10实例化成功","2016/5/14",""));
			break;
		}
		
		return details;
	}

}
