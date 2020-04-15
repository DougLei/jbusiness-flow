{
	"name":"ShoppingSettleAccounts",
	"description":"购物结账",
	"version":"1",
	"enabled":true,
	
	"params":[{
		"name":"shoppingCarId",
		"description":"购物车id",
		"scope":1,
		"dataType":"string",
		"required":true
	}],
	
	"events":[{
		"type":1,
		"name":"获取商品",
		"description":"根据购物车id查询到对应的商品",
		"actions":[{
			"type":"sql_query",
			"content":{
				"name":"queryProductsByShoppingCarId",
				"params":[{
					"name":"shoppingCarId",
					"scope":1
				}]
			},
			"result":{
				"name":"products",
				"scope":3,
				"dataType":"list"
			}
		}]
	}],
	
	"sqls":[{
		"type":"select",
		"name":"queryProductsByShoppingCarId",
		"params":[{
			"name":"shoppingCarId",
			"description":"购物车id",
			"scope":5,
			"dataType":"string",
			"required":true
		}],
		"content":{
			"selects":[{
				"results":[{
					"column":"product_name"
				},{
					"column":"product_type"
				},{
					"column":"product_price"
				},{
					"column":"product_count"
				}],
				"table":{
					"name":"shopping_car"
				},
				"whereGroups":[{
					"conditions":[{
						"left":{
							"column":"shopping_car_id"
						},
						"rights":[{
							"paramName":"shoppingCarId"
						}],
						"cop":"eq"
					}]
				}]
			}]
		}
	}]
}