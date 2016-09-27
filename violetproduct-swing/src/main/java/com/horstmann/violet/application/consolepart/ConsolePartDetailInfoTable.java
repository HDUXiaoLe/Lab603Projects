package com.horstmann.violet.application.consolepart;
import java.awt.Color;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.horstmann.violet.application.menu.util.zhangjian.Database.AbstractTestCaseVO;
import com.horstmann.violet.application.menu.util.zhangjian.Database.RealTestCaseVO;

public class ConsolePartDetailInfoTable extends JTable {

	private static final long serialVersionUID = -8389977798357867875L;
	private DefaultTableModel defaultTableModel;
	private final Object[] columnNames = { "序号", "测试用例名称","测试用例路径","备注"};

	public ConsolePartDetailInfoTable(int index) {

		defaultTableModel = new DefaultTableModel(columnNames, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.setModel(defaultTableModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowSelectionAllowed(true);
		this.setGridColor(Color.BLACK);
		this.setShowGrid(true);
		this.setShowHorizontalLines(true);
		this.setShowVerticalLines(true);
		this.setFillsViewportHeight(true);
		this.setDefaultEditor(Object.class, cellEditor);
		this.setColumnWidth(0, 50);
		this.doLayout();
		initRowsData(index);
	}    
	public void initRowsData(int i) {
		
		this.removeRowsData();
		if(i==1){//实例化
	
		
				List<RealTestCaseVO> list = ConsolePartDataTestDao.getRealTestCaseList();
		        Object[] rowData = new Object[columnNames.length];
		        int index = 1;// 用来显示序号的
		        int Index=0;
				// TODO Auto-generated method stub
		        while(Index+47<list.size()){
		        	
					//	Thread.sleep(1000);
						if (list != null && !list.isEmpty()) {
							for (RealTestCaseVO info : list.subList(Index, Index+47)) {
								rowData[0] = index++;// 用来显示序号的
								rowData[1] = info.getName();//测试用例名称								
								rowData[2] = info.getProcessList();//路径
								rowData[3] = info.getRemark();// 备注			
								defaultTableModel.addRow(rowData);	
								defaultTableModel.fireTableDataChanged();													
							}			
						      }	
						Index+=47;										
		        }																										        		
		}
		if(i==0)//抽象
		{
			List<AbstractTestCaseVO> list = ConsolePartDataTestDao.getAbsTestCaseList();
			Object[] rowData = new Object[columnNames.length];
			int index = 1;// 用来显示序号的
	  
			if (list != null && !list.isEmpty()) {
				for (AbstractTestCaseVO info : list) {
					rowData[0] = index++;// 用来显示序号的
					rowData[1] = info.getName();//测试用例名称
					
					rowData[2] = info.getTextRouter();//路径
					rowData[3] = info.getRemark();// 备注
					
					defaultTableModel.addRow(rowData);
				}
			}
		}
		this.revalidate();		
	}

	private void removeRowsData() {
		int count = defaultTableModel.getRowCount();
		for (count -= 1; count > -1; count--) {
			defaultTableModel.removeRow(count);
		}
	}

	private void setColumnWidth(int columnIndex, int width) {
		TableColumn column = this.getColumnModel().getColumn(columnIndex);
		column.setPreferredWidth(width);
		column.setMaxWidth(width);
		column.setMinWidth(width);
	}
}
