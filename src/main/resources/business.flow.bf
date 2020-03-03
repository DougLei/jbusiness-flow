// 统一说明
// 1.数据类型: 1(string), 2(byte), 3(short), 4(integer), 5(long), 6(float), 7(double), 8(boolean), 9(date), 10(array), 11(list), 12(object)(使用map实现), 每种类型会有自己的默认初始值(和java的标准一致)
// 2.参数范围: 1(输入参数), 2(输入输出参数), 3(输出参数), 4(全局参数), 5(本地参数, 遵循java变量的生命周期), 参数的范围从大到小排序为: 1=2=3>4>5
// 3.在给定参数名和参数的范围时, 参数名可以通过使用xxx.xx形式, 例如scope=1, paramName=user.name表示, 取输入参数中, 参数名为user数据的name属性值, 其他还例如 users.size, 表示取users集合或数组的长度, 等等
// 4.在配置value(固定值)时, 其值的类型可能是string, integer, date, boolean, 所以value后的双引号可有可无, 具体根据用户配置的值决定
// 5.在配置actions时, 如果需要配置具体的内容, 则用数组配置("actions":[{}]); 如果是引用commonActions中的, 则直接配置对应的name即可("actions":"xxxxName"); 即一个属性有不同的配置方式, 和value的配置相似(参看第4条统一说明)
// 6.action中的content同上, 可为对象, 也可为数组
// 7.action中的结果是会对业务流的参数有影响的, 而method中的参数不会

{
	// 基本配置
	"name":"", // 业务流名称(英文), 必填, 全局唯一
	"description":"", // 业务流描述, 默认为null
	"version":"1", // 版本控制, 默认为1
	"enabled":true, // 业务流是否启用, 默认为true
	
	// 参数配置
	"params":[{ // 输入参数数组, 默认为空数组
		"name":"", // 参数名(英文)
		"description":"", // 参数描述, 默认为null
		"scope":1, // 参数范围
		"dataType":1, // 参数数据类型, 默认为1
		"defaultValue":"", // 参数默认值, 与固定值一样, 默认为null
		"required":true // 参数是否必须, 默认为true
	}],
	
	// 事件配置
	"events":[{ // 业务流包含的所有事件
		"type":0, // 事件类型, 0一般事件, 1起始事件(一条业务流中只能有一个), 2结束事件(一条业务流中可有多个), 如果事件后没有其他事件, 默认也就结束了
		"name":"", // 事件名称(英文), 必填, 业务流唯一
		"description":"", // 事件描述, 默认为null
		"actions":[{ // 事件包含的动作, 按顺序执行
			"type":"", // 动作的类型, 以"类型前缀_描述"的命名规则定义
			"content":{}, // 动作具体要执行的内容
			"result":{ // 动作执行的结果, 可以没有结果, 默认为null
				"name":"", // 结果的(参数)名称
				"scope":5, // 结果的(参数)范围, 默认为5
				"dataType":"", // 结果的数据类型, 逻辑上这个dataType可以根据action的type, 就能确定值, 而不需要用户专门配置
			}
		}]
	}],
	
	// 流配置
	"flows":[{ // 业务流包含的所有流
		"description":"", // 流描述, 默认为null
		"type":1, // 流类型, 0顺序流, 1条件流(if-else/if-elsif-else), 默认为0, 在配置了条件流后, 如果没有配置else, 则提供一个默认的end-else结束流程
		"order":1, // 排序值, 默认为1, 当是条件流时, 如果配置了order值, 则根据order值的顺序, 从大到小依次判断, 碰到判断为true的, 进入到相应的targetEvent, 否则就按照json中配置的顺序依次判断
		"sourceEvent":"", // 起始的事件名 event.name
		"targetEvent":"", // 目标的事件名 event.name
		"conditionGroups":[{ // 判断条件组, 默认为空数组, 空数组标识判断结果默认为true
			"conditions":[{ // 条件
				"type":"data_op_comp", // 使用数据判断的action, 具体配置参看business.flow.action.desgin, 这里也只能配置这个type, 所以可以通过默认值, 无需专门配置这个值
				"content":{
					...
				},
				"nextLogicOp":"and" // 与下一个条件连接的逻辑操作符, and, or, 默认为and
			}],
			"nextLogicOp":"and" // 与下一个条件连接的逻辑操作符, and, or, 默认为and
		}],
	}],
	
	// 通用的action
	"commonActions":[{
		"name":"", // action的名称, 业务流唯一
		"description":"", // 描述
		"actions":[{}]
	}],
	
	// 方法配置, 一个method包含多个action, 最终提供返回值
	"methods":[{
		"name":"", // 方法名, 全局唯一
		"params":[{ 
			"name":"", // 参数名(英文) * 
			"description":"", // 参数描述, 默认为null
			"scope":5, // 参数范围, 这里不用配置, 直接使用5做为固定值
			"dataType":"string", // 参数数据类型, 默认为string *
			"defaultValue":"", // 参数默认值, 默认为null
			"required":true // 参数是否必须, 这里不用配置, 直接使用true做为固定值
		}],
		"actions":[{ // 具体的action
			// 所有的结果都属于本地map, 在return中决定返回哪些数据
		}], 
		
		"return":{ // 方法的返回值, 可有可无, 返回多个, 就返回map; 返回单个, 就直接返回值
			"all":false, // 是否返回全部数据, 默认false
			"names":["", ""], // all=false时, 要从本地map中返回的参数名
			"excludeNames":["", ""]	// all=true时, 排除这些名称的参数不返回
		}
	}],
	
	// sql配置, 可复用
	"sqls":[{
		"name":"", // 名称, 全局唯一
		"description":"", // 描述
		"type":1, // 类型, 1(insert), 2(delete), 3(update), 4(select)
		"content":{ // sql内容配置
			... // 具体参看business.flow.sql.desgin中不同type的配置结构
		}, 
		"params":[{ // sql需要的参数配置
			"name":"", // 参数名(英文) * 
			"description":"", // 参数描述, 默认为null
			"scope":5, // 参数范围, 这里不用配置, 直接使用5做为固定值
			"dataType":"string", // 参数数据类型, 默认为string *
			"defaultValue":"", // 参数默认值, 默认为null
			"required":true // 参数是否必须, 这里不用配置, 直接使用true做为固定值
		}] 
	}]
}