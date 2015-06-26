package com.love320.stats.core;

import java.util.List;

public class Config {
	
	/**
	 * 唯一标注索引
	 */
	private String index;
	
	/**
	 * 取上"index"结果中再处理
	 */
	private String srcIndex;
	
	/**
	 * 过滤名称组
	 */
	private List<String> filters;
	
	/**
	 * 统计的value名称
	 */
	private String value;
	
	/**
	 * 写入数据到库字段
	 */
	private List<String> columns;

	/**
	 * 持久存储的方式
	 * false:浮点[ConcurrentLinkedQueue]-
	 * true:字符[ConcurrentHashMap]
	 */
	private boolean wayFT = true;

    /**
     * 持久到DB中
     * false:不进入DB
     * true:进入DB
     */
    private boolean toDB = false;
	
	/**
	 * 数据的类型
	 * 
	 * 数据类型            大小       范围                                             默认值 
	 * 
	 * 0 byte(字节) 	    8         -128 - 127                                           0
	 * 1 shot(短整型)        16      -32768 - 32768                                         0
	 * 2 int(整型)           32   -2147483648-2147483648                                    0
	 * 3 long(长整型)        64   -9233372036854477808-9233372036854477808                  0        
	 * 4 float(浮点型)       32  -3.40292347E+38-3.40292347E+38                            0.0f
	 * 5 double(双精度)	    64  -1.79769313486231570E+308-1.79769313486231570E+308        0.0d
	 * 6 char(字符型)        16         ‘ \u0000 - u\ffff ’                             ‘\u0000 ’
	 * 7 boolean(布尔型)     1         true/false                                         false
	 * 8 string(字符串)
	 */
	private int type = 8;
	
	/**
	 * 主存储-备存储
	 */
	private boolean master;
	
	/**
	 * 获取信息时间Cron
	 */
	private String cron;
	
	/**
	 * 获取信息前暂停时间,作用是使用活动数据库完全变为非活动数据库
	 */
	private int sleep = 3000;
	
	/**
	 * 持久存储的表
	 */
	private String table;

	public List<String> getFilters() {
		return filters;
	}

	public void setFilters(List<String> filters) {
		this.filters = filters;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}



	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public String getIndex() {
		return index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public boolean isMaster() {
		return master;
	}

	public void setMaster(boolean master) {
		this.master = master;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSrcIndex() {
		return srcIndex;
	}

	public void setSrcIndex(String srcIndex) {
		this.srcIndex = srcIndex;
	}

    public boolean isWayFT() {
        return wayFT;
    }

    public void setWayFT(boolean wayFT) {
        this.wayFT = wayFT;
    }

    public boolean isToDB() {
        return toDB;
    }

    public void setToDB(boolean toDB) {
        this.toDB = toDB;
    }
}
