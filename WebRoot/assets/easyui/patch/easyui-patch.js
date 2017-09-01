(function() {
	if ($.fn.combobox) {
		$.fn.combobox.defaults.inputEvents.keyup = $.fn.combobox.defaults.inputEvents.keydown;// 兼容FireFox
		$.fn.combobox.defaults.inputEvents.keydown = $.noop;
	}
	if ($.fn.datebox) {
		$.fn.datebox.defaults.formatter = function(date) {
			return $.formatDate(date, 'yyyy-MM-dd');
		};
		$.fn.datebox.defaults.parser = function(value) {
			var v = $.parseDate(value, 'yyyy-MM-dd');
			return v == null ? new Date() : v;
		};
	}
	if ($.fn.datagrid) {
		$.extend($.fn.datagrid.methods, {
			url : function(jq, params) {
				jq.datagrid('options').url = params;
			},
			checkById : function(jq, params) {
				var idField = jq.datagrid('options').idField || 'id', ids = [].concat(params);
				$.each(jq.datagrid('getRows'), function(index, record) {
					($.inArray(record[idField], ids) !== -1) && jq.datagrid('checkRow', index)
				});
			}
		});
		$.extend($.fn.datagrid.defaults, {
			fit : true,
			fitColumns : true,
			selectOnCheck : false,
			checkOnSelect : false,
			singleSelect : true,
			checkOnSelect : false,
			pageSize : 15,
			pageList : [ 15, 30, 60, 120 ],
			loadMsg : '数据加载中，请稍后……',
		});
	}
	if ($.fn.tree) {
		$.extend($.fn.tree.methods, {
			uncheckAll : function(jq, params) {
				$.each(jq.tree('getChecked', [ 'checked', 'unchecked', 'indeterminate' ]), function(i, node) {
					jq.tree('uncheck', node.target);
				});
			},
			checkById : function(jq, params) {
				var node = jq.tree('find', params);
				node && jq.tree('check', node.target);
			}
		});
	}
})();