{
	"name": "ShoppingSettleAccounts",
	"description": "购物结账",
	"version": "1",
	"enabled": true,

	"params": [{
		"name": "shoppingCarId",
		"description": "购物车id",
		"scope": 1,
		"dataType": "string",
		"required": true
	}],

	"events": [{
		"type": 1,
		"name": "getProductsByShoppingCarId",
		"description": "根据购物车id查询到对应的商品",
		"actions": [{
				"type": "sql_query",
				"content": {
					"name": "queryProductsByShoppingCarId",
					"params": [{
						"name": "shoppingCarId",
						"scope": 1
					}]
				},
				"result": {
					"name": "products",
					"scope": 4,
					"dataType": "list"
				}
			},
			{
				"type": "param_op_declare",
				"content": [{
					"name": "totalPrice",
					"description": "商品总价",
					"scope": 3,
					"dataType": "double"
				}]
			}
		]
	}, {
		"name": "XiShuLeiProductCalcPrice",
		"description": "如果洗漱类用品的总价超过200元，则打8折",
		"actions": [{
			"type": "param_op_declare",
			"content": [{
				"name": "XiShuLeiProductTotalPrice",
				"description": "洗漱类产品总价",
				"scope": 5,
				"dataType": "double"
			}]
		}, {
			"type": "func_loop",
			"content": {
				"name": "products",
				"scope": 4,
				"alias": {
					"name":"product"
				},
				"actions": [{
					"type": "func_switch",
					"content": [{
						"conditionGroups": [{
							"conditions": [{
								"content": {
									"op": "eq",
									"left": {
										"name": "product.PRODUCT_TYPE",
										"scope": 5
									},
									"right": {
										"value": 2
									}
								}
							}]
						}],
						"actions": [{
							"type": "data_op_arithmetic",
							"content": [{
								"name": "XiShuLeiProductTotalPrice",
								"scope": 5,
								"op": "add"
							}, {
								"name": "product.PRODUCT_PRICE",
								"scope": 5
							}],
							"result": {
								"name": "XiShuLeiProductTotalPrice",
								"scope": 3,
								"dataType": "double"
							}
						}]
					}]
				}]
			}
		}]
	}],
	
	"flows":[{
		"sourceEvent":"getProductsByShoppingCarId",
		"targetEvent":"XiShuLeiProductCalcPrice"
	}],

	"sqls": [{
		"type": "select",
		"name": "queryProductsByShoppingCarId",
		"params": [{
			"name": "shoppingCarId",
			"description": "购物车id",
			"scope": 5,
			"dataType": "string",
			"required": true
		}],
		"content": {
			"selects": [{
				"results": [{
					"column": "product_name"
				}, {
					"column": "product_type"
				}, {
					"column": "product_price"
				}, {
					"column": "product_count"
				}],
				"table": {
					"name": "shopping_car"
				},
				"whereGroups": [{
					"conditions": [{
						"left": {
							"column": "shopping_car_id"
						},
						"rights": [{
							"paramName": "shoppingCarId"
						}],
						"cop": "eq"
					}]
				}]
			}]
		}
	}]
}