/**
 * Form表单信息回填操作JS
 * 编写者：白云飞
 * 时间：2014-03-14
 */
FillForm = {
	fill:function(id,d){
		var n1 = "";//声明一个接收变量用于接收对应取form表单中的id，name还是alias
		var rl = $("#"+id).find(":text,:hidden");
		for ( var i = 0; i < rl.length; i++) {
			n1 = this._parseGetName(rl[i]);
			$(rl[i]).attr("value",d[$(rl[i]).attr(n1)]);
		}
		rl = $("#"+id).find(":radio");
		var v="";
		for ( var i = 0; i < rl.length; i++) {
			n1 = this._parseGetName(rl[i]);
			v = d[$(rl[i]).attr(n1)];
			if($(rl[i]).attr("value")==v){
				if($(rl[i]).attr("checked")=="checked"){
				}else{
					$(rl[i]).attr("checked","checked");
				}
			}else{
				if($(rl[i]).attr("checked")==undefined){
				}else{
					$(rl[i]).removeAttr("checked");
				}
			}
		}
		rl = $("#"+id).find("select");
		for ( var i = 0; i < rl.length; i++) {
			n1 = this._parseGetName(rl[i]);
			v = d[$(rl[i]).attr(n1)];
			$(rl[i]).val(v);
		}
		rl = $("#"+id).find(":checkbox");
		var ck = new Array();
		for ( var i = 0; i < rl.length; i++) {
			n1 = this._parseGetName(rl[i]);
			if($.inArray($(rl[i]).attr(n1),ck)==-1){
				ck.push($(rl[i]).attr(n1));
			}
		}
		rl = $("#"+id).find(":checkbox");
		var vals = new Array();
		for ( var i = 0; i < ck.length; i++) {
			vals = d[ck[i]];
			for ( var j = 0; j < rl.length; j++) {
				n1 = this._parseGetName(rl[j]);
				if($(rl[j]).attr(n1) == ck[i]){
					if($.inArray($(rl[j]).attr("value"),vals)>=0){
						if($(rl[j]).attr("checked")=="checked"||$(rl[j]).attr("checked")==true){
						}else{
							$(rl[j]).attr("checked",true);
						}
					}else{
						if($(rl[j]).attr("checked")==undefined){
						}else{
							$(rl[j]).removeAttr("checked");
						}
					}
				}
			}
		}
	},
	//_parseGetName根据表单中的属性判断根据什么属性进行取。
	_parseGetName : function(o){
		var n = "";
		if($(o).attr("alias")==undefined || $(o).attr("alias") == ""){
			if($(o).attr("id")==undefined || $(o).attr("id")==""){
				if($(o).attr("name")==undefined || $(o).attr("name")==""){
				}else{
					n = "name";
				}
			}else{
				n = "id";
			} 
		}else{
			n = "alias";
		}
		return n;
	}
};