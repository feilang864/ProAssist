package com.qtxu.excel.headler;

import com.qtxu.document.dao.ExcelDocumentSheet;
import com.qtxu.excel.dao.ColumnConfig;

public abstract class ValueHeadler {
	protected ExcelDocumentSheet eDSheet;
	protected ColumnConfig columnConfig;
	public ExcelDocumentSheet getEDSheet() {
		return eDSheet;
	}

	public void setEDSheet(ExcelDocumentSheet eDSheet) {
		this.eDSheet = eDSheet;
	}

	public ColumnConfig getColumnConfig() {
		return columnConfig;
	}

	public void setColumnConfig(ColumnConfig columnConfig) {
		this.columnConfig = columnConfig;
	}

	public abstract void excute();
}
