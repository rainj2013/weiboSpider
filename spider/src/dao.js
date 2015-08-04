var ioc = {
		 // 数据源
		druidDataSource : {
			type :"com.alibaba.druid.pool.DruidDataSource",
			events : {
				create:'init',
				depose :"close"
			},
			fields : {
                url:'jdbc:mysql://localhost:3306/weibodata',
                username:'root',
                password:'root',
				filters:'stat',
				maxActive:20,
				initialSize:1,
				maxWait:60000
			}
		},
		dao : {
			type : "org.nutz.dao.impl.NutDao",
			args : [{refer:'druidDataSource'}]
		},
};